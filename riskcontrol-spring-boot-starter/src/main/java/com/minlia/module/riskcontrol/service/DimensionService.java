package com.minlia.module.riskcontrol.service;

import com.minlia.module.riskcontrol.dao.RedisDao;
import com.minlia.module.riskcontrol.entity.RiskConfig;
import com.minlia.module.riskcontrol.enums.RiskLevelEnum;
import com.minlia.module.riskcontrol.event.Event;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.StringJoiner;

/**
 * Created by sunpeak on 2016/8/6.
 */
@Slf4j
@Service
public class DimensionService {

    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    private final static String RISK_KEY_FORMAT = "risk_%s_%s:%s_%s";

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private RiskConfigService riskConfigService;

    /**
     * SortedSet
     * 移除过期数据
     * 设置有效时间
     * 添加行为数据
     * 计算行为频数
     */
    private final static String luaScript = "if tonumber(ARGV[1])>0 then " +
            "redis.call('ZREMRANGEBYSCORE',KEYS[1],0,ARGV[1]);" +
            "redis.call('EXPIRE',KEYS[1],ARGV[2]);" +
            "end;" +
            "redis.call('ZADD', KEYS[1], ARGV[3], ARGV[4]);" +
            "return redis.call('ZCOUNT',KEYS[1],ARGV[5],ARGV[6]);";

    private String luaSha;

    /**
     * lua 添加行为数据并获取结果
     */
    private Long runSha(String key, String remMaxScore, String expire, String score, String value, String queryMinScore, String queryMaxScore) {
        if (luaSha == null) {
            luaSha = redisDao.scriptLoad(luaScript);
        }
        return redisDao.evalsha(luaSha, 1, new String[]{key, remMaxScore, expire, score, value, queryMinScore, queryMaxScore});
    }

    /**
     * 计算频数，这里考虑性能，采用redis方式
     *
     * @param event
     * @param condDimensions
     * @param periodSeconds
     * @param argsDimension
     * @return
     */
    public long distinctCountWithRedis(Event event, String[] condDimensions, long periodSeconds, String argsDimension) {
        return addQueryHabit(event, condDimensions, periodSeconds, argsDimension);
    }

    public long distinctCountWithRedisAndConfig(Event event, String[] condDimensions, String argsDimension) {
        RiskConfig riskConfig = riskConfigService.get(event.getScene());
        if (null == riskConfig) {
            event.setLevel(RiskLevelEnum.NORMAL);
            return 0;
        } else {
            long count = this.distinctCountWithRedis(event, condDimensions, riskConfig.getPeriodSeconds(), argsDimension);
            event.addScore(count, riskConfig.getDangerThreshold(), riskConfig.getWarningThreshold(), riskConfig.getThresholdScore(), riskConfig.getPerScore());
            return count;
        }
    }

    /**
     * 计算频数
     *
     * @param event          事件
     * @param condDimensions 条件维度数组,注意顺序
     * @param periodSeconds  查询时间段、多少秒之内的
     * @param argsDimension  聚合维度
     * @return
     */
    private long addQueryHabit(Event event, String[] condDimensions, long periodSeconds, String argsDimension) {
        //获取条件维度拼接值
        StringJoiner condDimensionsValue = new StringJoiner("_");
        for (String condDimension : condDimensions) {
            Object value = getProperty(event, condDimension);
            if (value == null || "".equals(value)) {
                return 0;
            }
            condDimensionsValue.add(value.toString());
        }

        //获取聚合维度值
        Object argsDimensionValue = getProperty(event, argsDimension);
        if (argsDimensionValue == null || "".equals(argsDimensionValue)) {
            return 0;
        }

        //生成Redis键值
        String key = String.format(RISK_KEY_FORMAT, event.getScene(), String.join("_", condDimensions), argsDimension, condDimensionsValue.toString());

        //max scope 当前时间
        String maxScope = dateTimeScore(event.getOperateTime());

        //max scope 当前时间-periodSeconds
        String minScope = dateTimeScore(event.getOperateTime().minusSeconds(periodSeconds));

        //scope 操作时间
        String scope = dateTimeScore(event.getOperateTime());

        Long ret = runSha(key, minScope, String.valueOf(periodSeconds), scope, argsDimensionValue.toString(), minScope, maxScope);
        return ret == null ? 0 : ret.intValue();
    }

    /**
     * 计算sortedset的score
     *
     * @param date
     * @return
     */
    private String dateScore(Date date) {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(date);
    }

    private String dateTimeScore(LocalDateTime localDateTime) {
        return localDateTime.format(DATE_TIME_FORMATTER);
    }

    private Object getProperty(Event event, String field) {
        try {
            return PropertyUtils.getProperty(event, field);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

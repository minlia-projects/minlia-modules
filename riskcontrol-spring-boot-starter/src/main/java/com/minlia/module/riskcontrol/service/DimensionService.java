package com.minlia.module.riskcontrol.service;

import com.minlia.module.redis.util.RedisUtils;
import com.minlia.module.riskcontrol.config.RiskcontrolConfig;
import com.minlia.module.riskcontrol.entity.RiskDroolsConfig;
import com.minlia.module.riskcontrol.enums.RiskLevelEnum;
import com.minlia.module.riskcontrol.event.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.StringJoiner;

/**
 * @author garen
 * @date 2016/8/6
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DimensionService {

    private final RedisTemplate redisTemplate;
    private final RiskcontrolConfig riskcontrolConfig;
    private final RiskDroolsConfigService riskDroolsConfigService;
    private final static String RISK_KEY_FORMAT = "risk_%s_%s:%s_%s";

    /**
     * SortedSet
     * 移除过期数据
     * 设置有效时间
     * 添加行为数据
     * 计算行为频数
     */
    private final static String luaScript =
            "redis.call('ZADD', KEYS[1], ARGV[4], ARGV[5]);" +      //添加值
                    "if tonumber(ARGV[1])>0 then " +                        //移除分数是否大于0
                    "redis.call('ZREMRANGEBYSCORE',KEYS[1],0,ARGV[1]);" +   //移除0到开始分数
                    "redis.call('EXPIRE',KEYS[1],ARGV[3]);" +               //设置过期时间
                    "end;" +
                    "return redis.call('ZCOUNT',KEYS[1],ARGV[1],ARGV[2]);"; //统计区间数量
    DefaultRedisScript<Long> longDefaultRedisScript = new DefaultRedisScript(luaScript, Long.class);

    /**
     * lua 添加行为数据并获取结果
     *
     * @param minScore 最小分数/开始时间
     * @param maxScore 最大分数/结束时间
     * @param expire   失效时间/秒
     * @param score    操作时间
     * @param value    聚合参数
     */
    private Long runSha(String key, Long minScore, Long maxScore, Long expire, Long score, String value) {
        Long result = (Long) redisTemplate.execute(longDefaultRedisScript, Collections.singletonList(key), minScore, maxScore, expire, score, value);
        return result;
    }

    /**
     * SortedSet
     * 移除过期数据
     * 设置有效时间
     * 添加行为数据
     * 计算行为频数
     */
    private final static String luaScript1 = "if tonumber(ARGV[1])>0 then " +   //移除分数是否大于0
            "redis.call('ZREMRANGEBYSCORE',KEYS[1],0,ARGV[1]);" +               //移除0到开始分数
            "redis.call('EXPIRE',KEYS[1],ARGV[2]);" +                           //设置过期时间
            "end;" +
            "redis.call('ZADD', KEYS[1], ARGV[3], ARGV[4]);" +                  //添加值
            "return redis.call('ZCOUNT',KEYS[1],ARGV[5],ARGV[6]);";             //统计区间数量
    DefaultRedisScript<Long> longDefaultRedisScript1 = new DefaultRedisScript(luaScript, Long.class);

    /**
     * lua 添加行为数据并获取结果
     *
     * @param remMaxScore   开始时间
     * @param expire        失效时间/秒
     * @param score         操作时间
     * @param value         聚合参数
     * @param queryMinScore 查询最小时间
     * @param queryMaxScore 查询最大时间
     */
    private Long runSha1(String key, Long remMaxScore, Long expire, Long score, String value, Long queryMinScore, Long queryMaxScore) {
        Long result = (Long) redisTemplate.execute(longDefaultRedisScript, Collections.singletonList(key), remMaxScore, expire, score, value, queryMinScore, queryMaxScore);
        return result;
    }

    /**
     * 计算频数，这里考虑性能，采用redis方式
     *
     * @param event
     * @param condDimensions
     * @param periodSeconds
     * @param aggrDimension
     * @return
     */
    public long distinctCountWithRedis(Event event, String[] condDimensions, long periodSeconds, String aggrDimension) {
        if (!riskcontrolConfig.getRealSwitchFlag()) {
            event.setLevel(RiskLevelEnum.NORMAL);
            return 0;
        }
        return addQueryHabit(event, condDimensions, periodSeconds, aggrDimension);
    }

    public long distinctCountWithRedisAndConfig(Event event, String[] condDimensions, String aggrDimension) {
        RiskDroolsConfig riskDroolsConfig = riskDroolsConfigService.get(event.getScene());
        if (null == riskDroolsConfig) {
            event.setLevel(RiskLevelEnum.NORMAL);
            return 0;
        } else {
            long count = this.distinctCountWithRedis(event, condDimensions, riskDroolsConfig.getPeriodSeconds(), aggrDimension);
            event.addScore(count, riskDroolsConfig.getDangerThreshold(), riskDroolsConfig.getWarningThreshold(), riskDroolsConfig.getThresholdScore(), riskDroolsConfig.getPerScore());
            event.setDetails(String.format(riskDroolsConfig.getDetail(), count));
            return count;
        }
    }

    /**
     * 根据配置计算风控等级
     *
     * @param event
     * @param count
     * @return
     */
    public RiskLevelEnum calculationLevelWithConfig(Event event, long count) {
        if (!riskcontrolConfig.getRealSwitchFlag()) {
            event.setCount(0L);
            return RiskLevelEnum.NORMAL;
        }

        RiskDroolsConfig riskDroolsConfig = riskDroolsConfigService.get(event.getScene());
        if (null == riskDroolsConfig) {
            event.setLevel(RiskLevelEnum.NORMAL);
        } else {
            event.addScore(count, riskDroolsConfig.getDangerThreshold(), riskDroolsConfig.getWarningThreshold(), riskDroolsConfig.getThresholdScore(), riskDroolsConfig.getPerScore());
        }
        event.setCount(count);
        return event.getLevel();
    }

    /**
     * 清除频数
     *
     * @param event
     * @param condDimensions
     * @param aggrDimension
     * @return
     */
    public void cleanCountWithRedis(Event event, String[] condDimensions, String aggrDimension) {
        String key = getRedisKey(event, condDimensions, aggrDimension);
        RedisUtils.zremoveRangeByScore(key, -1, event.getTime());
    }

    /**
     * 计算频数
     *
     * @param event          事件
     * @param condDimensions 条件维度数组,注意顺序
     * @param periodSeconds  查询时间段、多少秒之内的
     * @param aggrDimension  聚合维度
     * @return
     */
    private long addQueryHabit(Event event, String[] condDimensions, long periodSeconds, String aggrDimension) {
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
        Object aggrDimensionValue = getProperty(event, aggrDimension);
        if (aggrDimensionValue == null || "".equals(aggrDimensionValue)) {
            return 0;
        }

        //生成Redis键值
        String key = getRedisKey(event.getScene(), condDimensions, aggrDimension, condDimensionsValue.toString());
        //max scope 当前时间
        //Long maxScope = event.getTime();
        //max scope 当前时间-periodSeconds
        Long minScope = event.getTime() - periodSeconds * 1000;
        //scope 操作时间
        //Long scope = event.getTime();
        Long ret = runSha(key, minScope, event.getTime(), periodSeconds, event.getTime(), aggrDimensionValue.toString());
        //Long ret = runSha1(key, minScope, periodSeconds, scope, aggrDimensionValue.toString(), minScope, maxScope);
        long count = ret == null ? 0 : ret.intValue();
        event.setCount(count);
        return count;
    }

    /**
     * 生成redis键值
     *
     * @param event
     * @param condDimensions
     * @param aggrDimension
     * @return
     */
    public String getRedisKey(Event event, String[] condDimensions, String aggrDimension) {
        //获取条件维度拼接值
        StringJoiner condDimensionsValue = new StringJoiner("_");
        for (String condDimension : condDimensions) {
            Object value = getProperty(event, condDimension);
            if (value == null || "".equals(value)) {
            }
            condDimensionsValue.add(value.toString());
        }

        //获取聚合维度值
        Object aggrDimensionValue = getProperty(event, aggrDimension);
        if (aggrDimensionValue == null || "".equals(aggrDimensionValue)) {
        }

        return getRedisKey(event.getScene(), condDimensions, aggrDimension, condDimensionsValue.toString());
    }

    public String getRedisKey(String scene, String[] condDimensions, String aggrDimension, String condDimensionsValue) {
        return String.format(RISK_KEY_FORMAT, scene, String.join("_", condDimensions), aggrDimension, condDimensionsValue).toLowerCase();
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

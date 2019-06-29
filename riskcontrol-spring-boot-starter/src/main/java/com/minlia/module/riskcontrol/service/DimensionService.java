package com.minlia.module.riskcontrol.service;

import com.minlia.module.riskcontrol.dao.RedisDao;
import com.minlia.module.riskcontrol.enums.TimePeriodEnum;
import com.minlia.module.riskcontrol.event.Event;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sunpeak on 2016/8/6.
 */
@Slf4j
@Service
public class DimensionService {

    @Autowired
    private RedisDao redisDao;

    private String riskEventCollection = "riskevent";

    /**
     * 过期SortedSet的数据
     * 过期SortedSet键
     * 添加SortedSet值
     * 计算SortedSet时间段内的频数
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
     * 计算频数，有2种方式，这里考虑性能，采用redis方式
     *
     * @param event
     * @param condDimensions
     * @param enumTimePeriod
     * @param argsDimension
     * @return
     */
    public int distinctCountWithRedis(Event event, String[] condDimensions, TimePeriodEnum enumTimePeriod, String argsDimension) {
        return addQueryHabit(event, condDimensions, enumTimePeriod, argsDimension);
    }

    /**
     * @param event          事件
     * @param condDimensions 条件维度数组,注意顺序
     * @param enumTimePeriod 查询时间段
     * @param argsDimension  聚合维度
     * @return
     */
    private int addQueryHabit(Event event, String[] condDimensions, TimePeriodEnum enumTimePeriod, String argsDimension) {
        if (event == null || ArrayUtils.isEmpty(condDimensions) || enumTimePeriod == null || argsDimension == null) {
            log.error("参数错误");
            return 0;
        }

        Date operate = event.getOperateTime();
        String key1 = String.join(".", String.join(".", condDimensions), argsDimension);
        String[] key2 = new String[condDimensions.length];
        for (int i = 0; i < condDimensions.length; i++) {
            Object value = getProperty(event, condDimensions[i]);
            if (value == null || "".equals(value)) {
                return 0;
            }
            key2[i] = value.toString();
        }
        String key = event.getScene() + "_sset_" + key1 + "_" + String.join(".", key2);

        Object value = getProperty(event, argsDimension);
        if (value == null || "".equals(value)) {
            return 0;
        }

        int expire = 0;
        String remMaxScore = "0";
        if (!enumTimePeriod.equals(TimePeriodEnum.ALL)) {
            //如果需要过期，则保留7天数据,满足时间段计算
            expire = 7 * 24 * 3600;
            remMaxScore = dateScore(new Date(operate.getTime() - expire * 1000L));
        }

        Long ret = runSha(key, remMaxScore, String.valueOf(expire), dateScore(operate), value.toString(), dateScore(enumTimePeriod.getMinTime(operate)), dateScore(enumTimePeriod.getMaxTime(operate)));
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


    private Object getProperty(Event event, String field) {
        try {
            return PropertyUtils.getProperty(event, field);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

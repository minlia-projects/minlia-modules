package com.minlia.module.riskcontrol.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.minlia.module.common.util.RequestIpUtils;
import com.minlia.module.riskcontrol.enums.RiskLevelEnum;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * Created by sunpeak on 2016/8/6.
 */
@Slf4j
@Data
public abstract class Event {

    public final static String IP = "ip";

    public final static String UID = "uid";

    public final static String TIME = "time";

    public final static String SCENE_VALUE = "sceneValue";

    //public final static String MOBILE_SEG = "mobileSeg";
    //
    //public final static String MOBILE_IP_AREA = "mobileIpArea";
    //
    //public final static String OPERATE_IP_AREA = "operateIpArea";

    /**
     * 事件id
     */
    @JsonIgnore
    private String eventId = UUID.randomUUID().toString();

    /**
     * 场景
     */
    @JsonIgnore
    private String scene;

    /**
     * 场景值
     */
    @JsonIgnore
    private String sceneValue;

    /**
     * 风险等级
     */
    @JsonIgnore
    private RiskLevelEnum level;

    /**
     * 计数
     */
    @JsonIgnore
    private Long count;

    /**
     * 事件评分
     */
    private int score;

    /**
     * 操作时间
     */
    private long time = System.currentTimeMillis();

    /**
     * 详情
     */
    private String details;

    /**
     * 是否匹配
     */
    private boolean matched;

    /**
     * IP
     */
    private String ip = RequestIpUtils.getClientIP();

    /****** TODO 以下扩展维度*****/

    /**
     * s
     * 用户ID
     */
    private Long uid;

    ///**
    // * 手机号段
    // */
    //private String mobileSeg;
    //
    ///**
    // * 手机归属地和运营商
    // */
    //private String mobileIpArea;
    //
    ///**
    // * 操作ip归属地和运营商
    // */
    //private String operateIpArea;

    /**
     * 计算事件评分
     *
     * @param count            当前值
     * @param dangerThreshold  危险阀值
     * @param warningThreshold 警告阀值
     * @param thresholdScore   阀值评分
     * @param perScore         超过阀值以上，每个评分
     * @return 是否达到阈值
     */
    public RiskLevelEnum addScore(long count, int dangerThreshold, int warningThreshold, int thresholdScore, int perScore) {
        if (dangerThreshold <= 0 || warningThreshold <= 0 || thresholdScore <= 0 || perScore < 0) {
            level = RiskLevelEnum.NORMAL;
        } else {
            if (count > warningThreshold) {
                this.score += thresholdScore + (count - warningThreshold) * perScore;
                if (count > dangerThreshold) {
                    level = RiskLevelEnum.DANGER;
                } else {
                    level = RiskLevelEnum.WARNING;
                }
            } else {
                level = RiskLevelEnum.NORMAL;
            }
        }
        return level;
    }

}
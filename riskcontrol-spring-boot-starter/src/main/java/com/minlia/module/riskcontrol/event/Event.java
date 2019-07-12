package com.minlia.module.riskcontrol.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.minlia.module.common.util.RequestIpUtils;
import com.minlia.module.riskcontrol.enums.RiskLevelEnum;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.net.util.IPAddressUtil;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by sunpeak on 2016/8/6.
 */
@Data
public abstract class Event {

    private static Logger logger = LoggerFactory.getLogger(Event.class);

    public final static String ID = "id";

    public final static String IP = "ip";

    public final static String OPERATETIME = "operateTime";

    public final static String MOBILESEG = "mobileSeg";

    public final static String MOBILEIPAREA = "mobileIpArea";

    public final static String OPERATEIPAREA = "operateIpArea";

    /**
     * 事件id
     */
    @JsonIgnore
    private String eventId;

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
     * 事件评分
     */
    private int score;

    /**
     * 操作时间
     */
    private LocalDateTime operateTime;

    private boolean isBlack;

    private boolean matched;


    /****** TODO 以下扩展维度*****/

    /**
     * IP
     */
    private String ip;

    /**
     * 手机号段
     */
    private String mobileSeg;

    /**
     * 手机归属地和运营商
     */
    private String mobileIpArea;

    /**
     * 操作ip归属地和运营商
     */
    private String operateIpArea;

    public Event() {
        operateTime = LocalDateTime.now();
        eventId = UUID.randomUUID().toString();
        ip = RequestIpUtils.getClientIP();
    }

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
            if (count >= warningThreshold) {
                this.score += thresholdScore + (count - warningThreshold) * perScore;
                if (count >= dangerThreshold) {
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

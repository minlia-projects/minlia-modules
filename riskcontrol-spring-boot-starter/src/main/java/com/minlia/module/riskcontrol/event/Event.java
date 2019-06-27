package com.minlia.module.riskcontrol.event;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by sunpeak on 2016/8/6.
 */
@Data
public abstract class Event {

    private static Logger logger = LoggerFactory.getLogger(Event.class);

    public final static String ID = "id";

    public final static String OPERATETIME = "operateTime";

    public final static String MOBILESEG = "mobileSeg";

    public final static String MOBILEIPAREA = "mobileIpArea";

    public final static String OPERATEIPAREA = "operateIpArea";

    /**
     * 事件id
     */
    private String id;

    /**
     * 场景
     */
    private String scene;

    /**
     * 事件评分
     */
    private int score;

    /**
     * 操作时间
     */
    private Date operateTime;

    /****** TODO 以下扩展维度*****/
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

    /**
     * 计算事件评分
     *
     * @param count      当前值
     * @param level      阀值
     * @param levelScore 阀值评分
     * @param perScore   超过阀值以上，每个评分
     * @return 是否达到阈值
     */
    public boolean addScore(int count, int level, int levelScore, int perScore) {
        if (level <= 0 || levelScore <= 0 || perScore < 0) {
            return false;
        }
        if (count >= level) {
            this.score += levelScore + (count - level) * perScore;
            return true;
        }
        return false;
    }

}

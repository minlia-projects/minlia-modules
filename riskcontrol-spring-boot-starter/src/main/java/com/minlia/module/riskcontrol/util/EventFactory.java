package com.minlia.module.riskcontrol.util;

import com.alibaba.fastjson.JSON;
import com.minlia.module.riskcontrol.entity.RCRuntimeException;
import com.minlia.module.riskcontrol.enums.SceneEnum;
import com.minlia.module.riskcontrol.event.Event;
import com.minlia.module.riskcontrol.event.LoginEvent;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * Created by sunpeak on 2016/8/6.
 */
@Component
public class EventFactory {

    private static Logger logger = LoggerFactory.getLogger(EventFactory.class);


    public static Event build(String json) {
        String scene = JSON.parseObject(json).getString("scene");
        if (StringUtils.isEmpty(scene)) {
            logger.error("scene参数错误，" + json);
            throw new RCRuntimeException("scene参数确实，" + json);
        }
        SceneEnum sceneEnum = SceneEnum.valueOf(scene.toUpperCase());
        if (sceneEnum == null) {
            logger.error("json格式错误，" + json);
            throw new RCRuntimeException("json格式错误，" + json);
        }

        Event event = null;
        if (SceneEnum.LOGIN.equals(sceneEnum)) {
            event = JSON.parseObject(json, LoginEvent.class);
        }

        check(event);

        return event;
    }


    private static void check(Event event) {
        if (event == null) {
            throw new RCRuntimeException("事件错误");
        }
        if (event.getOperateTime() == null) {
            event.setOperateTime(new Date());
        }
        if (StringUtils.isEmpty(event.getEventId())) {
            event.setEventId(UUID.randomUUID().toString());
        }

        // TODO  扩展维度信息
    }

}

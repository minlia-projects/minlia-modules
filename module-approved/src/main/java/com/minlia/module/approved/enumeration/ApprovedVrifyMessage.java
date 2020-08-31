package com.minlia.module.approved.enumeration;

import com.minlia.cloud.code.Code;
import com.minlia.cloud.i18n.Lang;

public enum ApprovedVrifyMessage implements Code {

    /**
     * 审批任务已存在
     */
    TASK_EXISTS("system.milia.approved.verify.task.exists"),

    /**
     * 审批任务不存在
     */
    TASK_NOT_EXISTS("system.milia.approved.verify.task.not.exists"),

    /**
     * 审批人不能是本人
     */
    TASK_NOT_MYSELF("system.milia.approved.verify.not.myself"),

    /**
     * 没有权限审批
     */
    NOT_APPROVED_PERMISSION("system.milia.approved.verify.not.permission"),

    ;
    private String name;

    ApprovedVrifyMessage(String name){
        this.name = name;
    }

    @Override
    public String code() {
        return this.name();
    }

    @Override
    public String i18nKey() {
        return this.name;
    }

    @Override
    public String message(Object... args) {
        return Lang.get(this.i18nKey(), args);
    }

}
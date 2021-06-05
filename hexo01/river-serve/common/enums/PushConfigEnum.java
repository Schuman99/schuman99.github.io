package com.general.common.enums;

import io.micrometer.core.instrument.distribution.TimeWindowHistogram;

/**
 * 通知配置枚举
 * @author ysj
 * @create 2018-06-02
 **/
public enum PushConfigEnum {

    ITEM("com.general.logic.item.service.impl.ItemManageImpl","新建事项",PushMessageTypeEnum.TODO_NOTICE),
    OHTER_ITEM("com.general.logic.item.service.impl.ItemTraceImpl","待办事项",PushMessageTypeEnum.TODO_NOTICE),
    COMMENT("com.general.logic.message.service.impl.MessageCommentServiceImpl","评论",PushMessageTypeEnum.COMMENT),
    WARNBRAKE("com.general.logic.warn.service.impl.WarnBrakeServiceImpl","闸泵站预警信息",PushMessageTypeEnum.WARN_NOTICE),
    WARNDEVICE("com.general.logic.warn.service.impl.WarnDeviceServiceImpl","设备提醒信息",PushMessageTypeEnum.WARN_NOTICE),
    WARNVIDEO("com.general.logic.warn.service.impl.WarnVideoServiceImpl","视频预警信息",PushMessageTypeEnum.WARN_NOTICE),
    WARNWATERQUALITY("com.general.logic.warn.service.impl.WarnWaterQualityServiceImpl","水质预警信息",PushMessageTypeEnum.WARN_NOTICE),
    MESSAGENEWKNOWLEDGE("com.general.logic.message.service.impl.MessageNewKnowledgeServiceImpl","新闻知识库",PushMessageTypeEnum.NEW);
//    APPEAL("com.general.logic.item.service.impl.ItemAppealServiceImpl", "诉求","");

    // 实现类名称
    private String className;
    // 名称
    private  String name;

    private PushMessageTypeEnum pushMessageTypeEnum;
    PushConfigEnum(String className, String name,PushMessageTypeEnum pushMessageTypeEnum) {
        this.className = className;
        this.name = name;
        this.pushMessageTypeEnum = pushMessageTypeEnum;
    }

    public PushMessageTypeEnum getPushMessageTypeEnum() {
        return pushMessageTypeEnum;
    }

    public void setPushMessageTypeEnum(PushMessageTypeEnum pushMessageTypeEnum) {
        this.pushMessageTypeEnum = pushMessageTypeEnum;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.className;
    }
    // 获取 枚举
    public static PushConfigEnum getEnumByClassName(String className) {
        for (PushConfigEnum en: PushConfigEnum.values()) {
            if (en.getClassName().equals(className)) {
                return en;
            }
        }
        return null;
    }


}

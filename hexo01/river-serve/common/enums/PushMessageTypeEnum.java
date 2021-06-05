package com.general.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 推送消息类型枚举
 */
public enum PushMessageTypeEnum {

    NOTICE("0", "公告"),
    COMMENT("1", "评论"),
    WARN_NOTICE("2", "预警通知"),
    TODO_NOTICE("3", "待办事项通知"),
    NEW("4", "新闻"),
    WARNING("5", "警告"),
    APPROVAL("6", "审批"),
    MAINTENANCE("7", "运维事项");

    private String type;
    private String name;

    PushMessageTypeEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.type;
    }

    // 获取 枚举
    public static List<String> getTypes(String reomveType) {
        List<String> list = new ArrayList<>();
        for (PushMessageTypeEnum en : PushMessageTypeEnum.values()) {
            if (!en.getType().equals(reomveType)) {
                list.add(en.type);
            }
        }
        return list;
    }

}

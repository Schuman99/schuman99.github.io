package com.general.common.enums;

/**
 * 推送消息状态
 */
public enum PushTargetStatusEnum {

    UNREAD("未读", 0),
    READ("已读", 1),
    DISABLED("失效", 2);

    private String name;

    private int value;

    PushTargetStatusEnum(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static PushTargetStatusEnum getEnumByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (PushTargetStatusEnum enums: PushTargetStatusEnum.values()) {
            if (enums.value == value)
                return enums;
        }
        return null;
    }
}

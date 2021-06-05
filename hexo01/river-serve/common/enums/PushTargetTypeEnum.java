package com.general.common.enums;

/**
 * 推送目标
 */
public enum PushTargetTypeEnum {

    USER("0","用户"),
    ROLE("1","角色"),
    ALL("2","所有");

    private String type;
    private  String name;

    PushTargetTypeEnum(String type, String name) {
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
}

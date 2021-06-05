package com.general.common.enums;

public enum MessageTargetEnum {

    USER("用户","0"),
    ROLE("角色","1"),
    ALL("所有","2");

    private  String name;
    private String type;

    MessageTargetEnum(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}

package com.general.common.enums;

/**
 * 信息面向用户
 */
public enum MessageFaceEnum {

    YONG_HU("用户","0"),
    GONG_KAI("公开","1");

    private String name;
    private  String type;

    MessageFaceEnum(String name, String type) {
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

package com.general.common.enums;

/**
 * 信息发布类型
 */
public enum MessageReleaseEnum {

    WATER("水务信息","0"),
    KNOWLEDGE("知识库","1"),
    MESSAGE("消息","2");

    private String name;
    private  String type;

    MessageReleaseEnum(String name, String type) {
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

package com.general.common.enums;

public enum AttachTypeEnum {
    TOU_XIANG("头像","0"),
    FU_JIAN("附件","1");

    private String name;
    private  String type;

    AttachTypeEnum(String name, String type) {
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

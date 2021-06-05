package com.general.common.enums;

/**
 * 数据监测枚举  市测 - 设备
 */
public enum CollectDataTypeEnum {

    SHI_CE("市测","0"),
    SHE_BEI("设备","1");

    private String name;
    private  String type;

    CollectDataTypeEnum(String name, String type) {
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

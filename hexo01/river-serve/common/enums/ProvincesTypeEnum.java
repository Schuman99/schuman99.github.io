package com.general.common.enums;

/**
 * 省市区类型枚举
 * @author 张渊
 * @create 2018-05-10
 */
public enum ProvincesTypeEnum {

    COUNTRY(0),
    PROVINCE(1),
    CITY(2),
    DISTRICT(3);

    private int type;

    ProvincesTypeEnum (int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

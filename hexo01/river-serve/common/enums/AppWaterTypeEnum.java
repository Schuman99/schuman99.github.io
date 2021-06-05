package com.general.common.enums;

/**
 * 水质类别
 */
public enum AppWaterTypeEnum {

    YOU("优", "1"),
    LIANG("良","2"),
    CHA("差","3"),;

    private  String label;

    private  String value;

    AppWaterTypeEnum(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}

package com.general.common.enums;

/**
 * YSJ
 * 考核目标
 */
public enum AssessmentTargetEnum {

    I("Ⅰ", "1"),
    II("Ⅱ","2"),
    III("Ⅲ","3"),
    IV("Ⅳ","4"),
    V("Ⅴ","5"),
    VI("劣Ⅴ","6");

    private  String label;

    private  String value;

    AssessmentTargetEnum(String label, String value) {
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

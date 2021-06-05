package com.general.common.enums;

/**
 * 对比符号
 * @author 张渊
 */
public enum SymbolEnum {
    LESS("<", -1, "≤"),
    LESS_OR_EQUAL("≤", 1, ">"),
//    EQUAL("=", 0, "!="),
    MORE(">", 1, "≤"),
    MORE_OR_EQUAL("≥", -1, "<");

    private String code;

    private Integer value;

    private String reCode;

    SymbolEnum (String code, Integer value, String reCode) {
        this.code = code;
        this.value = value;
        this.reCode = reCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getReCode() {
        return reCode;
    }

    public void setReCode(String reCode) {
        this.reCode = reCode;
    }

    @Override
    public String toString() {
        return this.code;
    }

    public static SymbolEnum getEnumByCode(String code) {
        for (SymbolEnum en: SymbolEnum.values()) {
            if (en.getCode().equals(code)) {
                return en;
            }
        }
        return null;
    }

    public static Integer getValue(String code) {
        for (SymbolEnum en: SymbolEnum.values()) {
            if (en.getCode().equals(code)) {
                return en.getValue();
            }
        }
        return 2;
    }

    public static String getReCode(String code) {
        for (SymbolEnum en: SymbolEnum.values()) {
            if (en.getCode().equals(code)) {
                return en.getReCode();
            }
        }
        return null;
    }
}

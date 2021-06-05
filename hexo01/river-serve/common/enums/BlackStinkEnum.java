package com.general.common.enums;

/**
 * 故障状态
 */
public enum BlackStinkEnum {

    NORMAL("正常","0"),
    LIGHT("轻度","1"),
    HEIGHT("重度","2");

    private String name;
    private String state;

    BlackStinkEnum(String name, String state) {
        this.name = name;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


}

package com.general.common.enums;

/**
 * 故障状态
 */
public enum PowerStateEnum {

    OPEN("开启",0),
    CLOSE("关闭",1);

    private String name;
    private Integer state;

    PowerStateEnum(String name, Integer state) {
        this.name = name;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }


}

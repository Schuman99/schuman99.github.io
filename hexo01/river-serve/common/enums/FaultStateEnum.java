package com.general.common.enums;

/**
 * 故障状态
 */
public enum FaultStateEnum {

    ONLINE("正常",0),
    TROUBLE("故障",1),
    OFFLINE("离线",2);

    private  String name;
    private Integer state;

    FaultStateEnum(String name, Integer state) {
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

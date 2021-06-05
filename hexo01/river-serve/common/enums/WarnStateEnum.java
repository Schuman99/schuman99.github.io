package com.general.common.enums;

import java.util.ArrayList;
import java.util.List;

public enum WarnStateEnum {

    UNTREATED("未处理",0),
    PROCESSED("已处理",1);

    private  String name;
    private  Integer state;

    WarnStateEnum(String name, Integer state) {
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

    public static List<Integer> getList() {
        List<Integer> list = new ArrayList<>();
        for (WarnStateEnum stateEnum : WarnStateEnum.values()) {
            list.add(stateEnum.getState());
        }
        return list;
    }
}

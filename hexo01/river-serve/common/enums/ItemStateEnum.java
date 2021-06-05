package com.general.common.enums;

import java.util.ArrayList;
import java.util.List;

public enum ItemStateEnum {

    UNTREATED("未处理","0"),
    PROCESSED("已处理","1");

    private  String name;
    private  String state;

    ItemStateEnum(String name, String state) {
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

    public static List<String> getList() {
        List<String> list = new ArrayList<>();
        for (ItemStateEnum stateEnum : ItemStateEnum.values()) {
            list.add(stateEnum.getState());
        }
        return list;
    }
}

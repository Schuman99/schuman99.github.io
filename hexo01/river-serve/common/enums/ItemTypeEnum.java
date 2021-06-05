package com.general.common.enums;

import java.util.ArrayList;
import java.util.List;

public enum ItemTypeEnum {

    WAIT("待办","0"),
    CHANGE("交办","1"),
    REPORT("上报","2"),
    ALREADY("已办","3");

    private  String name;
    private  String state;

    ItemTypeEnum(String name, String state) {
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

    @Override
    public String toString() {
        return this.state;
    }

    public static List<String> getList() {
        List<String> list = new ArrayList<>();
        for (ItemTypeEnum typeEnum : ItemTypeEnum.values()) {
            list.add(typeEnum.getState());
        }
        return list;
    }
}

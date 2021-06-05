package com.general.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 事项追踪里面的 来源类型
 */
public enum ItemTraceSourceTypeEnum {

    SELF_BUILD("自建","0"),
    ASSIGN("交办","1"),
    REPORT("上报","2"),
    SYSTEM("系统","3"),
    COMPLAIN("投诉","4"),
    PATROL("巡查","5"),
    WARN("预警","6");

    private  String name;
    private  String state;

    ItemTraceSourceTypeEnum(String name, String state) {
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
        return  this.state;
    }

    public static List<String> getList() {
        List<String> list = new ArrayList<>();
        for (ItemTraceSourceTypeEnum typeEnum : ItemTraceSourceTypeEnum.values()) {
            list.add(typeEnum.getState());
      }
      return list;
    }
}

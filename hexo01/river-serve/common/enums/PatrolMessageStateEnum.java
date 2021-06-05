package com.general.common.enums;

/**
 * 巡查状态
 * @author ysj
 */
public enum PatrolMessageStateEnum {

    HALFWAY_PATROL("巡查中",0),
    COMPLETE_PATROL("完成巡查",1),
    ABANDON_PATROL("放弃巡查",2);

    private  String name;
    private Integer type;

    PatrolMessageStateEnum(String name, Integer type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


}

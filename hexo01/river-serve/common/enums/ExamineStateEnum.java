package com.general.common.enums;

/**
 * 考核状态
 * @author ysj
 */
public enum ExamineStateEnum {

    UNEXMINE("未考核",0),
    ALREADY_EXAMINE("已考核",1);

    private  String name;
    private Integer type;

    ExamineStateEnum(String name, Integer type) {
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

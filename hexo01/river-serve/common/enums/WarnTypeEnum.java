package com.general.common.enums;

/**
 * 预警状态
 * @author ysj
 */
public enum WarnTypeEnum {

    YI_WU_RU_QIN("异物入侵",0);

    private  String name;
    private Integer type;

    WarnTypeEnum(String name, Integer type) {
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

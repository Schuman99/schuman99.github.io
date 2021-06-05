package com.general.common.enums;

/**
 * 消息读取状态
 */
public enum MessageStateEnum {

    UNREAD("未读",0),
    READ("已读",1),
    DISABLED("失效",2);

    private  String name;
    private Integer type;

    MessageStateEnum(String name, Integer type) {
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

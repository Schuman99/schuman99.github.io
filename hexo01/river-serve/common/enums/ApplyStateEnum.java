package com.general.common.enums;

/**
 * 申请状态
 */
public enum ApplyStateEnum {
    APPLY("申请中", 0),
    RESOLVE("同意", 1),
    REJECT("拒绝", 2);

    private String name;
    private Integer state;

    ApplyStateEnum(String name, Integer state) {
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

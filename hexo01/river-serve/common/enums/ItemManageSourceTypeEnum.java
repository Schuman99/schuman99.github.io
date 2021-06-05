package com.general.common.enums;


/**
 *  为了最初的 来源 生成事项的类型
 */
public enum ItemManageSourceTypeEnum {

    COMMON("普通","0"),
    VIDEO_WARN("视频预警","1"),
    PATROL("巡查","2"),
    APPEAL("诉求", "3"),
    BRAKE_WARN("闸泵站预警","4"),
    WATER_WARN("水质预警","5"),
    DEVICE_FAULT("设备故障","6");

    private  String name;
    private  String state;

    public static String getName (String state) {
        for (ItemManageSourceTypeEnum enums : ItemManageSourceTypeEnum.values()) {
            if (state.equals(enums.getState())) {
                return enums.getName();
            }
        }
        return null;
    }

    ItemManageSourceTypeEnum(String name, String state) {
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
}

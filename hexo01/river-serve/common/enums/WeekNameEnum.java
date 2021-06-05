package com.general.common.enums;
/**
 *
 *
 * @packageName com.general.common.enums
 * @className WeekNameEnum
 * @description TODO
 * @author yeluo
 * @date 2019-05-21 15:40
 * @version 1.0
 */
public enum WeekNameEnum {

    MONDAY("星期一", 1),
    TUESDAY("星期二", 2),
    WEDNESDAY("星期三", 3),
    THURSDAY("星期四", 4),
    FRIDAY("星期五", 5),
    SATURDAY("星期六", 6),
    SUNDAY("星期日", 7);

    private  String name;
    private  Integer type;

    WeekNameEnum(String name, Integer type) {
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

    // 获取 枚举
    public static WeekNameEnum getEnumByType(Integer type) {
        for (WeekNameEnum weekNameEnum: WeekNameEnum.values()) {
            if (weekNameEnum.getType().equals(type)) {
                return weekNameEnum;
            }
        }
        return null;
    }
}

package com.general.common.enums;

/**
 *  事项诉求配置枚举
 * @author ysj
 * @create 2018-06-02
 **/
public enum AppealTypeEnum {

    REFER("咨询", 0),
    COMPLAIN("投诉", 1),
    ADVISE("建议",2),
    HELP("求助",3),
    REPORT("举报",4);

    private  String name;
    private  Integer type;

    AppealTypeEnum(String name, Integer type) {
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
    public static AppealTypeEnum getEnumByType(Integer type) {
        for (AppealTypeEnum en: AppealTypeEnum.values()) {
            if (en.getType().equals(type)) {
                return en;
            }
        }
        return null;
    }


}

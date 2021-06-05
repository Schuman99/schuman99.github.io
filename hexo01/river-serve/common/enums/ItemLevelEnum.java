package com.general.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * ysj
 */
public enum ItemLevelEnum {

    GENERAL("普通", "0"),
    IMPORTANCE("重要", "1"),
    URGENCY("紧急", "2");

    private  String levelName;
    private  String level;

    ItemLevelEnum(String levelName, String level) {
        this.levelName = levelName;
        this.level = level;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public static List<String> getList() {
        List<String> list = new ArrayList<>();
        for (ItemLevelEnum levelEnum : ItemLevelEnum.values()) {
            list.add(levelEnum.getLevel());
        }
        return list;
    }

    @Override
    public String toString() {
        return this.level;
    }
}

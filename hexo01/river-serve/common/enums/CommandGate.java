package com.general.common.enums;

public enum CommandGate {

    OPEN("开启", "cs01", 0),
    CLOSE("关闭", "cs03", 1),
    STOP("停止", "cs02", 2);

    private String name;
    private String command;
    private Integer value;

    CommandGate(String name, String command, Integer value) {
        this.name = name;
        this.command = command;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public static String getCommand(Integer value) {
        for (CommandGate detail : CommandGate.values()) {
            if (detail.getValue() == value) {
                return detail.command;
            }
        }
        return null;
    }

    public static String getName(Integer value) {
        for (CommandGate detail : CommandGate.values()) {
            if (detail.getValue() == value) {
                return detail.name;
            }
        }
        return null;
    }
}

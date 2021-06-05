package com.general.common.enums;

public enum CommandPump {

    START("开启", "cs01", 0),
    STOP("关闭", "cs02", 1);

    private String name;
    private String command;
    private Integer value;

    CommandPump(String name, String command, Integer value) {
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
        for (CommandPump detail : CommandPump.values()) {
            if (detail.getValue() == value) {
                return detail.command;
            }
        }
        return null;
    }

    public static String getName(Integer value) {
        for (CommandPump detail : CommandPump.values()) {
            if (detail.getValue() == value) {
                return detail.name;
            }
        }
        return null;
    }
}

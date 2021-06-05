package com.general.common.enums;

import org.springframework.util.StringUtils;

/**
 * Websocket
 */
public enum WebSocketDestinationEnum {

    PUSH_MESSAGE("推送消息", "/push_message"),
    APPROVAL("审批信息", "/approval");

    private String name;
    private String value;

    WebSocketDestinationEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static WebSocketDestinationEnum getEnumByValue(String value) {
        if (value == null) {
            return null;
        }
        for (WebSocketDestinationEnum enums: WebSocketDestinationEnum.values()) {
            if (StringUtils.pathEquals(enums.value, value))
                return enums;
        }
        return null;
    }
}

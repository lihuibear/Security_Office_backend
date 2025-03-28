package com.lihui.security_office_backend.model.enums;

public enum ContentType {
    VIDEO("video"),
    ARTICLE("article");

    private final String value;

    ContentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ContentType fromValue(String value) {
        for (ContentType type : values()) {
            if (type.getValue().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效的内容类型: " + value);
    }
}
package com.lihui.security_office_backend.model.enums;

import lombok.Getter;

@Getter
public enum CompletionStatusType {
    NOT_LEARNED(0),
    LEARNED(1);

    private final int value;

    CompletionStatusType(int value) {
        this.value = value;
    }

    public static CompletionStatusType fromValue(int value) {
        for (CompletionStatusType type : values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效的完成状态值: " + value);
    }
}
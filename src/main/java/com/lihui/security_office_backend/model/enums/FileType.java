// FileType.java
package com.lihui.security_office_backend.model.enums;

public enum FileType {
    VIDEO("video"),
    ARTICLE("article"),
    COVER("cover");

    private final String type;

    FileType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    // 根据类型字符串获取对应的枚举
    public static FileType fromString(String type) {
        for (FileType fileType : FileType.values()) {
            if (fileType.type.equalsIgnoreCase(type)) {
                return fileType;
            }
        }
        throw new IllegalArgumentException("无效的文件类型：" + type);
    }
}
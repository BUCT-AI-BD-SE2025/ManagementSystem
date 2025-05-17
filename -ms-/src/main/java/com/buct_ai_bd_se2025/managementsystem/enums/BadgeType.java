package com.buct_ai_bd_se2025.managementsystem.enums;

public enum BadgeType {
    DOT("dot"),
    NORMAL("normal");

    private final String value;

    BadgeType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static BadgeType fromValue(String value) {
        for (BadgeType type : values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return NORMAL; // 默认值
    }

}

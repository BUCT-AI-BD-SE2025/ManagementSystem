package fun.yozora.admin.core.enums;

public enum BadgeVariants {
    DEFAULT("default"),
    DESTRUCTIVE("destructive"),
    PRIMARY("primary"),
    SUCCESS("success"),
    WARNING("warning");

    private final String value;

    BadgeVariants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    // 可选：根据字符串值获取枚举实例
    public static BadgeVariants fromValue(String value) {
        for (BadgeVariants variant : values()) {
            if (variant.getValue().equals(value)) {
                return variant;
            }
        }
        return SUCCESS; // 默认值
    }
}

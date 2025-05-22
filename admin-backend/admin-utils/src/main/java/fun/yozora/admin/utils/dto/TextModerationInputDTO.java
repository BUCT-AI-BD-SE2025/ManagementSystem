package fun.yozora.admin.utils.dto;

import lombok.Data;

@Data
public class TextModerationInputDTO {
    private String action;
    private String version;
    private String region;
    private String content;
    private String bizType;

}

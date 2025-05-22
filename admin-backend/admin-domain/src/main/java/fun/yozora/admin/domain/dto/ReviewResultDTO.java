package fun.yozora.admin.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class ReviewResultDTO {
    private List<String> ids;
    private String status;
    private String reason;
}
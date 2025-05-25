package fun.yozora.admin.domain.dto;

import lombok.Data;

@Data
public class ArtifactDTO {

    private String id;
    private String originId;

    private String title;
    private String url;
    private String image;
    private String location;
    private String period;
    private String material;
    private String measurement;
    private String artist;
    private String creditLine;
    private String type;
    private String description;
    private String illusion;
}

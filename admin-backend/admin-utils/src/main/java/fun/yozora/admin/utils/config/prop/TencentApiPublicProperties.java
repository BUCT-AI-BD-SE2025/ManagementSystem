package fun.yozora.admin.utils.config.prop;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class TencentApiPublicProperties {
    private String domainName;
    private String region;
    private String secretId;
    private String secretKey;
}
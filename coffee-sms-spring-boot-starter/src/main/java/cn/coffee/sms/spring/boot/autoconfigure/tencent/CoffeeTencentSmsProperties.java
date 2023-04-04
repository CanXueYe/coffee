package cn.coffee.sms.spring.boot.autoconfigure.tencent;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "coffee.tencent-sms")
public class CoffeeTencentSmsProperties {

    private String secretId;
    private String secretKey;
    private String sdkAppId;
    private String signName;

}

package cn.coffee.sms.spring.boot.autoconfigure.aliyun;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@Data
@ConfigurationProperties(prefix = "coffee.ali-sms")
public class CoffeeAliSmsProperties {

    /**
     * AccessKey ID
     */
    private String accessKeyId;

    private String accessKeySecret;

    private String signName;
}

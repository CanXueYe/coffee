package cn.coffee.oss.spring.boot.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@Data
@ConfigurationProperties(prefix = "coffee.ali-oss")
public class CoffeeAliOssProperties {

    private String accessKeyId;

    private String accessKeySecret;

    private String endpoint;

    private String bucketDefault="";

    private Map<String, bucketInfo> bucketList;

    @Data
    public static class bucketInfo{
        private String title="";
        private List<String> modules;
    }
}

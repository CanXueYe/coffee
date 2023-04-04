package cn.coffee.oss.spring.boot.autoconfigure;

import cn.coffee.oss.aliyun.AliOssSupport;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 对象存储自动配置
 *
 */
@Configuration
@EnableConfigurationProperties(CoffeeAliOssProperties.class)
public class CoffeeOssAutoConfig {

    private static final Logger log = LoggerFactory.getLogger(CoffeeOssAutoConfig.class);

    private CoffeeAliOssProperties aliOssProperties;

    public CoffeeOssAutoConfig(CoffeeAliOssProperties aliOssProperties){
        super();
        this.aliOssProperties = aliOssProperties;
    }

    @Bean
    public AliOssSupport aliOssSupport() {
        if(aliOssProperties!=null){
            log.info("......................................");
            log.info("*** aliOss 配置信息...");
            log.info("*** accessKeyId：{}", aliOssProperties.getAccessKeyId());
            log.info("*** accessKeySecret:{}", "******");
            log.info("*** endPoint:{}", aliOssProperties.getEndpoint());
            log.info("......................................");
        }
        AliOssSupport aliOssSupport = new AliOssSupport(
                aliOssProperties.getEndpoint(),
                aliOssProperties.getAccessKeyId(),
                aliOssProperties.getAccessKeySecret(),
                aliOssProperties.getBucketDefault(),
                JSONObject.toJSONString(aliOssProperties.getBucketList()));
        return aliOssSupport;
    }
}

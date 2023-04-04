package cn.coffee.sms.spring.boot.autoconfigure.aliyun;

import cn.coffee.sms.aliyun.AliSmsSupport;
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
@EnableConfigurationProperties(CoffeeAliSmsProperties.class)
public class CoffeeSmsAliAutoConfig {

    private static final Logger log = LoggerFactory.getLogger(CoffeeSmsAliAutoConfig.class);

    private CoffeeAliSmsProperties aliSmsProperties;

    public CoffeeSmsAliAutoConfig(CoffeeAliSmsProperties aliSmsProperties){
        super();
        this.aliSmsProperties = aliSmsProperties;
    }

    @Bean
    public AliSmsSupport aliSmsSupport() {
        if(aliSmsProperties!=null){
            log.info("......................................");
            log.info("*** aliSms 配置信息...");
            log.info("*** accessKeyId：{}", aliSmsProperties.getAccessKeyId());
            log.info("*** accessKeySecret:{}", "******");
            log.info("*** signName:{}", aliSmsProperties.getSignName());
            log.info("......................................");
        }
        AliSmsSupport aliSmsSupport = new AliSmsSupport(
                aliSmsProperties.getAccessKeyId(),
                aliSmsProperties.getAccessKeySecret(),
                aliSmsProperties.getSignName());
        return aliSmsSupport;
    }
}

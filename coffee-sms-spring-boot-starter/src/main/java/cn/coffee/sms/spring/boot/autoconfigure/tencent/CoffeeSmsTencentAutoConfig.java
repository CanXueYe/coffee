package cn.coffee.sms.spring.boot.autoconfigure.tencent;

import cn.coffee.sms.tencent.TencentSupport;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 对象存储自动配置
 *
 */
@Log4j2
@Configuration
@EnableConfigurationProperties(CoffeeTencentSmsProperties.class)
public class CoffeeSmsTencentAutoConfig {

    private CoffeeTencentSmsProperties tencentSmsProperties;

    public CoffeeSmsTencentAutoConfig(CoffeeTencentSmsProperties tencentSmsProperties){
        super();
        this.tencentSmsProperties = tencentSmsProperties;
    }

    @Bean
    public TencentSupport tencentSupport() {
        if(tencentSmsProperties!=null){
            log.info("......................................");
            log.info("*** TencentSms 配置信息...");
            log.info("*** secretId：{}", tencentSmsProperties.getSecretId());
            log.info("*** secretKey:{}", "******");
            log.info("*** sdkAppId:{}", tencentSmsProperties.getSdkAppId());
            log.info("*** signName:{}", tencentSmsProperties.getSignName());
            log.info("......................................");
        }
        TencentSupport tencentSmsSupport = new TencentSupport(
                tencentSmsProperties.getSecretId(),
                tencentSmsProperties.getSecretKey(),
                tencentSmsProperties.getSdkAppId(),
                tencentSmsProperties.getSignName());
        return tencentSmsSupport;
    }
}

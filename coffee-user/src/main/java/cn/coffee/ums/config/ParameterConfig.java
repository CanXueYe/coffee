package cn.coffee.ums.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Log4j2
@Configuration
@EnableConfigurationProperties(ParameterParam.class)
public class ParameterConfig {

    private ParameterParam param;

    public ParameterConfig(ParameterParam param){
        super();
        this.param = param;
    }

    @Bean(name = "param")
    public ParameterParam paramSupport() {
        if(param!=null){
            log.info("......................................");
            log.info("*** user 配置信息...");
            log.info("*** aes-key：{}", param.getAesKay());
            log.info("......................................");
        }
        ParameterParam paramSupport = new ParameterParam(
                param.getAesKay()
        );
        return paramSupport;
    }
}

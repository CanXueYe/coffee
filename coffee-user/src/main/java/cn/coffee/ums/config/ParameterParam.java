package cn.coffee.ums.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "coffee")
public class ParameterParam {

    private String aesKay;
}

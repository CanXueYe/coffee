package cn.coffee.sms.aliyun;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AliSmsSupport {

    /**
     * AccessKey ID
     */
    private String accessKeyId;

    private String accessKeySecret;

    private String SignName;


}

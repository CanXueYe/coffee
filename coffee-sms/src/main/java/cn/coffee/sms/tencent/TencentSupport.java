package cn.coffee.sms.tencent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TencentSupport {

    private String secretId;
    private String secretKey;
    private String sdkAppId;
    private String signName;



}

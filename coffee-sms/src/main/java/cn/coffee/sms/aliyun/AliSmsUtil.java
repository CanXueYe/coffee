package cn.coffee.sms.aliyun;

import cn.coffee.common.spring.SpringContextUtil;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;

import static com.aliyun.teautil.Common.assertAsString;

@Log4j2
public class AliSmsUtil {

    private AliSmsSupport getAliSmsSupport() {
        return (SpringContextUtil.getApplicationContext().getBean(AliSmsSupport.class));
    }

    /**
     * 发送短信
     * @param phone
     * @param templateCode
     * @param param
     * @return
     * @throws Exception
     */
    public Boolean sendAliSms(String phone,String templateCode,String param) throws Exception {
        Client client = AliSmsUtil.createClient(getAliSmsSupport().getAccessKeyId(), getAliSmsSupport().getAccessKeySecret());
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setSignName(getAliSmsSupport().getSignName())
                .setTemplateCode(templateCode)
                .setPhoneNumbers(phone)
                .setTemplateParam(param);
        RuntimeOptions runtime = new RuntimeOptions();
        try {
            SendSmsResponse response=client.sendSmsWithOptions(sendSmsRequest, runtime);
            log.info("======AliSms发送结果======\n"+new Gson().toJson(response.body));
            return true;
        } catch (TeaException error) {
            log.error(error);
            assertAsString(error.message);
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            log.error(_error);
            assertAsString(error.message);
        }
        return false;
    }



    public static Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config()
                // 您的 AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 您的 AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new Client(config);
    }



}

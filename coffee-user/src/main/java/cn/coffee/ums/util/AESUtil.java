package cn.coffee.ums.util;

import cn.coffee.ums.config.ParameterParam;
import cn.dev33.satoken.secure.SaSecureUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * AES加密工具类
 */
@Log4j2
@Component
public class AESUtil implements CommandLineRunner {

    @Autowired
    private ParameterParam param;

    // 定义秘钥
    private static String key;

    /**
     * 密码加密
     * @param rawPwd
     * @return
     */
    public static String cipherPwd(String rawPwd){
        // 加密
        String cipher = SaSecureUtil.aesEncrypt(key, rawPwd);
        return cipher;
    }

    /**
     * 校验密码
     * @param rawPwd
     * @param cipherPwd
     * @return
     */
    public static boolean checkPwd(String rawPwd,String cipherPwd){
        // 解密
        String decode = SaSecureUtil.aesDecrypt(key, cipherPwd);
        if(rawPwd.equals(decode)){
            return true;
        }
        return false;
    }

    @Override
    public void run(String... args) throws Exception {
        /*System.out.println(">>>>>>>>>>>>>>>服务启动执行，执行加载数据等操作<<<<<<<<<<<<<");
        System.out.println(param);*/
        key=param.getAesKay();
    }
}

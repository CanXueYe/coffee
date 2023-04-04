package cn.coffee.ums.util;

import cn.dev33.satoken.secure.SaSecureUtil;

/**
 * RSA加密工具类
 */
public class RSAUtil {

    // 定义私钥和公钥
    final static String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAO+wmt01pwm9lHMdq7A8gkEigk0XKMfjv+4IjAFhWCSiTeP7dtlnceFJbkWxvbc7Qo3fCOpwmfcskwUc3VSgyiJkNJDs9ivPbvlt8IU2bZ+PBDxYxSCJFrgouVOpAr8ar/b6gNuYTi1vt3FkGtSjACFb002/68RKUTye8/tdcVilAgMBAAECgYA1COmrSqTUJeuD8Su9ChZ0HROhxR8T45PjMmbwIz7ilDsR1+E7R4VOKPZKW4Kz2VvnklMhtJqMs4MwXWunvxAaUFzQTTg2Fu/WU8Y9ha14OaWZABfChMZlpkmpJW9arKmI22ZuxCEsFGxghTiJQ3tK8npj5IZq5vk+6mFHQ6aJAQJBAPghz91Dpuj+0bOUfOUmzi22obWCBncAD/0CqCLnJlpfOoa9bOcXSusGuSPuKy5KiGyblHMgKI6bq7gcM2DWrGUCQQD3SkOcmia2s/6i7DUEzMKaB0bkkX4Ela/xrfV+A3GzTPv9bIBamu0VIHznuiZbeNeyw7sVo4/GTItq/zn2QJdBAkEA8xHsVoyXTVeShaDIWJKTFyT5dJ1TR++/udKIcuiNIap34tZdgGPI+EM1yoTduBM7YWlnGwA9urW0mj7F9e9WIQJAFjxqSfmeg40512KP/ed/lCQVXtYqU7U2BfBTg8pBfhLtEcOg4wTNTroGITwe2NjL5HovJ2n2sqkNXEio6Ji0QQJAFLW1Kt80qypMqot+mHhS+0KfdOpaKeMWMSR4Ij5VfE63WzETEeWAMQESxzhavN1WOTb3/p6icgcVbgPQBaWhGg==";
    final static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDvsJrdNacJvZRzHauwPIJBIoJNFyjH47/uCIwBYVgkok3j+3bZZ3HhSW5Fsb23O0KN3wjqcJn3LJMFHN1UoMoiZDSQ7PYrz275bfCFNm2fjwQ8WMUgiRa4KLlTqQK/Gq/2+oDbmE4tb7dxZBrUowAhW9NNv+vESlE8nvP7XXFYpQIDAQAB";

    /**
     * 密码加密
     * @param rawPwd
     * @return
     */
    public static String cipher(String rawPwd){
        // 使用公钥加密
        String ciphertext = SaSecureUtil.rsaEncryptByPublic(publicKey, rawPwd);
        System.out.println("公钥加密后：" + ciphertext);
        return ciphertext;
    }

    /**
     * 校验密码
     * @param rawPwd
     * @param cipher
     * @return
     */
    public static boolean checkPwd(String rawPwd,String cipher){
        // 使用私钥解密
        String text2 = SaSecureUtil.rsaDecryptByPrivate(privateKey, cipher);
        System.out.println("私钥解密后：" + text2);
        if(rawPwd.equals(text2)){
            return true;
        }
        return false;

    }

    /*public static void main(String[] args) throws Exception {
        // 生成一对公钥和私钥，其中Map对象 (private=私钥, public=公钥)
        //System.out.println(SaSecureUtil.rsaGenerateKeyPair());
        String p=cipher("123456");
        System.out.println(p);

        System.out.println(checkPwd("123456",p));
    }*/

}

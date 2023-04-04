package cn.coffee.common.encry;

import cn.coffee.common.exception.BizException;

import java.security.MessageDigest;

/**
 * MD5 加密
 */
public class MD5Util {
    public static final String KEY_MD5 = "MD5";
    private static final char[] HEX = "0123456789abcdef".toCharArray();

    /***
     * MD5加密（生成唯一的MD5值）
     * @param data
     * @return
     * @throws Exception
     */
    public static String encryMD5(byte[] data) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
        md5.update(data);
        byte[] digest=md5.digest();

        return hex(digest);
    }

    /**
     * 校验密码
     * @param rawPassword
     * @param encryPassword
     * @return
     */
    public static boolean checkPassword(String rawPassword,String encryPassword){
        try{
            String encryPwd = encryMD5(rawPassword.getBytes());
            if(encryPassword.equals(encryPwd)){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            throw new BizException("密码校验失败，请联系管理员！");
        }
    }

    public static void main(String[] args) throws Exception {
        String get1=encryMD5("123456".getBytes());
        String get2=encryMD5(get1.getBytes());
        System.out.println(get1);
        System.out.println(get2);

        System.out.println(checkPassword("123456",get1));
    }


    public static String hex(byte[] bytes) {
        if (bytes == null)
            return null;

        char[] chars = new char[bytes.length << 1];
        for (int i = 0; i < bytes.length; i++) {
            int n = i << 1;
            chars[n] = HEX[bytes[i] >> 4 & 0xf];
            chars[n + 1] = HEX[bytes[i] & 0xf];
        }

        return new String(chars);
    }
}

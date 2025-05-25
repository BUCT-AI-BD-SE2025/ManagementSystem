package fun.yozora.admin.app.utils;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

public class JasyptUtils {

    /**
     * 加密
     * @param password 加密盐值
     * @param text    需要加密的字符串
     * @return 加密后的字符串
     */
    public static String encrypt(String password, String text) {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setConfig(cryptor(password));
        return encryptor.encrypt(text);
    }

    /**
     * 解密
     * @param password 加密盐值
     * @param text    需要解密的字符串
     * @return 解密后的字符串
     */
    public static String decrypt(String password, String text) {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setConfig(cryptor(password));
        return encryptor.decrypt(text);
    }

    /**
     * 配置（对应yml中的配置）
     * @param password 加密盐值
     * @return SimpleStringPBEConfig
     */
    public static SimpleStringPBEConfig cryptor(String password) {
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        //设置盐值
        config.setPassword(password);
        //设置算法配置信息
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setProviderName("SunJCE");
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        config.setPoolSize("1");
        return config;
    }


    public static void main(String[] args) {
        // 加密
        String password = "";
        String text = "";
        String encryptStr = encrypt(password, text);
        // 解密
        String decryptStr = decrypt(password, encryptStr);
        System.out.println("加密后:" + encryptStr);
        System.out.println("解密后:" + decryptStr);
    }
}
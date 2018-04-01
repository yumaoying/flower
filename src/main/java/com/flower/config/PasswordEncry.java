package com.flower.config;

import com.flower.entity.UserInfo;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * Created by yumaoying on 2018/4/1.
 * 密码加密
 */
public class PasswordEncry {
    private String algorithmName = "md5"; //加密算法
    private int hashIterations = 2; //加密次数


    /***
     *生成随机salt
     * @param userInfo 对密码进行加密
     */
    public void encryPassword(UserInfo userInfo) {
        RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
        String salt = randomNumberGenerator.nextBytes().toHex();
        userInfo.setSalt(salt);
        String newPassword = new SimpleHash(algorithmName, userInfo.getPassword(), ByteSource.Util.bytes(userInfo.credentialsSalt()), hashIterations).toHex();
        userInfo.setPassword(newPassword);
    }

//    public static void main(String[] args) {
//        PasswordEncry passwordEncry = new PasswordEncry();
//        UserInfo userInfo = new UserInfo();
//        userInfo.setUid(1);
//        userInfo.setUsername("admin");
//        userInfo.setPassword("123456");
//        // userInfo.setState(new Byte("0"));
//        passwordEncry.encryPassword(userInfo);
//        System.out.println(userInfo);
//    }
}

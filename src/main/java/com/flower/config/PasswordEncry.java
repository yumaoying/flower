package com.flower.config;

import com.flower.entity.SysUser;
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
     * @param sysUser 对密码进行加密
     */
    public void encryPassword(SysUser sysUser) {
        RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
        String salt = randomNumberGenerator.nextBytes().toHex();
        sysUser.setSalt(salt);
        String newPassword = new SimpleHash(algorithmName, sysUser.getPassword(), ByteSource.Util.bytes(sysUser.getSalt()), hashIterations).toHex();
        sysUser.setPassword(newPassword);
    }

    public static void main(String[] args) {
        PasswordEncry passwordEncry = new PasswordEncry();
        SysUser userInfo = new SysUser();
        userInfo.setUid(1);
        userInfo.setUsername("admin");
        userInfo.setPassword("123456");
        userInfo.setState(1);
        passwordEncry.encryPassword(userInfo);
        System.out.println(userInfo);
    }
}

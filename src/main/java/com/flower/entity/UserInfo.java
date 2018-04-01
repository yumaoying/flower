package com.flower.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by yumaoying on 2018/3/24.
 */
@Entity
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 7775362148393562352L;
    @Id
    @GeneratedValue
    private Integer uid;
    @Column(unique = true)
    private String username;//帐号
    private String name;//名称（昵称或者真实姓名)
    private String password; //密码;
    private String salt;//加密密码的salt
    private byte state;//用户状态,0:创建未认证（比如没有激活，没有输入验证码等等）--等待验证的用户 , 1:

    @ManyToMany(fetch = FetchType.EAGER) //立即从数据库中进行加载数据;
    @JoinTable(name = "SysUserRole", joinColumns = {@JoinColumn(name = "uid")}, inverseJoinColumns = {@JoinColumn(name = "roleId")})
    private List<SysRole> roleList;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }

    @JsonBackReference //解决无限递归
    public List<SysRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<SysRole> roleList) {
        this.roleList = roleList;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", state=" + state +
                ", roleList=" + roleList +
                '}';
    }

    /**
     * 密码salt
     * 不要设置成get方法，做缓存时使用
     * 确定名为get...的方法在Jackson2JsonRedisSerializer使用中 会被序列化成一个属性值到json字符串中。
     * 重新对salt重新进行了定义，用户名+salt，这样就更加不容易被破解
     *
     * @return
     */
    public String credentialsSalt() {
        //return this.salt;
        return this.username + this.salt;
    }
}

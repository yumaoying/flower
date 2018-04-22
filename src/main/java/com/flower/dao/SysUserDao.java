package com.flower.dao;

import com.flower.entity.SysPermission;
import com.flower.entity.SysRole;
import com.flower.entity.SysUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by yumaoying on 2018/3/24.
 */
public interface SysUserDao extends JpaRepository<SysUser, Integer>, JpaSpecificationExecutor<SysUser> {
    /***
     * 通过用户名查找用户信息
     * @param username 用户名
     * @return 用户信息
     */
    public SysUser findByUsername(String username);

    /***
     * 通过用户名查找用户信息
     * @param id 用户id
     * @return 用户信息
     */
    public SysUser findByUid(Integer id);

    public void delete(Integer integer);

    Page<SysUser> findAll(Specification<SysUser> spec, Pageable pageable);

    /**
     * 根据用户id查找角色
     *
     * @param id uid
     * @return 角色
     */
    @Query(value = "select u.roleList from SysUser u where u.id=?1")
    public List<SysRole> findRoleListByUid(Integer id);

    /***
     *修改用户信息
     * @param uid uid
     * @param username 账户名
     * @param name 真实姓名
     */
    @Modifying
    @Query(value = "update SysUser u set u.username=?2,u.name=?3,u.state=?4 where u.uid=?1")
    public void edit(Integer uid, String username, String name, Integer state);

    /***
     * 保存用户角色
     * @param uid 用户id
     * @param rid 角色id
     */
    @Modifying
    @Query(value = "insert into sys_user_role(uid,role_id) values(?1,?2)", nativeQuery = true)
    public void saveUserRoles(Integer uid, Integer rid);

    /***
     * 删除用户角色
     * @param uid 用户id
     */
    @Modifying
    @Query(value = "delete from sys_user_role  where  uid=?1", nativeQuery = true)
    public void deleteUserRoleByUid(Integer uid);

    @Modifying
    @Query(value = "update SysUser u set u.password=?2,u.salt=?3 where u.uid=?1")
    public int editPassword(Integer uid, String password, String salt);
}


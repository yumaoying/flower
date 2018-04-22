package com.flower.service.impl;

import com.flower.entity.SysRole;
import com.flower.entity.SysUser;
import com.flower.dao.SysUserDao;

import com.flower.service.SysUserService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yumaoying on 2018/3/24.
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    @Resource
    private SysUserDao sysUserDao;

    @Override
    public SysUser findByUsername(String userName) {
        return sysUserDao.findByUsername(userName);
    }

    @Override
    public SysUser findByUid(Integer id) {
        return sysUserDao.findByUid(id);
    }


    @Override
    public void save(SysUser sysUser) {
        sysUserDao.save(sysUser);
    }


    @Override
    public void del(Integer id) {
        sysUserDao.delete(id);
    }

    @Override
    public Page<SysUser> getUserInfoPage(SysUser sysUser, Pageable pageable) {
        Specification<SysUser> specification = new Specification<SysUser>() {
            /**
             * 构造断言
             * @param root 实体对象引用
             * @param criteriaQuery 规则查询对象
             * @param cb 规则构建对象
             * @return 断言
             */
            @Override
            public Predicate toPredicate(Root<SysUser> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>(); //所有的断言
                if (sysUser.getUsername() != null && !sysUser.getUsername().trim().isEmpty()) {
                    predicates.add(cb.like(root.get("username").as(String.class), "%" + sysUser.getUsername() + "%"));
                }
                if (sysUser.getUid() != null && !sysUser.getUid().toString().trim().isEmpty()) {
                    predicates.add(cb.equal(root.get("uid").as(Integer.class), sysUser.getUid()));
                }
                if (sysUser.getState() != null) {
                    predicates.add(cb.equal(root.get("state").as(Integer.class), sysUser.getState()));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return sysUserDao.findAll(specification, pageable);
    }

    public List<SysRole> findRoleListByUid(Integer uid) {
        return sysUserDao.findRoleListByUid(uid);
    }

    @Transactional
    public void edit(Integer uid, String username, String name, Integer state) {
        sysUserDao.edit(uid, username, name, state);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {Exception.class})
    @CacheEvict
    public void addUserRole(Integer uid, Integer[] roleId) {
        //先删除原有的角色
        sysUserDao.deleteUserRoleByUid(uid);
        //重新插入角色
        for (int i = 0; i < roleId.length; i++)
            sysUserDao.saveUserRoles(uid, roleId[i]);
    }

    @Transactional
    @CacheEvict
    public int editPassword(Integer uid, String password, String salt) {
        return sysUserDao.editPassword(uid, password, salt);
    }
}

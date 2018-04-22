package com.flower.service.impl;


import com.flower.dao.SysRoleDao;
import com.flower.entity.SysPermission;
import com.flower.entity.SysRole;
import com.flower.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yumaoying on 2018/4/15.
 * 系统角色Service
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleDao sysRoleDao;

    //分页查询
    public Page<SysRole> findAll(SysRole sysRole, Pageable pageable) {
        Specification<SysRole> specification = new Specification<SysRole>() {
            Predicate predicate = null;

            @Override
            public Predicate toPredicate(Root<SysRole> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>(); //所有的断言
                if (sysRole.getId() != null && !sysRole.getId().toString().trim().isEmpty()) {
                    predicate = cb.equal(root.get("id").as(Integer.class), sysRole.getId());
                    predicates.add(predicate);
                }
                if (sysRole.getRole() != null && !sysRole.getRole().toString().trim().isEmpty()) {
                    predicate = cb.like(root.get("role").as(String.class), "%" + sysRole.getRole() + "%");
                    predicates.add(predicate);
                }
                if (sysRole.getDescription() != null && !sysRole.getDescription().toString().trim().isEmpty()) {
                    predicate = cb.like(root.get("description").as(String.class), "%" + sysRole.getDescription() + "%");
                    predicates.add(predicate);
                }
                if (sysRole.getAvailable() != null && !sysRole.getAvailable().toString().trim().isEmpty()) {
                    predicate = cb.equal(root.get("available").as(Integer.class), sysRole.getAvailable());
                    predicates.add(predicate);
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return sysRoleDao.findAll(specification, pageable);
    }

    @Transactional
    @CacheEvict
    public void del(Integer id) {
        sysRoleDao.delete(id);
    }

    @Transactional
    public void add(SysRole sysRole) {
        sysRoleDao.save(sysRole);
    }

    @Transactional
    @CacheEvict
    public void edit(SysRole sysRole) {
        sysRoleDao.save(sysRole);
    }

    public SysRole findByRole(String role) {
        return sysRoleDao.findByRole(role);
    }

    @Override
    public List<SysRole> findAll() {
        return sysRoleDao.findAll();
    }

    @Override
    public SysRole findById(Integer id) {
        return sysRoleDao.findById(id);
    }

    // Propagation.REQUIRED 如果有事务,那么加入事务,没有的话新建一个(不写的情况下) readOnly-可写，针对增删改操作
    //让checked例外也回滚，一般只有RuntimeException会回滚，加上可都回滚
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {Exception.class})
//    @CacheEvict
    public void savePermisssion(Integer rid, String permissions) {
        //先删除权限，再插入
        sysRoleDao.deletePermisssionByRid(rid);
        String[] perssionstr = permissions.split(",");
        for (int i = 0; i < perssionstr.length; i++) {
            Integer permissionId = Integer.parseInt(perssionstr[i]);
            sysRoleDao.savePermission(rid, permissionId);
        }
    }

    //根据角色id查找权限
    public List<SysPermission> findPermissionsById(Integer id) {
        return sysRoleDao.findPermissionsById(id);
    }
}

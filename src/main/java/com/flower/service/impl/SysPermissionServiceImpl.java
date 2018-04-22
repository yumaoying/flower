package com.flower.service.impl;

import com.flower.dao.SysPermissionDao;
import com.flower.entity.SysPermission;
import com.flower.service.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yumaoying on 2018/4/22.
 */
@Service
public class SysPermissionServiceImpl implements SysPermissionService {

    @Autowired
    private SysPermissionDao sysPermissionDao;

    /***
     * 查找权限，按sort排序
     * @return
     */
    @Override
    public List<SysPermission> findByParentIdAfterOrderBySortAsc(Integer pid) {
        return sysPermissionDao.findByParentIdAfterOrderBySortAsc(pid);
    }

    public Page<SysPermission> getPermissionPages(SysPermission permission, Pageable pageable) {
        Specification<SysPermission> specification = new Specification<SysPermission>() {
            @Override
            public Predicate toPredicate(Root<SysPermission> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>(); //所有的断言
                if (permission.getId() != null && !permission.getId().toString().trim().isEmpty()) {
                    predicates.add(cb.equal(root.get("id").as(Integer.class), permission.getId()));
                }
                if (permission.getName() != null && !permission.getName().toString().trim().isEmpty()) {
                    predicates.add(cb.like(root.get("name").as(String.class), "%" + permission.getName() + "%"));
                }
                if (permission.getPermission() != null && !permission.getPermission().toString().trim().isEmpty()) {
                    predicates.add(cb.like(root.get("permission").as(String.class), "%" + permission.getName() + "%"));
                }
                if (permission.getResourceType() != null) {
                    predicates.add(cb.equal(root.get("resourceType").as(Integer.class), permission.getResourceType()));
                }
                if (permission.getAvailable() != null && !permission.getAvailable().toString().trim().isEmpty()) {
                    predicates.add(cb.equal(root.get("available").as(Integer.class), permission.getAvailable()));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return sysPermissionDao.findAll(specification, pageable);
    }

    @Transactional
    public void add(SysPermission permission) {
        sysPermissionDao.save(permission);
    }

    @Transactional
    public void edit(SysPermission permission) {
        sysPermissionDao.save(permission);
    }

    @Transactional
    public void delete(Integer id) {
        sysPermissionDao.delete(id);
    }

    public SysPermission findById(Integer id) {
        return sysPermissionDao.findById(id);
    }
}

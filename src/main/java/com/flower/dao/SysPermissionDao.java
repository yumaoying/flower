package com.flower.dao;

import com.flower.entity.SysPermission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by yumaoying on 2018/4/22.
 */
public interface SysPermissionDao extends JpaRepository<SysPermission, Integer>, JpaSpecificationExecutor<SysPermission> {

    //查询权限信息
    public List<SysPermission> findByParentIdAfterOrderBySortAsc(Integer pid);

    public Page<SysPermission> findAll(Specification specification, Pageable pageable);

    public SysPermission save(SysPermission permission);

    public void delete(Integer id);

    public SysPermission findById(Integer id);
}

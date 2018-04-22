package com.flower.dao;

import com.flower.entity.SysPermission;
import com.flower.entity.SysRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by yumaoying on 2018/4/15.
 * SysRoleDao
 */
public interface SysRoleDao extends JpaRepository<SysRole, Integer>, JpaSpecificationExecutor<SysRole> {
    //条件分页查询
    Page<SysRole> findAll(Specification<SysRole> spec, Pageable pageable);

    public void delete(Integer integer);

    public SysRole save(SysRole sysRole);

    public SysRole findByRole(String role);

    public List<SysRole> findAll();

    public SysRole findById(Integer id);

    @Modifying
    @Query(value = "delete from sys_role_permission  where  role_id=?1", nativeQuery = true)
    public void deletePermisssionByRid(Integer rid);

    @Modifying
    @Query(value = "insert into sys_role_permission(role_id,permission_id) values(?1,?2)", nativeQuery = true)
    public void savePermission(Integer rid, Integer permissionIds);

    @Query(value = "select r.permissions from SysRole r where r.id=?1")
    public List<SysPermission> findPermissionsById(Integer id);
}

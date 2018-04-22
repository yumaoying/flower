package com.flower.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by yumaoying on 2018/3/24.
 */
@Entity
public class SysPermission implements Serializable {

    private static final long serialVersionUID = -3189424055261363207L;
    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "name")
    private String name; //资源名称
    @Column(name = "resource_type")
    private Integer resourceType;///资源类型，[menu|button]  1:菜单2：按钮
    @Column(name = "url")
    private String url; //资源路径
    @Column(name = "permission")
    private String permission; //权限字符串 menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
    @Column(name = "parent_id")
    private Integer parentId;//父编号
    @Column(name = "sort")
    private Integer sort; //排序
    @Column(name = "available")
    private Integer available;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "SysRolePermission", joinColumns = {@JoinColumn(name = "permissionId")}, inverseJoinColumns = {@JoinColumn(name = "roleId")})
    private List<SysRole> roles;

    @Transient
    private String checked;//是否选中

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getResourceType() {
        return resourceType;
    }

    public void setResourceType(Integer resourceType) {
        this.resourceType = resourceType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    @JsonBackReference
    public List<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRole> roles) {
        this.roles = roles;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "SysPermission{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", resourceType=" + resourceType +
                ", url='" + url + '\'' +
                ", permission='" + permission + '\'' +
                ", parentId=" + parentId +
                ", sort=" + sort +
                ", available=" + available +
                ", checked='" + checked + '\'' +
                '}';
    }
}

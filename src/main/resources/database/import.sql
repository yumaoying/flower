
--------系统管理员表，密码为加密后数据，加密前数据为123456
insert into `sys_user` (`uid`, `username`, `password`, `salt`, `state`, `name`) values('1','admin','7085ab7c91470f35517638d67dc284da','5d114997f3dab23a7220c6b5d7db7a8b','1','管理员');


------------系统角色表
insert into `sys_role` (`id`, `available`, `description`, `role`) values('1','1','超级管理员','superadmin');
insert into `sys_role` (`id`, `available`, `description`, `role`) values('2','1','管理员','admin');
insert into `sys_role` (`id`, `available`, `description`, `role`) values('3','1','test','test');
insert into `sys_role` (`id`, `available`, `description`, `role`) values('5','1','对会员有操作权限','role2');
insert into `sys_role` (`id`, `available`, `description`, `role`) values('7','1','role5','role5');
insert into `sys_role` (`id`, `available`, `description`, `role`) values('8','1','role3','role3');
insert into `sys_role` (`id`, `available`, `description`, `role`) values('9','1','user4','user4');
insert into `sys_role` (`id`, `available`, `description`, `role`) values('10','1','user7','user7');
insert into `sys_role` (`id`, `available`, `description`, `role`) values('11','1','user6','user6');


----------系统权限表
insert into `sys_permission` (`id`, `name`, `permission`, `parent_id`, `available`, `resource_type`, `url`, `sort`) values('1','系统管理','/system','0','1','0','/system','1');
insert into `sys_permission` (`id`, `name`, `permission`, `parent_id`, `available`, `resource_type`, `url`, `sort`) values('2','用户管理','/sysUser','1','1','1','/sysUser','2');
insert into `sys_permission` (`id`, `name`, `permission`, `parent_id`, `available`, `resource_type`, `url`, `sort`) values('3','角色管理','/role','1','1','1','/role','3');
insert into `sys_permission` (`id`, `name`, `permission`, `parent_id`, `available`, `resource_type`, `url`, `sort`) values('4','权限管理','/permission','1','1','1','/permission','4');
insert into `sys_permission` (`id`, `name`, `permission`, `parent_id`, `available`, `resource_type`, `url`, `sort`) values('5','会员管理','/customer','1','1','1','/customer','5');
insert into `sys_permission` (`id`, `name`, `permission`, `parent_id`, `available`, `resource_type`, `url`, `sort`) values('6','商品管理','/goods','1','1','1','/goods','6');
insert into `sys_permission` (`id`, `name`, `permission`, `parent_id`, `available`, `resource_type`, `url`, `sort`) values('7','商品类别管理','/category','1','1','1','/category','7');
insert into `sys_permission` (`id`, `name`, `permission`, `parent_id`, `available`, `resource_type`, `url`, `sort`) values('8','评价管理','/comments','1','1','1','/comments','8');
insert into `sys_permission` (`id`, `name`, `permission`, `parent_id`, `available`, `resource_type`, `url`, `sort`) values('9','公告管理','/notice','1','1','1','/notice','9');
insert into `sys_permission` (`id`, `name`, `permission`, `parent_id`, `available`, `resource_type`, `url`, `sort`) values('10','商品库存管理','/stock','1','1','1','/stock','10');
insert into `sys_permission` (`id`, `name`, `permission`, `parent_id`, `available`, `resource_type`, `url`, `sort`) values('11','供应商管理','/supplier','1','1','1','/supplier','11');
insert into `sys_permission` (`id`, `name`, `permission`, `parent_id`, `available`, `resource_type`, `url`, `sort`) values('12','统计模块','/statics','1','1','1','/statics','12');
insert into `sys_permission` (`id`, `name`, `permission`, `parent_id`, `available`, `resource_type`, `url`, `sort`) values('13','用户查询','/sysUser/users','2','1','2','/sysUser/add','13');
insert into `sys_permission` (`id`, `name`, `permission`, `parent_id`, `available`, `resource_type`, `url`, `sort`) values('14','用户添加','/sysUser/add','2','1','2','/sysUser/edit','14');
insert into `sys_permission` (`id`, `name`, `permission`, `parent_id`, `available`, `resource_type`, `url`, `sort`) values('15','用户修改','/sysUser/edit','2','1','2','/sysUser/delete','15');
insert into `sys_permission` (`id`, `name`, `permission`, `parent_id`, `available`, `resource_type`, `url`, `sort`) values('16','用户删除','/sysUser/delete','2','1','2','/sysUser/users','16');
insert into `sys_permission` (`id`, `name`, `permission`, `parent_id`, `available`, `resource_type`, `url`, `sort`) values('17','用户个人信息维护','/sysUser/loadUser','2','1','2','/sysUser/loadUser','17');
insert into `sys_permission` (`id`, `name`, `permission`, `parent_id`, `available`, `resource_type`, `url`, `sort`) values('18','角色查询','/role/roles','3','1','2','/role/roles','18');
insert into `sys_permission` (`id`, `name`, `permission`, `parent_id`, `available`, `resource_type`, `url`, `sort`) values('19','角色添加','/role/edit','3','1','2','/role/edit','19');
insert into `sys_permission` (`id`, `name`, `permission`, `parent_id`, `available`, `resource_type`, `url`, `sort`) values('20','角色修改','/role/edit','3','1','2','/role/edit','20');
insert into `sys_permission` (`id`, `name`, `permission`, `parent_id`, `available`, `resource_type`, `url`, `sort`) values('21','分配权限','/rolesavePermission','3','1','2','/rolesavePermission','22');
insert into `sys_permission` (`id`, `name`, `permission`, `parent_id`, `available`, `resource_type`, `url`, `sort`) values('22','角色删除','/role/delete','3','1','2','/role/delete','21');
insert into `sys_permission` (`id`, `name`, `permission`, `parent_id`, `available`, `resource_type`, `url`, `sort`) values('23','权限添加','/permission/add','4','0','2','/permission/add','23');
insert into `sys_permission` (`id`, `name`, `permission`, `parent_id`, `available`, `resource_type`, `url`, `sort`) values('24','权限修改','/permission/edit','4','1','2','/permission/edit','24');



------------用户-角色关系表
insert into `sys_user_role` (`uid`, `role_id`) values('1','1');



--------------角色-权限表
insert into `sys_role_permission` (`permission_id`, `role_id`) values('1','1');
insert into `sys_role_permission` (`permission_id`, `role_id`) values('2','1');
insert into `sys_role_permission` (`permission_id`, `role_id`) values('3','1');
insert into `sys_role_permission` (`permission_id`, `role_id`) values('4','1');
insert into `sys_role_permission` (`permission_id`, `role_id`) values('5','1');
insert into `sys_role_permission` (`permission_id`, `role_id`) values('6','1');
insert into `sys_role_permission` (`permission_id`, `role_id`) values('7','1');
insert into `sys_role_permission` (`permission_id`, `role_id`) values('8','1');
insert into `sys_role_permission` (`permission_id`, `role_id`) values('9','1');
insert into `sys_role_permission` (`permission_id`, `role_id`) values('10','1');
insert into `sys_role_permission` (`permission_id`, `role_id`) values('11','1');
insert into `sys_role_permission` (`permission_id`, `role_id`) values('12','1');
insert into `sys_role_permission` (`permission_id`, `role_id`) values('13','1');
insert into `sys_role_permission` (`permission_id`, `role_id`) values('14','1');
insert into `sys_role_permission` (`permission_id`, `role_id`) values('15','1');
insert into `sys_role_permission` (`permission_id`, `role_id`) values('16','1');
insert into `sys_role_permission` (`permission_id`, `role_id`) values('17','1');
insert into `sys_role_permission` (`permission_id`, `role_id`) values('18','1');
insert into `sys_role_permission` (`permission_id`, `role_id`) values('19','1');
insert into `sys_role_permission` (`permission_id`, `role_id`) values('20','1');
insert into `sys_role_permission` (`permission_id`, `role_id`) values('21','1');
insert into `sys_role_permission` (`permission_id`, `role_id`) values('22','1');
insert into `sys_role_permission` (`permission_id`, `role_id`) values('23','1');
insert into `sys_role_permission` (`permission_id`, `role_id`) values('24','1');

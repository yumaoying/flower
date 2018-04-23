/*
SQLyog Trial v11.01 (32 bit)
MySQL - 5.0.41-community-nt : Database - hua
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `car` */

DROP TABLE IF EXISTS `car`;

CREATE TABLE `car` (
  `car_id` int(11) NOT NULL COMMENT '购物车编号',
  `user_id` int(11) default NULL COMMENT '用户id',
  `goods_id` int(11) default NULL COMMENT '商品id',
  `mount` int(11) default NULL COMMENT '商品数量',
  PRIMARY KEY  (`car_id`),
  KEY `user_id` (`user_id`),
  KEY `goods_id` (`goods_id`),
  CONSTRAINT `car_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `car_ibfk_2` FOREIGN KEY (`goods_id`) REFERENCES `goods` (`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='购物车';

/*Data for the table `car` */

/*Table structure for table `category` */

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `category_id` int(11) NOT NULL auto_increment COMMENT '分类编号',
  `category_code` varchar(20) NOT NULL COMMENT '分类编码',
  `category_name` varchar(20) NOT NULL COMMENT '分类名称',
  `catagory_desc` varchar(200) default NULL COMMENT '分类描述',
  PRIMARY KEY  (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品分类';

/*Data for the table `category` */

/*Table structure for table `comments` */

DROP TABLE IF EXISTS `comments`;

CREATE TABLE `comments` (
  `comment_id` int(11) NOT NULL auto_increment COMMENT '评价id',
  `comment_title` varchar(50) default NULL COMMENT '评价标题',
  `comment_content` varchar(300) default NULL COMMENT '评价内容',
  `comment_date` datetime default NULL COMMENT '评价时间',
  `comment_user_id` int(11) default NULL COMMENT '评价用户id',
  `comment_goods_id` int(11) default NULL COMMENT '商品id',
  PRIMARY KEY  (`comment_id`),
  KEY `comment_user_id` (`comment_user_id`),
  CONSTRAINT `comments_ibfk_1` FOREIGN KEY (`comment_user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品评论';

/*Data for the table `comments` */

/*Table structure for table `customer` */

DROP TABLE IF EXISTS `customer`;

CREATE TABLE `customer` (
  `user_id` int(11) NOT NULL auto_increment,
  `user_address` varchar(50) default NULL,
  `user_birthday` varchar(10) default NULL,
  `user_core` int(11) default NULL,
  `user_eamil` varchar(50) default NULL,
  `user_img` varchar(50) default NULL,
  `user_last` varchar(20) default NULL,
  `user_level` varchar(20) default NULL,
  `user_name` varchar(50) NOT NULL,
  `user_pw` varchar(50) NOT NULL,
  `user_qq` varchar(50) default NULL,
  `user_realname` varchar(50) default NULL,
  `user_register` varchar(20) default NULL,
  `user_sex` varchar(5) default NULL,
  `user_tel` varchar(20) default NULL,
  PRIMARY KEY  (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `customer` */

/*Table structure for table `goods` */

DROP TABLE IF EXISTS `goods`;

CREATE TABLE `goods` (
  `goods_id` int(4) NOT NULL auto_increment COMMENT '商品编号',
  `goods_name` varchar(50) default NULL COMMENT '商品名称',
  `goods_desc` varchar(1000) default NULL COMMENT '商品描述',
  `goods_pic` varchar(50) default NULL COMMENT '商品图片',
  `goods_color` varchar(50) default NULL COMMENT '商品颜色',
  `goods_shijia` double default NULL COMMENT '商品市场价',
  `goods_price` double default NULL COMMENT '商品售价',
  `goods_catelog_id` int(11) default NULL COMMENT '商品类别id',
  `goods_purchase_price` double default NULL COMMENT '进货单价',
  `goods_discount` decimal(3,2) default NULL COMMENT '商品折扣',
  `goods_mean` varchar(1000) default NULL COMMENT '花语',
  PRIMARY KEY  (`goods_id`),
  KEY `goods_catelog_id` (`goods_catelog_id`),
  CONSTRAINT `goods_ibfk_1` FOREIGN KEY (`goods_catelog_id`) REFERENCES `category` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品信息';

/*Data for the table `goods` */

/*Table structure for table `notice` */

DROP TABLE IF EXISTS `notice`;

CREATE TABLE `notice` (
  `notice_id` int(11) NOT NULL COMMENT '公告id',
  `notice_title` varchar(20) default NULL COMMENT '公告标题',
  `notice_content` varchar(500) default NULL COMMENT '公告内容',
  `notice_date` datetime default NULL COMMENT '公告发布时间',
  `notice_user_name` varchar(11) default NULL COMMENT '公告发布人员',
  PRIMARY KEY  (`notice_id`),
  KEY `notice_user_id` (`notice_user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公告信息';

/*Data for the table `notice` */

/*Table structure for table `order` */

DROP TABLE IF EXISTS `order`;

CREATE TABLE `order` (
  `order_id` int(11) NOT NULL auto_increment COMMENT '编号',
  `order_no` varchar(100) default NULL COMMENT '订单号',
  `order_user_id` int(11) default NULL COMMENT '订单用户id',
  `order_number` int(11) default NULL COMMENT '订单总量',
  `order_amount` double default NULL COMMENT '订单总额',
  `order_date` datetime default NULL COMMENT '订单日期',
  PRIMARY KEY  (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单信息';

/*Data for the table `order` */

/*Table structure for table `order_detail` */

DROP TABLE IF EXISTS `order_detail`;

CREATE TABLE `order_detail` (
  `order_id` int(11) NOT NULL auto_increment COMMENT '编号',
  `order_no` varchar(32) NOT NULL COMMENT '订单编号',
  `goods_id` int(11) NOT NULL COMMENT '商品id',
  `goods_per_price` double default NULL COMMENT '商品单价',
  `order_number` int(11) default NULL COMMENT '订单数量',
  `order_amount` double default NULL COMMENT '订单金额',
  `order_user_id` int(11) NOT NULL COMMENT '订单用户id',
  `order_pay_way` varchar(100) default NULL COMMENT '付款方式',
  `order_date` datetime default NULL COMMENT '订单生成日期',
  `order_status` varchar(4) default '0' COMMENT '订单状态(00-未支付,02-支付失败,03-支付成功,04-已受理,待发货,05-已发货,运输中,07-待收货,08-已收货,09-待评价,10-已评价,11-订单已取消,12-订单已删除)',
  `order_address` varchar(150) default NULL COMMENT '收款人地址',
  `order_user_name` varchar(100) default NULL COMMENT '收货人姓名',
  `order_user_phone` varchar(20) default NULL COMMENT '收货人联系方式',
  `order_deliver` datetime default NULL COMMENT '送货日期',
  `order_sender_name` varchar(50) default NULL COMMENT '送货人姓名',
  `order_sender_tel` varchar(20) default NULL COMMENT '送货人联系方式',
  `order_finish_date` datetime default NULL COMMENT '订单完成日期(最后收货日期)',
  `order_remark` varchar(500) default NULL COMMENT '订单备注',
  `order_express` varchar(100) default NULL COMMENT '快递公司',
  `order_parent_id` int(11) default NULL COMMENT '父订单id',
  `order_express_status` varchar(100) default NULL COMMENT '物流状态',
  PRIMARY KEY  (`order_id`),
  KEY `goods_id` (`goods_id`),
  KEY `order_user_id` (`order_user_id`),
  KEY `order_parent_id` (`order_parent_id`),
  CONSTRAINT `order_detail_ibfk_1` FOREIGN KEY (`goods_id`) REFERENCES `goods` (`goods_id`),
  CONSTRAINT `order_detail_ibfk_2` FOREIGN KEY (`order_user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `order_detail_ibfk_3` FOREIGN KEY (`order_parent_id`) REFERENCES `order` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单明细';

/*Data for the table `order_detail` */

/*Table structure for table `purchase` */

DROP TABLE IF EXISTS `purchase`;

CREATE TABLE `purchase` (
  `id` int(11) NOT NULL auto_increment COMMENT '编号',
  `goods_id` int(11) default NULL COMMENT '商品编号',
  `pruchase_date` datetime default NULL COMMENT '采购日期',
  `purchase_number` int(11) default '0' COMMENT '采购数量',
  `purchase_price` double default '0' COMMENT '采购单价',
  `purchase_sup_id` int(11) default NULL COMMENT '供应商id',
  `purchase_user` varchar(50) default NULL COMMENT '采购人员姓名',
  `purchase_user_tel` varchar(20) default NULL COMMENT '采购人员联系方式',
  PRIMARY KEY  (`id`),
  KEY `purchase_sup_id` (`purchase_sup_id`),
  KEY `goods_id` (`goods_id`),
  CONSTRAINT `purchase_ibfk_1` FOREIGN KEY (`purchase_sup_id`) REFERENCES `supplier` (`sup_id`),
  CONSTRAINT `purchase_ibfk_2` FOREIGN KEY (`goods_id`) REFERENCES `goods` (`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='采购信息';

/*Data for the table `purchase` */

/*Table structure for table `stock` */

DROP TABLE IF EXISTS `stock`;

CREATE TABLE `stock` (
  `id` int(11) NOT NULL auto_increment COMMENT '编号',
  `goods_id` int(11) NOT NULL COMMENT '商品编号',
  `stock_num` int(11) default NULL COMMENT '库存数量',
  `alarm` bit(5) default '\0' COMMENT '是否已报警(0-未报警,1-已报警)',
  `desc` varchar(100) default NULL COMMENT '备注信息',
  PRIMARY KEY  (`id`),
  KEY `goods_id` (`goods_id`),
  CONSTRAINT `stock_ibfk_1` FOREIGN KEY (`goods_id`) REFERENCES `goods` (`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='库存信息';

/*Data for the table `stock` */

/*Table structure for table `supplier` */

DROP TABLE IF EXISTS `supplier`;

CREATE TABLE `supplier` (
  `sup_id` int(11) NOT NULL auto_increment COMMENT '供应商编号',
  `sup_name` varchar(50) NOT NULL COMMENT '供应商名称',
  `sup_address` varchar(100) default NULL COMMENT '供应商地址',
  `sup_tel` varchar(20) default NULL COMMENT '供应商联系方式',
  `sup_eamil` varchar(50) default NULL COMMENT '供应商邮箱',
  PRIMARY KEY  (`sup_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='供应商信息';

/*Data for the table `supplier` */

/*Table structure for table `sys_permission` */

DROP TABLE IF EXISTS `sys_permission`;

CREATE TABLE `sys_permission` (
  `id` int(11) NOT NULL auto_increment,
  `available` int(11) default NULL,
  `name` varchar(255) default NULL,
  `parent_id` int(11) default NULL,
  `permission` varchar(255) default NULL,
  `resource_type` int(11) default NULL,
  `sort` int(11) default NULL,
  `url` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_permission` */

insert  into `sys_permission`(`id`,`available`,`name`,`parent_id`,`permission`,`resource_type`,`sort`,`url`) values (1,1,'系统管理',0,'/system',0,1,'/system'),(2,1,'用户管理',1,'/sysUser',1,2,'/sysUser'),(3,1,'角色管理',1,'/role',1,3,'/role'),(4,1,'权限管理',1,'/permission',1,4,'/permission'),(5,1,'会员管理',1,'/customer',1,5,'/customer'),(6,1,'商品管理',1,'/goods',1,6,'/goods'),(7,1,'商品类别管理',1,'/category',1,7,'/category'),(8,1,'评价管理',1,'/comments',1,8,'/comments'),(9,1,'公告管理',1,'/notice',1,9,'/notice'),(10,1,'商品库存管理',1,'/stock',1,10,'/stock'),(11,1,'供应商管理',1,'/supplier',1,11,'/supplier'),(12,1,'统计模块',1,'/statics',1,12,'/statics'),(13,1,'用户查询',2,'/sysUser/users',2,13,'/sysUser/add'),(14,1,'用户添加',2,'/sysUser/add',2,14,'/sysUser/edit'),(15,1,'用户修改',2,'/sysUser/edit',2,15,'/sysUser/delete'),(16,1,'用户删除',2,'/sysUser/delete',2,16,'/sysUser/users'),(17,1,'用户个人信息维护',2,'/sysUser/loadUser',2,17,'/sysUser/loadUser'),(18,1,'角色查询',3,'/role/roles',2,18,'/role/roles'),(19,1,'角色添加',3,'/role/edit',2,19,'/role/edit'),(20,1,'角色修改',3,'/role/edit',2,20,'/role/edit'),(21,1,'分配权限',3,'/rolesavePermission',2,22,'/rolesavePermission'),(22,1,'角色删除',3,'/role/delete',2,21,'/role/delete'),(23,0,'权限添加',4,'/permission/add',2,23,'/permission/add'),(24,1,'权限修改',4,'/permission/edit',2,24,'/permission/edit');

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL auto_increment,
  `available` int(11) default NULL,
  `description` varchar(255) default NULL,
  `role` varchar(255) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `UK_mwbqlu5c82jfd2w9oa9d6e87d` (`role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_role` */

insert  into `sys_role`(`id`,`available`,`description`,`role`) values (1,1,'超级管理员','superadmin'),(2,1,'管理员','admin'),(3,1,'test','test'),(5,1,'对会员有操作权限','role2'),(7,1,'role5','role5'),(8,1,'role3','role3'),(9,1,'user4','user4'),(10,1,'user7','user7'),(11,1,'user6','user6');

/*Table structure for table `sys_role_permission` */

DROP TABLE IF EXISTS `sys_role_permission`;

CREATE TABLE `sys_role_permission` (
  `permission_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  KEY `FK9q28ewrhntqeipl1t04kh1be7` (`role_id`),
  KEY `FKomxrs8a388bknvhjokh440waq` (`permission_id`),
  CONSTRAINT `FKomxrs8a388bknvhjokh440waq` FOREIGN KEY (`permission_id`) REFERENCES `sys_permission` (`id`),
  CONSTRAINT `FK9q28ewrhntqeipl1t04kh1be7` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_role_permission` */

insert  into `sys_role_permission`(`permission_id`,`role_id`) values (1,1),(2,1),(3,1),(4,1),(5,1),(6,1),(7,1),(8,1),(9,1),(10,1),(11,1),(12,1),(13,1),(14,1),(15,1),(16,1),(17,1),(18,1),(19,1),(20,1),(21,1),(22,1),(23,1),(24,1);

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `uid` int(11) NOT NULL auto_increment,
  `name` varchar(255) default NULL,
  `password` varchar(255) default NULL,
  `salt` varchar(255) default NULL,
  `state` int(11) default NULL,
  `username` varchar(255) default NULL,
  PRIMARY KEY  (`uid`),
  UNIQUE KEY `UK_51bvuyvihefoh4kp5syh2jpi4` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_user` */

insert  into `sys_user`(`uid`,`name`,`password`,`salt`,`state`,`username`) values (1,'管理员','7085ab7c91470f35517638d67dc284da','5d114997f3dab23a7220c6b5d7db7a8b',1,'admin');

/*Table structure for table `sys_user_role` */

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `uid` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  KEY `FKhh52n8vd4ny9ff4x9fb8v65qx` (`role_id`),
  KEY `FKput17v9wwg8wiukw8ykroaaag` (`uid`),
  CONSTRAINT `FKput17v9wwg8wiukw8ykroaaag` FOREIGN KEY (`uid`) REFERENCES `sys_user` (`uid`),
  CONSTRAINT `FKhh52n8vd4ny9ff4x9fb8v65qx` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_user_role` */

insert  into `sys_user_role`(`uid`,`role_id`) values (1,1);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `user_name` varchar(50) NOT NULL COMMENT '用户名',
  `user_pw` varchar(50) NOT NULL COMMENT '用户密码',
  `user_realname` varchar(50) default NULL COMMENT '真实姓名',
  `user_address` varchar(50) default NULL COMMENT '地址',
  `user_sex` varchar(5) default NULL COMMENT '性别',
  `user_tel` varchar(20) default NULL COMMENT '电话',
  `user_eamil` varchar(50) default NULL COMMENT '邮箱',
  `user_qq` varchar(50) default NULL COMMENT '用户qq',
  `user_core` int(11) default NULL COMMENT '用户积分',
  `user_level` varchar(20) default NULL COMMENT '用户等级',
  `user_birthday` varchar(10) default NULL COMMENT '生日',
  `user_register` varchar(20) default NULL COMMENT '注册日期',
  `user_last` varchar(20) default NULL COMMENT '用户最后登陆时间',
  `user_img` varchar(50) default NULL COMMENT '用户头像',
  PRIMARY KEY  (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员信息';

/*Data for the table `user` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

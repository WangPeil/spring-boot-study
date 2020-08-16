CREATE DATABASE `test` CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';


CREATE TABLE `user`
(
    `id`          bigint(11)  NOT NULL AUTO_INCREMENT COMMENT '主键',
    `username`    varchar(20) NOT NULL DEFAULT '' COMMENT '姓名',
    `age`         int(11)     NOT NULL DEFAULT '0' COMMENT '年龄',
    `create_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='用户表';
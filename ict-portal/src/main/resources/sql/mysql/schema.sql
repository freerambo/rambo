CREATE DATABASE `ict_portal` /*!40100 DEFAULT CHARACTER SET utf8 */;


    create table t_user (
		id int auto_increment,
        login_name varchar(255) not null unique,
        name varchar(64),
        password varchar(255),
        salt varchar(64),
        email varchar(128),
		phone varchar(32),
        status varchar(32),
        team_id int,
       	register_date timestamp not null default 0,

        primary key (id)
    );
    
      insert into t_user (id, login_name, name, email,phone, password, salt, status, team_id,register_date) values(1,'admin','Admin','zhuyb@ntu.edu.sg','98672803','691b14d79bf0fa2215f155235df5e670b64394cc','7efbd59d9741d34f','1',1,'2015-09-09 12:23:37');
insert into t_user (id, login_name, name, email, phone,password, salt, status, team_id,register_date) values(2,'user','rambo','user@e.ntu.edu.sg','98672803','2488aa0c31c624687bd9928e0a5d29e7d1ed520b','6d65d24122c30500','1',1,'2015-09-09 12:23:37');


    create table t_user_role (
        user_id int not null,
        role_id int not null,
        primary key (user_id, role_id)
    );
    
   	create table t_team (
		id int auto_increment,
    	name varchar(255) not null unique,
    	master_id int,
        primary key (id)
    );
    
    create table t_role (
		id int auto_increment,
    	name varchar(255) not null unique,
    	permitions varchar(255),
        primary key (id)
    );
    
    
    CREATE TABLE `ict_portal`.`t_subscription` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `email` VARCHAR(128) NULL COMMENT '',
  `create_time` TIMESTAMP NULL DEFAULT '0000-00-00 00:00:00' COMMENT '',
  `status` VARCHAR(32) NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '')
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;



drop table if exists t_task;
drop table if exists t_user;

create table t_task (
	id bigint auto_increment,
	title varchar(128) not null,
	description varchar(255),
	user_id bigint not null,
    primary key (id)
) engine=InnoDB;


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
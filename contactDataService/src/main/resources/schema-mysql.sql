create table if not exists contact (id bigint not null auto_increment, email varchar(100) not null, first_name varchar(55) not null, last_name varchar(55) not null, type varchar(10) not null, value varchar(20) not null, title varchar(35) not null, primary key (id));
alter ignore table contact add marital_status varchar(15);
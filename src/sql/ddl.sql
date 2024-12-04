create user 'spring5'@'localhost' identified by 'spring5';

create database spring5fs character set=utf8;

grant all privileges on spring5fs.* to 'spring5'@'localhost';

-- 테이블 생성
create table spring5fs.MEMBER (
                                  ID int auto_increment primary key,   -- auto_increment 칼럼으로 지정함
                                  EMAIL varchar(255),
                                  PASSWORD varchar(100),
                                  NAME varchar(100),
                                  REGDATE datetime,
                                  unique key (EMAIL)
) engine=InnoDB character set = utf8;

SELECT user, host FROM mysql.user WHERE user = 'spring5';


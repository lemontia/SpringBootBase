# Spring Boot 기본설정.
아래 항목들 적용.


## Spring Boot 구성요소

- Gradle 사용

- Mariadb JDBC 추가

- DB 로그
-- 파라미터 적용된 SQL문 보임 => log4jdbc
	예) INSERT INTO tb_member (user_email, user_name, auth_site) VALUES ( 'test22@test.com' , '이름', 'KAKAO' ) 

- Mybatis
> Mapper 설정
> configuration 에는 최소한의 것 설정

- 에러 핸들러
> ExceptionHandler => @ControllerAdvice

- 사용자수정 Exception
> CustomException

- thymeleaf

- devtools

- lombok

- ErrorPageHandler - 2018.06.29
> 에러 메세지에 따른 URL 위치 변경

- Spring Security - 2018.06.29
> 스프링 시큐리티 로그인 및 권한테스트 추가


## DB 설정
1. MariaDB(또는 MySQL) 에 test 데이터베이스 생성

2. 다음의 스크립트 실행
drop table tb_member;
CREATE TABLE `tb_member` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`user_email` VARCHAR(50) NOT NULL,
	`user_password` VARCHAR(500) NULL DEFAULT '',
	`user_name` VARCHAR(30) NULL DEFAULT NULL,
	`user_status` VARCHAR(1) NULL DEFAULT 'Y',
	`user_role` VARCHAR(1) NULL DEFAULT '9',
	`reg_dt` DATETIME DEFAULT CURRENT_TIMESTAMP,
	`mod_dt` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`),
	UNIQUE INDEX `uniq_tb_member_user_email` (`user_email`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=8
;

INSERT INTO `tb_member` (`id`, `user_email`, `user_password`, `user_name`, `user_status`, `user_role`, `auth_site`, `reg_dt`, `mod_dt`) VALUES (1, 'test22@test.com', '', '이름', 'Y', '9', 'KAKAO', '2018-06-29 16:05:18', '2018-06-29 16:05:18');
INSERT INTO `tb_member` (`id`, `user_email`, `user_password`, `user_name`, `user_status`, `user_role`, `auth_site`, `reg_dt`, `mod_dt`) VALUES (2, 'test@test.com', '$2a$10$SGkWWbyiXh6qJ7JU.JTpNuttDLxa2lXndpY/FX3r/Wnu1jcfKpiS.', 'test', 'Y', '2', 'KAKAO', '2018-06-29 16:05:46', '2018-06-29 17:54:22');


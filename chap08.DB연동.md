# Chapter8. DB연동 
#### JDBC란?
#### Spring JDBC
#### JDBC 프로그래밍의 단점을 보완하는 스프링
#### 프로젝트 준비하기
#### DataSource 설정
#### JdbcTemplate을 이용한 쿼리 실행
#### 트랜잭션 처리
#### 로그 메시지


2024.12.02 16:38
프로젝트 준비 
 - pom파일에 DB설정 추가 
 - src\main\java\spring 에 사용할 소스 파일 복붙
 - git 생성 및 연동 
 - MySQL 테이블 생성 
 - AppCtx에 dataSource 설정 (교재에서는 className이 Dbconfig로 되어 있는데 경로는 AppCtx로 되어 있음 )


3.1 Tomcat JDBC의 주요 프로퍼티 
 ```
 setInitialSize(int) : 커넥션 풀을 초기화할 때 생성할 초기 커넨션 개수 지정(default: 10)
setMaxActive(int) : 커넥션 풀에서 가져올 수 있는 최대 커넥션 개수 지정(default: 100)
setMaxIdle(int)  : 커넥션 풀에 유지할 수 있는 최대 커넥션 개수 지정(default: 100)
setMinIdle(int) : 커넥션 풀에 유지할 최소 커넥션 개수 지정(default: initialSize값)
setMaxWait(int) : 커넥션 풀에서 커넥션을 가져올 때 대기할 최대 시간을 밀리초 단위로 지정(default: 30000밀리초(30초))
setMaxAge(long) : 최초 커넥션 연결 후 커넥션의 최대 유효 시간을 밀리초 단위로 지정(default: 0(유효시간 없음))
setValidationQuery(String) : 커넥션이 유효한지 검사할 때 사용할 쿼리를 지정(default: null(검사 안함))
setValidationQueryTimeout(int) : 검사 쿼리의 최대 실행 시간을 초 단위로 지정해당 시간을 초과하면 검사에 실패한 것으로 간주0 이하로 지정하면 비활성화(default: -1)
setTestOnBorrow(boolean) : 풀에서 커넥션을 가져올 때 검사 여부를 지정(default: false)
setTestOnReturn(boolean) :  풀에 커넥션을 반환할 때 검사 여부를 지정(default: false)
setTestWhileIdle(boolean) : 커넥션이 풀에 유휴 상태로 있는 동안에 검사할지 여부를 지정(default: false)
setMinEvictableIdleTimeMillis(int) : 커넥션 풀에 유휴 상태로 유지할 최소 시간을 밀리초 단위로 지정.testWhileIdle 설정이 true라면, 이 시간을 초과한 커넥션을 풀에서 제거(default: 60000밀리초(60초))
setTimeBetweenEvictionRunsMillis(int) : 커넥션 풀의 유휴 커넥션을 검사할 주기를 밀리초 단위로 지정1초 이하로 설정하면 안됨(default: 5000밀리초(5초))
```

4.JdbcTemplate를 이용한 쿼리 실행 
4.1 jdbcTemplate 생성하기 
4.2 jdbcTemplate를 이용한 조회 쿼리 실행
List<T> query(String sql, RowMapper<T> rowMapper)
List<T> query(String sql, Object[] args, RowMapper<T> rowMapper)
List<T> query(String sql, RowMapper<T> rowMapper, Object... args)
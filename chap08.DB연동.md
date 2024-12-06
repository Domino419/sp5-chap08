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

에러 :
public int count() {
Integer count = jdbcTemplate.queryForObject("select count(*) from Member", Integer.class) ;
return count ;
}
정의되지 않은 메서드 호출로 인한 컴파일 에러 
리턴값을 count 변수로 제대로 줬어야 하는데 count()로 오타를 내버림.

4.4 jdbcTemplate를 이용한 변경 쿼리 실행 
INSERT, UPDATE, DELETE 쿼리는 update() 메서드를 사용한다. 
int update(String sql) 
int update(String sql, Object ... args )  

4.5 PreparedStatementCreator 를 이용한 쿼리 실행 
 쿼리에서 사용할 값을 인자로 전달하는 방법 말고 
 set 메서드를 사용해서 직접 인덱스 파라미터의 값을 설정해야 하는 경우에 사용,
 PreparedStatementCreator를 인자로 받는 메서드를 이용해서 직접 PreparedStatementCreator 을 생성하고 설정할 수 있다. 


4.6 . insert 쿼리 실행시 keyHolder를 이용해서 자동 생성 키값 구하기.
MySql의 AUTO_INCREMENT 칼럼은 행이 추가되면 자동으로 값이 할당되는 컬럼으로 주요키 칼럼에 사용된다.
그냥 insert 하게 되면 자동 증가 칼럼에 해당하는 값은 지정하지 않게 됨.
쿼리 실행 후에 생성된 키값을 알고 싶다면  ketHolder를 사용한다. 


빌드 실패 : Caused by: com.mysql.jdbc.exceptions.jdbc4.CommunicationsException: Communications link failure
SSLHandshakeException: Received fatal alert: protocol_version
>> SSL핸드쉐이크 사용하지 않음으로 AppCtx에서 설정 변경 
>> POM파일에서 MySql 버전 최신 버전 8.0.25로 변경 
>> Db접속 유저 권한?부분에 설정 변경 처리 
>> SELECT user, host, plugin FROM mysql.user WHERE user = 'spring5';
>> ALTER USER 'spring5'@'localhost' IDENTIFIED WITH mysql_native_password BY 'spring5';
>> 그래도 안되길래 보니까.. getString을 getNstring으로 오타내서 빌드 실패 ^^....

>>.BadSqlGrammarException  PreparedStatementCallback; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: Unknown column 'MAIL' in 'field list'
>     "INSERT INTO Member(EMAIL, PASSWORD, NAME, REGDATE) VALUES (?, ?, ?, ?)", >> Email을 mail로 잘못 써서 수정함. 
> 

DB연동시 봤던 에러 메시지와 수습한 기록들.. 
1. JDBC 연결 실패 - MySQL 버전을 8 버전으로 설치했더니 인증 방식이 틀려서 실패 . USER 'spring5'@'localhost' IDENTIFIED WITH mysql_native_password BY 'spring5'; 
권한 설정을 다시 하고 나서 연결 성공 
2. 빌드 중에 캐릭터셋이 안 맞다고 에러 발생,  에러로그에서 Can not call getNString() 메시지 확인 후 코드 확인하니까 getString이 아니라 getNString으로 오타냈음.
3. BadSqlGrammarException  PreparedStatementCallback; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: Unknown column 'MAIL' in 'field list'
   에러 로그에서 email을 mail로 오타낸 부분이 확인되어서 해당 부분 수정함.
4. SSL핸드쉐이크 ~ 관련 에러 메시지 발생시 데이터소스의 Db Url 에서 ssl 사용하지 않음으로 주소에 추가 기재해서 넘어감. useSSL=false&requireSSL=false
5. (DB 버전 , 사용자 유저 권한, 컬럼명이나 getString 오타 부분 체크할 것 )

2024.12.04 23:21
빌드 성공, 209 page까지 진행했음.
스프링의 익셉션 변환 처리 부분부터 시작하면 됨.

7. 트랜잭션 처리
 - setAutoCommit(false) , commit() , rollback()을 사용해서 트랜잭션을 반영하는 방법 
 - @Transactional 애노테이션을 사용 하는 방법 (AppCtx 에서 플랫폼 트랜잭션 매니저에 빈 설정, @Transactional  애노테이션 활성화 성정 필요 )
```
public class ChangePasswordService {
    private MemberDao memberDao;

    @Transactional 
    public void changePassword(String email, String oldPwd, String newPwd) {
        Member member = memberDao.selectByEmail(email); // 쿼리가 트랜잭션에 묶인다.
        if (member == null)
            throw new MemberNotFoundException();
        member.changePassword(oldPwd, newPwd); // 쿼리가 트랜잭션에 묶인다.
        memberDao.update(member);
    }
```

트랜잭션 처리를 실습하기 위한 코드 입력 완료 , 빌드 완료, 틀린 비번 넣어서 에러 처리 되는지 체크 완료 
Logback 모츌 추가 , logback도 추가 했음. 

log 제대로 찍히는데.. 터미널에서 로그색상 알록달록하게 만들고 싶어서 
이리저리 설정 바꿔보다가 오늘은 여기서 끝 ~ 


package config;


import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.MemberDao;



@Configuration
public class AppCtx {

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        DataSource ds = new DataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost/spring5fs?characterEncoding=utf8");
        ds.setUsername("spring5");  //Db연결시 사용자명
        ds.setPassword("spring5");  // Db연결시 암호
        ds.setInitialSize(2);       // 커넥션 갯수
        ds.setMaxActive(10);        // 커넥션 풀에서 가져올 수 있는 커넥션 max개수
        ds.setTestWhileIdle(true);  // 유휴 커넥션 검사
        ds.setMinEvictableIdleTimeMillis(1000*60*3);  // 최소 유휴 시간 3분
        ds.setTimeBetweenEvictionRunsMillis(1000*10);  // 10초 주기
        return ds;
    }

    @Bean
    public MemberDao memberDao() {
        return new MemberDao(dataSource());
    }

}

// ds.setMinEvictableIdleTimeMillis(1000*60*3);  // 최소 유휴 시간 3분
// ds.setTimeBetweenEvictionRunsMillis(1000*10);  // 10초 주기
// 자바에서 시간 관련 API는 대부분이 초단위보다 세밀하게 시간 간격을 제어할 수 있도록 밀리초 단위를 기본으로 사용함.


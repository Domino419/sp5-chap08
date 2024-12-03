package dbquery;

import javax.sql.DataSource;
import java.sql.Connection ;
import java.sql.ResultSet ;
import java.sql.SQLException ;
import java.sql.Statement ;

/**
 * class         : DbQuery
 * date          : 24-12-03
 * description   : 데이터베이스 쿼리를 실행하여 결과를 반환하는 클래스.
 *                  DataSource를 사용하여 커넥션 풀을 관리하며, MEMBER 테이블의 레코드 수를 조회하는 기능 제공.
 */
public class DbQuery {
    private DataSource dataSource ; // 데이터베이스 커넥션 풀을 관리하는 DataSource 객체

    /**
     * method        : DbQuery
     * date          : 24-12-03
     * param         : DataSource dataSource - 데이터베이스 커넥션 풀을 관리하는 객체
     * description   : DataSource를 받아서 DbQuery 객체를 초기화.
     */
    public DbQuery(DataSource dataSource) {
        this.dataSource = dataSource ;
    }

    /**
     * method        : count
     * date          : 24-12-03
     * return        : int - MEMBER 테이블의 레코드 수
     * description   : MEMBER 테이블의 레코드 수를 조회하여 반환.
     *                  예외 발생 시 RuntimeException으로 변환하여 던짐.
     *                  사용 후 Connection 객체를 반드시 반환.
     */
    public int count() {
        Connection conn = null ;

        try {
            conn = dataSource.getConnection();                                            // 커넥션 풀에서 커넥션 획득
            try (Statement stmt = conn.createStatement();                                 // 쿼리 실행 객체 생성
                 ResultSet rs = stmt.executeQuery("select count(*) from MEMBER"))     // 쿼리 실행 및 결과 반환
            {
                rs.next();                                                                // 결과 집합의 첫 번째 행으로 이동
                return rs.getInt(1);                                           // 첫 번째 컬럼 값 반환
            }

        } catch (Exception e) {
            throw  new RuntimeException(e) ;                                              // 예외를 런타임 예외로 변환
        } finally {
            if(conn != null )
                try {
                    conn.close();
                }catch (SQLException e){
                    System.err.println("커넥션 반환 실패: " + e.getMessage());
                    e.printStackTrace();                                       // 예외 스택 트레이스 출력
                }
        }


    }
}

// 교재에서는 finaly 구문에서 로그 생략하고 그냥 넘어가도록 되어 있으나
// 로그 기록되도록 코드 수정해놓음.
//  교재와 동일하게 보고 싶은 경우에는 55, 56 line을 주석 처리 하면 됨.



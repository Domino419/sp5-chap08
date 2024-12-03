package spring;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 * class         : MemberDao
 * date          : 24-11-17
 * description   : JDBC를 활용하여 회원 데이터를 관리하는 DAO 클래스.
 * history       : 1.chap03에서 가져온 파일에서 Db연동을 위해 기존 메서드 모두 삭제  (24.11.17)
 *               : 2. DataSource를 통한 JdbcTemplate 초기화 추가   (24.12.03)
 */
public class MemberDao {

    private JdbcTemplate jdbcTemplate ;    // 데이터베이스와의 상호작용을 단순화하기 위한 JdbcTemplate 객체

    /**
     * method        : MemberDao  (생성자 전달 방식)
     * date          : 24-12-03
     * param         : DataSource dataSource - 데이터베이스 연결 정보를 제공하는 DataSource 객체
     * description   : DataSource를 이용하여 JdbcTemplate 객체를 초기화. 이를 통해 데이터베이스와의 작업을 처리.
     */
    public MemberDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource) ;
    }

    /**
     * method        : selectByEmail
     * date          : 24-12-03
     * param         : String email - 조회할 회원의 이메일 주소
     * return        : Member - 이메일에 해당하는 회원 객체, 없으면 null 반환
     * description   : 데이터베이스에서 주어진 이메일에 해당하는 회원 정보를 조회.
     *                  조회 결과를 List로 받고, 결과가 없으면 null을 반환.
     *                  SQL 쿼리를 사용하여 EMAIL 컬럼을 기준으로 검색 수행.
     */
    public Member selectByEmail(String email) {
        List<Member> results = jdbcTemplate.query(
                "Select * from Member where EMAIL = ?",
                new RowMapper<Member>() {
                    @Override
                    public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Member member = new Member(
                                rs.getString("EMAIL"),
                                rs.getString("PASSWORD"),
                                rs.getString("NAME"),
                                rs.getTimestamp("REGDATE").toLocalDateTime());
                        member.setId(rs.getLong("ID"));
                        return member;
                    }
                }, email
        );

        return results.isEmpty() ? null : results.get(0);

    }

    public void insert(Member member){
    }

    public void update(Member member){
    }



}


//자바 버전 8 이상인 경우에는 selectByEmail 메소드를 람다식으로 표현해서 사용할 수도 있음.
// 근데 그렇게 많이 짧아진 거 같지는 않은데.....................
/* str
public Member selectByEmail(String email) {
    List<Member> results = jdbcTemplate.query(
            "Select * from Member where EMAIL = ?",
            (rs, rowNum) -> {
                Member member = new Member(
                        rs.getString("EMAIL"),
                        rs.getString("PASSWORD"),
                        rs.getString("NAME"),
                        rs.getTimestamp("REGDATE").toLocalDateTime());
                member.setId(rs.getLong("ID"));
                return member;
            },
            email
    );

    return results.isEmpty() ? null : results.get(0);
}
end */

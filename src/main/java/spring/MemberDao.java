package spring;

import java.sql.*;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

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


    /**
     * method        : insert
     * date          : 24-12-04
     * param         : Member member - 데이터베이스에 삽입할 회원 객체 (이메일, 비밀번호, 이름, 등록일시 정보 포함)
     * return        : void
     * description   : 새로운 회원 정보를 MEMBER 테이블에 삽입. 자동 생성된 키(ID)를 가져와서 Member 객체에 설정.
     *                  PreparedStatementCreator를 사용하여 SQL INSERT 쿼리를 실행하고, KeyHolder로 생성된 ID 값을 반환.
     */
    public void insert(final Member member) {
        KeyHolder keyHolder = new GeneratedKeyHolder();  // 자동 생성된 키 값을 받을 KeyHolder 객체 생성

        // jdbcTemplate의 update 메서드를 사용하여 데이터 삽입 처리
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                // 회원 데이터를 삽입할 SQL 쿼리 작성
                PreparedStatement pstmt = con.prepareStatement(
                        "INSERT INTO Member(EMAIL, PASSWORD, NAME, REGDATE) VALUES (?, ?, ?, ?)",
                        new String[] {"ID"} // 자동 생성된 ID 값을 반환받기 위해 "ID" 컬럼을 지정
                );

                // PreparedStatement에 회원 정보 세팅
                pstmt.setString(1, member.getEmail());
                pstmt.setString(2, member.getPassword());
                pstmt.setString(3, member.getName());
                pstmt.setTimestamp(4, Timestamp.valueOf(member.getRegisterDateTime()));

                return pstmt; // 생성한 PreparedStatement 반환
            }
        }, keyHolder); // PreparedStatement 실행 후 자동 생성된 키 값을 keyHolder에 저장

        // 자동 생성된 키 값(ID)을 Member 객체에 설정
        Number keyValue = keyHolder.getKey();
        member.setId(keyValue.longValue());  // Long 타입으로 변환하여 회원 객체에 설정
    }



    /**
     * method        : update
     * date          : 24-12-04
     * param         : Member member - 업데이트할 회원 객체 (이름, 비밀번호, 이메일 정보를 포함)
     * return        : void
     * description   : MEMBER 테이블에서 이메일을 기준으로 해당 회원의 이름과 비밀번호를 업데이트.
     *                  SQL 쿼리를 사용하여 UPDATE 문을 실행하며, JdbcTemplate의 update 메서드를 통해 처리.
     *  hostiry      : 198pagem update method 추가 ,
     *                  INSERT, UPDATE, DELETE 쿼리는 update() 메서드를 사용한다.
     *                  int update(String sql)
     *                  int update(String sql, Object ... args )
     *                  Update() 메서드는 쿼리 실행 결과로 변경된 행의 갯수를 리턴한다.
     */
    public void update(Member member){
        jdbcTemplate.update(
                "UPDATE MEMBER SET NAME = ? , PASSWORD = ? where EMAIL = ?" ,
                member.getName(), member.getPassword() , member.getEmail() ) ;
    }



    /**
     * method        : selectAll
     * date          : 24-12-04
     * param         : 없음
     * return        : List<Member> - 데이터베이스의 모든 회원 정보를 포함하는 리스트
     * description   : 데이터베이스의 MEMBER 테이블에서 모든 회원 데이터를 조회.
     *                  각 행은 Member 객체로 매핑되며, 결과는 리스트 형태로 반환됨.
     *                  SQL 쿼리를 사용하여 전체 데이터 조회 수행.
     * history       : 195page selectAll 메서드 추가
     */
    public List<Member> selectAll() {
        List<Member> results = jdbcTemplate.query("select * from MEMBER", new RowMapper<Member>() {
                    @Override
                    public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Member member = new Member(
                                rs.getString("EMAIL"),
                                rs.getString("PASSWORD"),
                                rs.getString("NAME"),
                                rs.getTimestamp("REGDATE").toLocalDateTime());
                        member.setId(rs.getLong("ID"));
                    return member ;
                    }
                });
        return results ;
    }


    /**
     * method        : count
     * date          : 24-12-04
     * param         : 없음
     * return        : int - MEMBER 테이블의 전체 레코드 수
     * description   : 데이터베이스의 MEMBER 테이블에서 전체 회원 수를 조회.
     *                  SQL 쿼리 `select count(*) from Member`를 실행하여 결과를 정수로 반환.
     *                >> queryForObject() 메서드는 쿼리 실행 결과 행이 한개인 경우에 사용할 수 있는 메서드.
     *                >> queryForObject() 메서드의 두번째 파라미터는 칼럼을 읽어올 때 사용할 타입을 지정한다.
     */
    public int count() {
        Integer count = jdbcTemplate.queryForObject("select count(*) from Member", Integer.class) ;
        return count ;
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

package spring;

import java.util.Collection;
import java.util.HashMap ;
import java.util.Map ;

/**
 * class         : MemberDao
 * date          : 24-11-17
 * description   : 메모리 내에서 회원 데이터를 관리하는 DAO 클래스.
 */
public class MemberDao {

    private static long nextId = 0 ;
    private Map<String , Member> map = new HashMap<>()  ;

    /**
     * method        : selectByEmail
     * date          : 24-11-17
     * param         : String email - 조회할 회원의 이메일
     * return        : Member
     * description   : 주어진 이메일로 회원 정보를 조회하고 반환.
     *                 이메일이 존재하지 않으면 null 반환.
     */
    public Member selectByEmail(String email ){
        return map.get(email) ;
    }

    /**
     * method        : insert
     * date          : 24-11-17
     * param         : Member member - 추가할 회원 객체
     * return        : void
     * description   : 새로운 회원 객체를 추가하며, 회원 ID를 자동 증가하여 설정.
     */
    public void insert(Member member) {
        member.setId(++nextId);
        map.put(member.getEmail(), member) ;
    }

    /**
     * method        : update
     * date          : 24-11-17
     * param         : Member member - 업데이트할 회원 객체
     * return        : void
     * description   : 주어진 회원 객체로 데이터를 업데이트. 동일한 이메일을 기준으로 정보를 덮어씌움.
     */
    public void update(Member member) {
        map.put(member.getEmail(), member) ;
    }


    /**
     * method        : selectAll
     * date          : 24-11-19
     * return        : Collection<Member>
     * description   : 모든 회원 정보를 Collection 형태로 반환.
     */
    public Collection<Member> selectAll() {
        return map.values() ;
    }

}

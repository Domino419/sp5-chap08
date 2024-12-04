package main;

import config.AppCtx;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.Member;
import spring.MemberDao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


/**
 * class         : MainForMemberDao
 * date          : 24-12-04
 * description   : MemberDao를 테스트하기 위한 메인 클래스. 데이터 조회, 수정, 삽입 기능 확인.
 */
public class MainForMemberDao {
    private static MemberDao memberDao ;


    public static void main(String[] args ) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtx.class);

        memberDao = ctx.getBean(MemberDao.class) ;

        selectAll();
        updateMember() ;
        insertMember() ;

        ctx.close();
    }


    /**
     * method        : selectAll
     * date          : 24-12-04
     * description   : 데이터베이스에 저장된 모든 회원 정보를 조회하고 출력하는 메서드.
     *                  - 회원의 총 개수를 출력.
     *                  - 회원 목록의 ID, 이메일, 이름 출력.
     */
    private static void selectAll(){
        System.out.println("-----selectAll");
        int total = memberDao.count() ;
        System.out.println("전체 데이터 : " + total);
        List<Member> members = memberDao.selectAll() ;
        for ( Member m : members ){
            System.out.println(m.getId() +":" + m.getEmail()+ ":" + m.getName());
        }
    }


    /**
     * method        : updateMember
     * date          : 24-12-04
     * description   : 특정 이메일을 가진 회원의 암호를 변경하는 메서드.
     *                  - 이메일로 회원 정보 조회.
     *                  - 암호를 새로운 랜덤 값으로 변경.
     *                  - 변경된 내용을 데이터베이스에 업데이트.
     */
    private static void updateMember(){
        System.out.println("-----updateMember");
        Member member = memberDao.selectByEmail("blue@naver.com") ;
        String oldPw = member.getPassword();
        String newPw = Double.toHexString(Math.random()) ;
        member.changePassword(oldPw , newPw);

        memberDao.update(member);
        System.out.println("암호변경 : " + oldPw + "> " + newPw);
    }

    /**
     * field         : formatter
     * date          : 24-12-04
     * description   : 현재 시간을 특정 형식("MMddHHmmss")으로 포맷팅하기 위한 DateTimeFormatter 객체.
     */
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddHHmmss") ;

    /**
     * method        : insertMember
     * date          : 24-12-04
     * description   : 새로운 회원 데이터를 데이터베이스에 삽입하는 메서드.
     *                  - 현재 시간 기반으로 고유 이메일과 이름 생성.
     *                  - 새 Member 객체를 생성하고 삽입.
     *                  - 삽입된 데이터의 ID를 출력.
     */
    private static void insertMember() {
        System.out.println("-----insertMember");

        String prefix = formatter.format(LocalDateTime.now()) ;     // 현재 시간으로 고유한 문자열 생성
        Member member = new Member(prefix + "+@test.com" , prefix , prefix, LocalDateTime.now()) ; // 새 회원 객체 생성
        memberDao.insert(member);
        System.out.println(member.getId() + "데이터 추가 ");
    }




}

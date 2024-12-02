package spring;

import java.util.Collection;

/**
 * class         : MemberListPrinter
 * date          : 24-11-17
 * description   : 모든 회원의 정보를 출력하는 클래스. MemberDao를 통해 회원 목록을 가져오고 MemberPrinter를 사용하여 출력함.
 */
public class MemberListPrinter {
 private MemberDao memberDao ;
 private MemberPrinter printer ;

    /**
     * method        : MemberListPrinter (constructor)
     * date          : 24-11-17
     * param         : MemberDao memberDao - 회원 데이터를 관리하는 DAO 객체
     *               : MemberPrinter printer - 회원 정보를 출력하는 Printer 객체
     * description   : MemberListPrinter 생성자. MemberDao와 MemberPrinter를 주입받아 초기화.
     */
    public MemberListPrinter(MemberDao memberDao, MemberPrinter printer) {
        this.memberDao =memberDao;
        this.printer =printer;
    }

    /**
     * method        : printAll
     * date          : 24-11-17
     * return        : void
     * description   : 모든 회원 정보를 가져와서 MemberPrinter를 사용해 출력.
     */
    public void printAll() {
        Collection <Member> members = memberDao.selectAll() ;
        members.forEach( m -> printer.print(m));
    }


}

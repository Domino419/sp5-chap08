package spring;

/**
 * class         : MemberInfoPrinter
 * date          : 24-11-17
 * description   : 특정 이메일로 회원 정보를 조회하여 출력하는 기능을 제공하는 클래스.
 *                 MemberDao를 통해 회원 데이터를 가져오고, MemberPrinter를 사용하여 정보를 출력한다.
 */
public class MemberInfoPrinter {

    private MemberDao memberDao ;
    private MemberPrinter printer ;

    /**
     * method        : printMemberInfoPrinter
     * date          : 24-11-17
     * param         : String email - 조회할 회원의 이메일
     * return        : void
     * description   : 주어진 이메일로 회원 정보를 검색하여, 존재하면 출력하고 없으면 메시지를 출력한다.
     */
    public void printMemberInfoPrinter(String email) {
        Member member = memberDao.selectByEmail(email) ;
        if ( member == null ) {
            System.out.println("데이터 없음 \n");
            return;
        }
        printer.print(member);
        System.out.println();
    }

    /**
     * method        : printMemberInfoPrinter
     * date          : 24-11-17
     * param         : String email - 조회할 회원의 이메일
     * return        : void
     * description   : 주어진 이메일로 회원 정보를 검색하여, 존재하면 출력하고 없으면 메시지를 출력한다.
     */
    public void printMemberInfo(String email) {
        Member member = memberDao.selectByEmail(email);
        if (member == null) {
            System.out.println("데이터 없음\n");
            return;
        }
        printer.print(member);
        System.out.println();
    }


    /**
     * method        : setMemberDao
     * date          : 24-11-17
     * param         : MemberDao memberDao - 의존성 주입을 위한 MemberDao 객체
     * return        : void
     * description   : MemberDao 객체를 설정하여 Member 데이터 조회에 사용한다.
     */
    public void setMemberDao(MemberDao memberDao) {
        this.memberDao = memberDao ;
    }

    /**
     * method        : setPrinter
     * date          : 24-11-17
     * param         : MemberPrinter printer - 의존성 주입을 위한 MemberPrinter 객체
     * return        : void
     * description   : MemberPrinter 객체를 설정하여 회원 정보를 출력하는 데 사용한다.
     */
    public void setPrinter(MemberPrinter printer){
        this.printer = printer ;
    }
}


/*
6.3 DI 방식 2 : 세터 메서드 방식
 세터 메서드를 이해서 의존 객체를 주입받는 코드 작성 실습 ( 85page)
 작업 후 AppCtx에 설정 부분 추가
 */
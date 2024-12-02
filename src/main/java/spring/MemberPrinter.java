package spring;

/**
 * class         : MemberPrinter
 * date          : 24-11-17
 * description   : 회원 정보를 출력하는 클래스
 */
public class MemberPrinter {

    /**
     * method        : print
     * date          : 24-11-17
     * param         : Member member - 출력할 회원 객체
     * return        : void
     * description   : 회원 객체의 ID, 이메일, 이름, 등록일을 콘솔에 포맷된 형태로 출력.
     */
    public void print(Member member) {
        System.out.printf(
                "회원 정보: 아이디=%d, 이메일=%s, 이름=%s, 등록일=%tF\n",
                member.getId(), member.getEmail(),  member.getName(), member.getRegisterDateTime());

    }

}

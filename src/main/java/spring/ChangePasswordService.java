package spring;

public class ChangePasswordService {

    private MemberDao memberDao ;


    /**
     * class         : changePassword
     * date          : 24-11-17 21:52
     * param         : email, oldPwd, newPwd)
     * description   : email을 기준으로 회원 정보를 조회한 뒤 패스워드 변경 처리
     */
    public void changePassword(String email, String oldPwd, String newPwd) {
        Member member = memberDao.selectByEmail(email) ;
        if ( member == null )
            throw new MemberNotFoundException() ;
        member.changePassword(oldPwd, newPwd);
        memberDao.update(member);
    }

    public void setMemberDao(MemberDao memberDao){
        this.memberDao = memberDao ;
    }
}

package spring;

import java.time.LocalDateTime;

/**
 * class         : MemberRegisterService
 * date          : 24-11-17
 * description   : 회원 등록 서비스
 */
public class MemberRegisterService {

    private MemberDao memberDao;

    public MemberRegisterService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    /**
     * method        : regist
     * date          : 24-11-17 12:30
     * param         :
     * return        : Long Id
     * description   : 이메일 기준으로 중복 회원 가입시 익셉션, 중복이 아닌 경우 회원 가입 처리
     */
    public Long regist(RegisterRequest req) {
        Member member = memberDao.selectByEmail(req.getEmail());
        if (member != null) {
            throw new DuplicateMemberException("dup email " + req.getEmail());
        }
        Member newMember = new Member(
                req.getEmail(), req.getPassword(), req.getName(),
                LocalDateTime.now());
        memberDao.insert(newMember);
        return newMember.getId();
    }

}

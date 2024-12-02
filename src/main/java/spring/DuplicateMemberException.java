package spring;

/**
 * class         : DuplicateMemberException
 * date          : 24-11-17 12:17
 * param         :
 * description   : 회원 가입시 동일한 이메일로 가입 시도하는 경우 에러처리
 */
public class DuplicateMemberException extends RuntimeException {

    public DuplicateMemberException(String message) {
        super(message);
    }
}

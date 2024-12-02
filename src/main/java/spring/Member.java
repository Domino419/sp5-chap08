package spring;

import java.time.LocalDateTime ;

public class Member {

    private Long id ;
    private String email  ;
    private String password  ;
    private String name  ;
    private LocalDateTime registerDateTime ;

    public Member(String email, String password, String name, LocalDateTime regDateTime ) {
        this.email = email;
        this.password  = password;
        this.name = name ;
        this.registerDateTime = regDateTime  ;
    }

    void setId (Long id  ) {
        this.id = id ;
    }

    public Long getId() {
        return id ;
    }

    public String getEmail() {
        return email ;
    }


    public String getPassword() {
        return password ;
    }

    public String getName() {
        return name ;
    }

    public LocalDateTime getRegisterDateTime() {
        return registerDateTime ;
    }

    /**
     * methodName    : changePassword
     * date          : 24-11-17 12:02
     * param         : String oldPassword , String newPassword
     * description   : oldPassword가 저장된 password와 같은 경우에 암호 변경 처리
     */
    public void changePassword(String oldPassword , String newPassword) {
        if ( ! password.equals(oldPassword))
            throw new WrongIdPasswordException();
        this.password = newPassword ;
    }

}

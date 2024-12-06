package main;

import config.AppCtx;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.ChangePasswordService;
import spring.MemberNotFoundException;
import spring.WrongIdPasswordException;

@Slf4j
public class MainForCPS {

    public static void main(String[] args ) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtx.class) ;
        ChangePasswordService cps = ctx.getBean("changePwdSvc"  ,ChangePasswordService.class ) ;

        try{
//            cps.changePassword("blue@naver.com" , "1234", "1111");
           // cps.changePassword("blue@naver.com" , "1111", "1234");
            cps.changePassword("pwd@naver.com" , "1111", "2222");
            System.out.println("암호를 변경했습니다.");
        }catch (MemberNotFoundException e) {
            System.out.println("MemberNotFoundException  : 존재하지 않는 회원 이메일입니다. ");
        }catch (WrongIdPasswordException e) {
            System.out.println("WrongIdPasswordException : 암호가 올바르지 않습니다.");
        }

        // Lombok @Slf4j 로 추가된 Logger 사용
        log.info("테스트 메시지 - INFO 레벨");
        log.debug("테스트 메시지 - DEBUG 레벨");

        ctx.close();
    }

}

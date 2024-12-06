package config;

import org.slf4j.Logger;

public class LoggerFactory {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(LoggerFactory.class);

    public static void main(String[] args) {
        // 로그 메시지 출력
        logger.info("테스트");
        logger.debug("테스트");
    }
}
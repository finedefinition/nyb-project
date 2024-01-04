package com.norwayyachtbrockers.aspects;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Aspect
@Component
public class YachtPostAspect {

    private static final Logger logger = LoggerFactory.getLogger(YachtPostAspect.class);

    @After("execution(* com.norwayyachtbrockers.service.impl.YachtServiceImpl.save(..))")
    public void logAfterYachtSave() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = LocalDateTime.now().format(formatter); // The current date and time
        logger.info("Yacht entity is successfully stored on server as of " + formattedDateTime);
    }
}
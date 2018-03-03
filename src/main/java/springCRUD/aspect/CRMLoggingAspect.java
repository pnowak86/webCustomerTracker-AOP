package springCRUD.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.mapping.Join;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component("cRMLoggingAspect")
public class CRMLoggingAspect {

    //setup logger
    private Logger myLogger = Logger.getLogger(getClass().getName());

    //setup pointcut declarations
    @Pointcut("execution(* springCRUD.controller.*.*(..))")
    private void forControllerPackage() {
    }

    @Pointcut("execution(* springCRUD.service.*.*(..))")
    private void forServicePackage() {
    }

    @Pointcut("execution(* springCRUD.dao.*.*(..))")
    private void forDaoPackage() {
    }

    @Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
    private void forAppFlow() {
    }


    //add @Before advice
    @Before("forAppFlow()")
    public void before(JoinPoint joinPoint) {

        //display the method we are calling
        String theMethod = joinPoint.getSignature().toShortString();
        myLogger.info("===============> in @Before: calling method " + theMethod);

        //display the arguments to the method

        //get the arguments
        Object[] args = joinPoint.getArgs();

        //loop through and display args
        for (Object tempArg : args) {
            myLogger.info("===============> argument: " + tempArg);
        }
    }

    //add @AfterReturning advice
    @AfterReturning(
            pointcut = "forAppFlow()",
            returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {

        //display method we are returning from
        String theMethod = joinPoint.getSignature().toShortString();
        myLogger.info("===============> in @AfterReturning: calling method " + theMethod);


        //display data returned
        myLogger.info("===============> result: " + result);

    }

}

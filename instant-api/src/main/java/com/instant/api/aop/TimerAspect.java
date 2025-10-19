package com.instant.api.aop;

import lombok.NonNull;

import org.apache.logging.log4j.util.TriConsumer;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * Interface that can be used to log the execution time of a join point.
 */
public interface TimerAspect {

    /**
     * Run the join point and log the execution time.
     *
     * @param joinPoint the join point to execute
     * @return the result of the join point execution
     * @throws Throwable when the execution did not succeed
     */
    default Object proceed(@NonNull ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result = null;
        try {
            result = joinPoint.proceed();
        }
        catch (Exception e) {
            result = e;
            throw e;
        }
        finally {
            timerConsumer().accept(joinPoint, result, System.currentTimeMillis() - startTime);
        }
        return result;
    }

    /**
     * Returns a consumer that will always be called no matter the result of the join point execution.
     *
     * @return the consumer
     */
    TriConsumer<JoinPoint, Object, Long> timerConsumer();

}

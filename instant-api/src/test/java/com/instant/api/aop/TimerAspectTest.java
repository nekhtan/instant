package com.instant.api.aop;

import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith({ OutputCaptureExtension.class, MockitoExtension.class })
@Slf4j
class TimerAspectTest {

    @Mock
    private ProceedingJoinPoint joinPoint;

    private final TimerAspect aspect = () -> (joinPoint, result, executionTime) -> log.info("[{}] Execution time: {}ms", result, executionTime);

    @Test
    void shouldConsumeAfterProceeding(CapturedOutput output) throws Throwable {
        when(joinPoint.proceed()).thenReturn("result");

        assertEquals("result", aspect.proceed(joinPoint));

        assertTrue(output.getAll().contains("[result] Execution time:"));
    }

    @Test
    void shouldConsumeAfterThrowing(CapturedOutput output) throws Throwable {
        Throwable thrown = new RuntimeException();

        when(joinPoint.proceed()).thenThrow(thrown);

        RuntimeException actuallyThrown = assertThrows(RuntimeException.class, () -> aspect.proceed(joinPoint));

        assertEquals(thrown, actuallyThrown);
        assertTrue(output.getAll().contains("[java.lang.RuntimeException] Execution time:"));
    }
}
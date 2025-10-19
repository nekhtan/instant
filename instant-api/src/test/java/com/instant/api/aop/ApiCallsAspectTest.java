package com.instant.api.aop;

import com.instant.api.controller.ParkingLotController;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith({ OutputCaptureExtension.class, MockitoExtension.class })
class ApiCallsAspectTest {

    @Mock
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Mock
    private HttpStatusCodeResolver httpStatusCodeResolver;

    @InjectMocks
    private ApiCallsAspect aspect;

    @Mock
    private MockHttpServletRequest request;

    @Mock
    private ProceedingJoinPoint joinPoint;

    @Mock
    private MethodSignature methodSignature;

    @Mock
    private RequestMappingInfo requestMappingInfo;

    @Mock
    private HandlerMethod handlerMethod;

    @Mock
    private PathPattern pathPattern;

    private Method method;

    @BeforeEach
    void setup() throws Exception {
        when(methodSignature.getMethod()).thenReturn(method);

        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(joinPoint.getSignature().toShortString()).thenReturn("signature-short");

        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(request.getRequestURL()).thenReturn(new StringBuffer("http://my-api.fr/parking-lots/nice/all"));
        when(request.getQueryString()).thenReturn("page=0&size=10");

        when(pathPattern.getPatternString()).thenReturn("/parking-lots/{id}/all");
        when(requestMappingInfo.getPathPatternsCondition()).thenReturn(mock(PathPatternsRequestCondition.class));
        when(requestMappingInfo.getPathPatternsCondition().getPatterns()).thenReturn(Set.of(pathPattern));
        when(requestMappingHandlerMapping.getHandlerMethods()).thenReturn(Map.of(requestMappingInfo, handlerMethod));

        method = ParkingLotController.class.getMethod("getAllIn", String.class, Pageable.class);
    }

    @Test
    void shouldLog(CapturedOutput output) throws Throwable {
        when(methodSignature.getMethod()).thenReturn(method);
        when(handlerMethod.getMethod()).thenReturn(method);
        when(joinPoint.proceed()).thenReturn("result");
        when(httpStatusCodeResolver.resolve("result")).thenReturn(HttpStatus.OK.value());

        assertEquals("result", aspect.proceed(joinPoint));
        assertTrue(output.getAll().contains("[200] signature-short (/parking-lots/{id}/all - http://my-api.fr/parking-lots/nice/all) [page=0&size=10]:"));
    }
}
package com.instant.api.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.logging.log4j.util.TriConsumer;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Aspect for logging REST calls.
 */
@Component
@Aspect
@Slf4j
@RequiredArgsConstructor
public class ApiCallsAspect implements TimerAspect {

    private final RequestMappingHandlerMapping requestMappingHandlerMapping;
    private final HttpStatusCodeResolver httpStatusCodeResolver;

    /**
     * Pointcut of all public methods in the {@code api} package.
     */
    @Pointcut("execution(public * com.instant.api.controller..*(..))")
    public void publicMethods() {}

    /**
     * Logs the execution time of a method in the {@code api} package.
     *
     * @param joinPoint the join point
     * @return the result of the join point execution
     * @throws Throwable when the execution did not succeed
     */
    @Around("publicMethods()")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        return proceed(joinPoint);
    }

    @Override
    public TriConsumer<JoinPoint, Object, Long> timerConsumer() {
        return (joinPoint, result, executionTime) -> {
            if (joinPoint.getSignature() instanceof MethodSignature signature) {
                int httpStatusCode = httpStatusCodeResolver.resolve(result);

                findMapping(signature).ifPresent(mapping -> log.info("[{}] {} ({} - {}) [{}]: {}ms",
                    httpStatusCode,
                    joinPoint.getSignature().toShortString(),
                    mapping,
                    getUrl(),
                    getQueryParams(),
                    executionTime));
            }
        };
    }

    private Optional<String> findMapping(MethodSignature signature) {
        return requestMappingHandlerMapping.getHandlerMethods()
            .entrySet()
            .stream()
            .filter(entry -> entry.getValue().getMethod().equals(signature.getMethod())
                    && entry.getKey().getPathPatternsCondition() != null)
            .map(entry -> entry.getKey()
                .getPathPatternsCondition()
                .getPatterns()
                .stream()
                .map(PathPattern::getPatternString)
                .collect(Collectors.joining(",")))
            .findFirst();
    }

    private static String getUrl() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes != null ? attributes.getRequest().getRequestURL().toString() : "unknown";
    }

    private static String getQueryParams() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null) {
            return "";
        }

        String query = attrs.getRequest().getQueryString();
        return query == null ? "" : query;
    }

}

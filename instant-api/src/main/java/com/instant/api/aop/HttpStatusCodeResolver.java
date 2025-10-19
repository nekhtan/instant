package com.instant.api.aop;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Resolver of {@link HttpStatusCode}s.
 */
@Component
public class HttpStatusCodeResolver {

    private static final int UNKNOWN_STATUS_CODE = -1;

    /**
     * Resolves the HTTP status code from an object.
     *
     * @param object the object to resolve as a status code, can be:
     *            <ul>
     *            <li>a {@link HttpStatusCode}, in which case the resolved code will be the status value
     *            <li>something else, in which case the resolved code will be {@code -1}
     *            </ul>
     * @return the HTTP status code value, or {@code -1} if the status could not be resolved
     */
    public int resolve(Object object) {
        HttpStatusCode statusCode = resolveInternal(object);

        return statusCode == null
            ? UNKNOWN_STATUS_CODE
            : normalize(statusCode).value();
    }

    private HttpStatusCode resolveInternal(Object object) {
        if (object instanceof HttpStatusCode httpStatusCode) {
            return httpStatusCode;
        }
        Object actualResult = object;
        if (object instanceof Optional<?> optional && optional.isPresent()) {
            actualResult = optional.get();
        }
        if (actualResult instanceof ResponseEntity<?> responseEntity) {
            return responseEntity.getStatusCode();
        }
        return null;
    }

    /**
     * Normalizes the status in a way that we won't end up with deprecated Spring statuses.
     * <p>
     * This means that even if we have a 103/{@link HttpStatus#CHECKPOINT} it should
     * be resolved as 103/{@link HttpStatus#EARLY_HINTS}.
     */
    private static HttpStatusCode normalize(HttpStatusCode httpStatusCode) {
        return httpStatusCode == null
            ? null
            : HttpStatus.valueOf(httpStatusCode.value());
    }
}

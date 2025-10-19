package com.instant.fetcher.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to specify which city a {@link ParkingLotsResponse} implementation is for.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface ParkingLotsResponseFor {

    /**
     * The city the response is for.
     *
     * @return the city
     */
    SupportedCity city();

}

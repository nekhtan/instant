package com.instant.fetcher.util;

import lombok.extern.slf4j.Slf4j;

import com.instant.fetcher.model.ParkingLotsResponse;
import com.instant.fetcher.model.ParkingLotsResponseFor;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Resolver for {@link ParkingLotsResponse} classes based on city names.
 */
@Component
@Slf4j
public class ParkingLotsResponseClassResolver {

    private static final Map<String, Class<? extends ParkingLotsResponse<?>>> CITY_TO_RESPONSE_CLASS = new HashMap<>();

    static {
        String basePackage = "com.instant.fetcher.model";

        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AssignableTypeFilter(ParkingLotsResponse.class));

        for (BeanDefinition beanDefinition : scanner.findCandidateComponents(basePackage)) {
            try {
                Class<?> clazz = Class.forName(beanDefinition.getBeanClassName());
                if (ParkingLotsResponse.class.isAssignableFrom(clazz)) {
                    ParkingLotsResponseFor annotation = clazz.getAnnotation(ParkingLotsResponseFor.class);
                    if (annotation != null) {
                        if (CITY_TO_RESPONSE_CLASS.containsKey(annotation.city().name().toLowerCase())) {
                            throw new IllegalStateException("Duplicate response class for city: " + annotation.city());
                        }
                        CITY_TO_RESPONSE_CLASS.put(annotation.city().name().toLowerCase(), (Class<? extends ParkingLotsResponse<?>>) clazz);
                    }
                }
            }
            catch (ClassNotFoundException e) {
                throw new IllegalStateException("Failed to load ParkingLotsResponse class: " + beanDefinition.getBeanClassName(), e);

            }
        }
    }

    /**
     * Resolve the {@link ParkingLotsResponse} class for the given city name.
     *
     * @param cityName the city name
     * @return the corresponding {@link ParkingLotsResponse} class, or null if not found
     */
    public Class<? extends ParkingLotsResponse<?>> resolve(String cityName) {
        return CITY_TO_RESPONSE_CLASS.get(cityName.toLowerCase());
    }
}

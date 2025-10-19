package com.instant.fetcher.service;

import jakarta.annotation.PostConstruct;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.instant.fetcher.properties.FetcherProperties;
import com.instant.fetcher.properties.SourceConfiguration;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

/**
 * Service to schedule data refresh tasks for enabled sources.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SchedulerService {

    private final FetcherProperties fetcherProperties;
    private final TaskScheduler taskScheduler;
    private final DataRefreshService dataRefreshService;

    /**
     * Schedules data refresh tasks for all enabled sources based on their cron expressions.
     * <p>
     * Also triggers an immediate data refresh on startup.
     */
    @PostConstruct
    public void scheduleSources() {
        for (SourceConfiguration source : fetcherProperties.getSources()) {
            if (source.isEnabled()) {

                // Schedule future executions based on cron expression from configuration
                taskScheduler.schedule(
                    () -> dataRefreshService.refreshDataForSource(source),
                    new CronTrigger(source.getSchedule().replace("\"", "")) // remove extra quotes
                );

                // Execute immediately on startup
                dataRefreshService.refreshDataForSource(source);
            }
        }
    }

}
package com.instant.fetcher.model;

/**
 * The service response interface describing a position.
 */
public interface Position {

    /**
     * The latitude of the position.
     *
     * @return the latitude
     */
    String latitude();

    /**
     * The longitude of the position.
     *
     * @return the longitude
     */
    String longitude();
}

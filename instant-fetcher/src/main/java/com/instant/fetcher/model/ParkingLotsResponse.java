package com.instant.fetcher.model;

import java.util.List;

/**
 * The root service response interface.
 */
public interface ParkingLotsResponse<T> {

    int total();

    List<T> parkingLots();

}

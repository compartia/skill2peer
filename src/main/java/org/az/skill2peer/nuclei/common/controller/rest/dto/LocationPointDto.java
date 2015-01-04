package org.az.skill2peer.nuclei.common.controller.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationPointDto {

    /**
     *
     */
    @JsonProperty(value = "k")
    double latitude;

    @JsonProperty(value = "D")
    double longitude;

    public LocationPointDto() {
    }

    public LocationPointDto(final double latitude, final double longitude) {
        super();
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(final double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(final double longitude) {
        this.longitude = longitude;
    }
}

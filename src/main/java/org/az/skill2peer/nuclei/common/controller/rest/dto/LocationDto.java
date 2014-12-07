package org.az.skill2peer.nuclei.common.controller.rest.dto;

public class LocationDto {
    private String name;

    private String address;

    private String description;

    private Integer id;

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }
}

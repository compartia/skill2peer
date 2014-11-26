package org.az.skill2peer.nuclei.common.controller.rest.dto;

public class LocationDto {
    private String name;

    private String address;

    private String comment;

    private Integer id;

    public String getAddress() {
        return address;
    }

    public String getComment() {
        return comment;
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

    public void setComment(final String comment) {
        this.comment = comment;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }
}

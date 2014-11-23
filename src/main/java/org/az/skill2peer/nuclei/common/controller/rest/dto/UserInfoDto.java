package org.az.skill2peer.nuclei.common.controller.rest.dto;

import org.az.skill2peer.nuclei.common.model.HasIntegerId;

public class UserInfoDto implements HasIntegerId {

    private String firstName;

    private Integer id;

    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public Integer getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }
}

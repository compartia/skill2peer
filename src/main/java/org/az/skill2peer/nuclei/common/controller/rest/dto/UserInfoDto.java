package org.az.skill2peer.nuclei.common.controller.rest.dto;

import org.az.skill2peer.nuclei.common.model.HasIntegerId;

/**
 *
 * @author Artem Zaborskiy
 *
 */
public class UserInfoDto implements HasIntegerId {

    private String firstName;

    private Integer id;

    private String lastName;

    private String info;

    private int rating;

    private String imageUrl;

    public String getFirstName() {
        return firstName;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getInfo() {
        return info;
    }

    public String getLastName() {
        return lastName;
    }

    public int getRating() {
        return rating;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setInfo(final String info) {
        this.info = info;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public void setRating(final int rating) {
        this.rating = rating;
    }
}

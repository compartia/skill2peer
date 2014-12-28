package org.az.skill2peer.nuclei.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "userconnection")
public class UserSocialProfile {

    private static final long serialVersionUID = -7681338116723403579L;

    @Id
    @Column(name = "userid")
    private String id;

    @Column(name = "imageurl")
    private String imageUrl;

    public String getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

}

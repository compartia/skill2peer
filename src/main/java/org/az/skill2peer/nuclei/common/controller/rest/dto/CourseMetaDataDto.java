package org.az.skill2peer.nuclei.common.controller.rest.dto;

public class CourseMetaDataDto {
    private Integer id;

    private boolean isFavorited;

    public Integer getId() {
        return id;
    }

    public boolean isFavorited() {
        return isFavorited;
    }

    public void setFavorited(final boolean isFavorited) {
        this.isFavorited = isFavorited;
    }

    public void setId(final Integer id) {
        this.id = id;
    }
}

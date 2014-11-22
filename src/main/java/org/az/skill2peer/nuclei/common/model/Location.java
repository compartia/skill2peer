package org.az.skill2peer.nuclei.common.model;

import javax.persistence.Id;

public class Location extends BaseEntity<Integer> {
    /**
     *
     */
    private static final long serialVersionUID = -789766183254985839L;

    @Id
    private Integer id;

    private String name;

    @Override
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }
}

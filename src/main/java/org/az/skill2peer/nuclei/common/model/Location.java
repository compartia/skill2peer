package org.az.skill2peer.nuclei.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "location")
@SequenceGenerator(name = "location_id_seq", sequenceName = "location_id_seq")
public class Location extends BaseEntity<Integer> {
    /**
     *
     */
    private static final long serialVersionUID = -789766183254985839L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_id_seq")
    private Integer id;

    @Column(name = "description")
    @Size(max = 1000)
    private String description;

    private Float longitude;

    private Float latitude;

    @Size(max = 1000)
    private String name;

    @Size(max = 1000)
    private String address;

    @Size(max = 1000)
    private String vicinity;

    @Size(max = 1000)
    private String html;

    @Size(max = 1000)
    private String icon;

    @Size(max = 1000)
    private String url;

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public String getHtml() {
        return html;
    }

    public String getIcon() {
        return icon;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public Float getLatitude() {
        return latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setHtml(final String html) {
        this.html = html;
    }

    public void setIcon(final String icon) {
        this.icon = icon;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public void setLatitude(final Float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(final Float longitude) {
        this.longitude = longitude;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public void setVicinity(final String vicinity) {
        this.vicinity = vicinity;
    }

}

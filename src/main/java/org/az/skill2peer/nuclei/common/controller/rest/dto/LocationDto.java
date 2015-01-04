package org.az.skill2peer.nuclei.common.controller.rest.dto;

public class LocationDto {
    private String name;

    private String address, vicinity;

    private String description;

    private String html;

    private String icon;

    private String url;

    private LocationPointDto geometry;

    private Integer id = -1;

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public LocationPointDto getGeometry() {
        return geometry;
    }

    public String getHtml() {
        return html;
    }

    public String getIcon() {
        return icon;
    }

    public Integer getId() {
        return id;
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

    public void setGeometry(final LocationPointDto geometry) {
        this.geometry = geometry;
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

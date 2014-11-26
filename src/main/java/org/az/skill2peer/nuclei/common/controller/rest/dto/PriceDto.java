package org.az.skill2peer.nuclei.common.controller.rest.dto;

/**
 *
 * @author Artem Zaborskiy
 *
 */
public class PriceDto {
    private String value;

    private String currency;

    private String comment;

    public String getComment() {
        return comment;
    }

    public String getCurrency() {
        return currency;
    }

    public String getValue() {
        return value;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    public void setValue(final String value) {
        this.value = value;
    }
}

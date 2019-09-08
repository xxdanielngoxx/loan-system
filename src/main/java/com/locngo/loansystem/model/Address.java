package com.locngo.loansystem.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
public class Address implements Serializable {

    private static final long serialVersionUID = 8105484307584635351L;

    @NotNull
    @Column(name = "province")
    private String province;

    @NotNull
    @Column(name = "district")
    private String district;

    @NotNull
    @Column(name = "wards")
    private String wards;

    @NotNull
    @JsonProperty("street_address")
    @Column(name = "street_address")
    private String streetAddress;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWards() {
        return wards;
    }

    public void setWards(String wards) {
        this.wards = wards;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }
}

package com.minlia.module.map.geocode.body.response;

/**
 * Created by garen on 2018/5/11.
 */
public class RegeocodeBody {

    private String formatted_address;

    private AddressComponentBody addressComponent;

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public AddressComponentBody getAddressComponent() {
        return addressComponent;
    }

    public void setAddressComponent(AddressComponentBody addressComponent) {
        this.addressComponent = addressComponent;
    }
}

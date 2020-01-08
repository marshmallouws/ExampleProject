/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Address;

/**
 *
 * @author Annika
 */
public class AddressDTO {
    private String street;
    private String additionalinfo;
    private String city;
    private int zip;
    
    public AddressDTO(Address address) {
        this.street = address.getStreet();
        this.additionalinfo = address.getAdditionalInfo();
        this.city = address.getCityInfo().getCity();
        this.zip = address.getCityInfo().getZip();
    }
    
    public AddressDTO() {}

    public String getStreet() {
        return street;
    }

    public String getAdditionalinfo() {
        return additionalinfo;
    }

    public String getCity() {
        return city;
    }

    public int getZip() {
        return zip;
    }
}

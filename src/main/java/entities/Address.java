/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Bitten
 */
@Entity
@Table(name = "address")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "street")
    private String street;
    
    @Column(name = "additional_info")
    private String additionalInfo;
    
    @JoinColumn(name = "city_info")
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private CityInfo cityInfo;
    
    @OneToMany(mappedBy = "address")
    private List<Person> person;
    
    public Address() {}
    
    /*
    public Address(String street, String additionalInfo) {
        this.street = street;
        this.additionalInfo = additionalInfo;
    } */

    public Address(String street, String additionalInfo, CityInfo cityInfo) {
        this.street = street;
        this.additionalInfo = additionalInfo;
        this.cityInfo = cityInfo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
    
    public CityInfo getCityInfo() {
        return cityInfo;
    }
    
    public void setCityInfo(CityInfo c) {
        this.cityInfo = c;
    }

    public List<Person> getUsers() {
        return person;
    }

    public void setUsers(List<Person> users) {
        this.person = users;
    }   
}
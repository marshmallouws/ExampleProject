/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Hobby;
import entities.Role;
import entities.Person;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Bitten
 */
public class PersonDTO {

    private String firstname;
    private String lastname;
    private String email;
    private Date dob;
    private String street;
    private String additionalinfo;
    private String city;
    private int zip;
    private List<HobbyDTO> hobbies;
    private Long id;
    private String password;
    private String phone;
    private List<String> roles;
    
    public PersonDTO(Person person) {
        this.id = person.getId();
        this.firstname = person.getFirstName();
        this.lastname = person.getLastname();
        this.email = person.getEmail();
        this.dob = person.getDateOfBirth();
        this.password = person.getPassword();
        this.phone = person.getPhone();
        this.street = person.getAddress().getStreet();
        this.additionalinfo = person.getAddress().getAdditionalInfo();
        this.zip = person.getAddress().getCityInfo().getZip();
        this.city = person.getAddress().getCityInfo().getCity();
        
        hobbies = new ArrayList<>();
        person.getHobbies().forEach((h) -> hobbies.add(new HobbyDTO(h)));
        
        //roles = user.getRolesAsStrings();
    }
    
    // Default constructor used by Gson library to convert Json to Object
    public PersonDTO() {}

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setAdditionalinfo(String additionalinfo) {
        this.additionalinfo = additionalinfo;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public void setHobbies(List<HobbyDTO> hobbies) {
        this.hobbies = hobbies;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

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

    public List<HobbyDTO> getHobbies() {
        return hobbies;
    }

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public List<String> getRoles() {
        return roles;
    }
}

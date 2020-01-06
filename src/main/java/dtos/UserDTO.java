/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Hobby;
import entities.Role;
import entities.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Bitten
 */
public class UserDTO {

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
    private List<RoleDTO> roles;

    public UserDTO(User p, List<Hobby> hobs, List<Role> rols) {
        this.id = p.getId();
        this.firstname = p.getFirstName();
        this.lastname = p.getLastname();
        this.email = p.getEmail();
        this.dob = p.getDateOfBirth();
        this.password = p.getPassword();
        this.phone = p.getPhone();

        this.hobbies = new ArrayList<>();
        this.roles = new ArrayList<>();
        hobs.forEach((h) -> hobbies.add(new HobbyDTO(h)));
        rols.forEach((r) -> roles.add(new RoleDTO(r)));
    }

    public UserDTO(User p, List<Hobby> hobs) {
        this.id = p.getId();
        this.firstname = p.getFirstName();
        this.lastname = p.getLastname();
        this.email = p.getEmail();
        this.dob = p.getDateOfBirth();
        this.password = p.getPassword();
        this.phone = p.getPhone();

        this.hobbies = new ArrayList<>();
        hobs.forEach((h) -> hobbies.add(new HobbyDTO(h)));
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

    public List<String> getRolesAsStrings() {
        if (roles.isEmpty()) {
            return null;
        }
        List<String> rolesAsStrings = new ArrayList();
        for (RoleDTO role : roles) {
            rolesAsStrings.add(role.getRoleName());
        }
        return rolesAsStrings;
    }
}

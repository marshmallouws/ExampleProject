/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import static javax.persistence.TemporalType.*;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Annika
 */
@Entity
@Table(name = "person")
@NamedQuery(name = "Person.deleteAllRows", query = "DELETE FROM Person")
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "email", unique = true) //Used for login therefore unique
    private String email;

    @NotNull
    @Column(name = "firstname")
    private String firstName;

    @NotNull
    @Column(name = "lastname")
    private String lastname;

    // Could be TIME or TIMESTAMP
    @Column(name = "dob")
    @Temporal(DATE /*TIME | TIMESTAMP*/)
    @NotNull
    private Date dateOfBirth;

    // Will not be persisted into database
    @Transient
    private int age;

    @Column(name = "password")
    @NotNull
    private String password;

    @Column(name = "phone")
    private String phone;

    /*
    Eager fetch to ensure that hobbies are always fetched when user is fetched
    */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<Hobby> hobbies = new ArrayList<>();

    @JoinTable(name = "person_roles", joinColumns = {
        @JoinColumn(name = "p_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "role_name", referencedColumnName = "role_name")
    })
    @ManyToMany
    private List<Role> roles;
    
    @ManyToOne
    private Address address;

    /*
    JPA provider needs to create instances of entity. 
    If class would contain only constructor which takes arbitrary 
    arguments, JPA provider cannot figure out values for those arguments.
     */
    public Person() {
    }

    public Person(String firstName, String lastName, String email, Date dob, String password, String phone, List<Hobby> hobbies, Address address) {
        this.firstName = firstName;
        this.lastname = lastName;
        this.email = email;
        this.dateOfBirth = dob;
        this.hobbies = hobbies;
        this.phone = phone;
        this.age = 20; // Calculate age from dob
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
        this.address = address;
    }

    public boolean verifyPassword(String pw) {
        return (BCrypt.checkpw(pw, password));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getAge() {
        return age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Hobby> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<Hobby> hobbies) {
        this.hobbies = hobbies;
    }

    public void addHobby(Hobby h) {
        this.hobbies.add(h);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRole() {
        return roles;
    }

    public void setRole(List<Role> roles) {
        this.roles = roles;
    }

    public List<String> getRolesAsStrings() {
        if (roles.isEmpty()) {
            return null;
        }
        List<String> rolesAsStrings = new ArrayList();
        for (Role role : roles) {
            rolesAsStrings.add(role.getRoleName());
        }
        return rolesAsStrings;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}

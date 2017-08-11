
package com.microsoft.crm.v1.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.minlia.cloud.entity.AbstractEntity;
import org.springframework.data.annotation.Transient;

import javax.persistence.*;
import java.util.*;

/**
 * @author Jarvis Song
 */

@Entity
@Table(name = "DS_USER")
//@DynamicSearch
public class User extends AbstractEntity {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Integer id;
//    @Condition
    @Column(name = "FIRSTNAME")
    @JsonProperty
    @JSONField
    private String firstname;
//    @Condition(type = LIKE)
    @Column(name = "LASTNAME")
    @JsonProperty
    @JSONField
    private String lastname;
    @JsonProperty
    @JSONField
    private int age;
    @JsonProperty
    @JSONField
    private boolean active;
    @JsonProperty
    @JSONField
    private Date createdAt;

    @JsonProperty
    @JSONField
    @Column(name = "EMAIL_ADDRESS")
    private String emailAddress;

    @ManyToMany
    @JoinTable(name = "DS_USER_DS_USER",
            joinColumns = @JoinColumn(name = "DS_USER_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "COLLEAGUES_ID", referencedColumnName = "ID"))
    private Set<User> colleagues;
    @ManyToOne
    @JoinColumn(name = "MANAGER_ID")
    @JsonProperty
    @JSONField
    private User manager;
    @ManyToMany
    @JoinTable(name = "ds_user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "ID"))
    @JsonProperty
    @JSONField
    private Set<Role> roles;

    @Embedded
    private Address address;

    @Lob
    private byte[] binaryData;

//    @ElementCollection
//    private Set<String> attributes;

    private Date dateOfBirth;

    @OneToMany
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private List<Booking> bookings;

    @Transient
    private String testData;

    /**
     * Creates a new empty instance of {@code User}.
     */
    public User() {
        this(null, null, null);
    }

    //    @PersistenceConstructor
    public User(String firstname, String lastname) {

        this.firstname = firstname;
        this.lastname = lastname;

    }

    public User(String firstname, String lastname, String emailAddress, Role... roles) {

        this.firstname = firstname;
        this.lastname = lastname;
        this.emailAddress = emailAddress;
        this.active = true;
        this.roles = new HashSet<Role>(Arrays.asList(roles));
        this.colleagues = new HashSet<User>();
//        this.attributes = new HashSet<String>();
        this.createdAt = new Date();
    }

    public String getTestData() {
        return testData;
    }

    public void setTestData(String testData) {
        this.testData = testData;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

//    @PreInssert
//    public void preInssert() {
//        System.out.println(this.getClass().getName() + " preInssert............");
//    }
//
//    @PreUpdate
//    public void preUpdate() {
//        System.out.println(this.getClass().getName() + "preUpdate............");
//    }

//    /**
//     * @return the id
//     */
//    public Integer getId() {
//
//        return id;
//    }
//
//    /**
//     * @param id the id to set
//     */
//    public void setId(Integer id) {
//
//        this.id = id;
//    }

    /**
     * Returns the firstname.
     *
     * @return the firstname
     */
    public String getFirstname() {

        return firstname;
    }

    /**
     * Sets the firstname.
     *
     * @param firstname the firstname to set
     */
    public void setFirstname(final String firstname) {

        this.firstname = firstname;
    }

    /**
     * Returns the lastname.
     *
     * @return the lastname
     */
    public String getLastname() {

        return lastname;
    }

    /**
     * Sets the lastname.
     *
     * @param lastname the lastname to set
     */
    public void setLastname(String lastname) {

        this.lastname = lastname;
    }

    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Returns the email address.
     *
     * @return the emailAddress
     */
    public String getEmailAddress() {

        return emailAddress;
    }

    /**
     * Sets the email address.
     *
     * @param emailAddress the emailAddress to set
     */
    public void setEmailAddress(String emailAddress) {

        this.emailAddress = emailAddress;
    }

    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Returns the user's roles.
     *
     * @return the roles
     */
    public Set<Role> getRoles() {

        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    /**
     * Gives the user a role. Adding a role the user already owns is a no-op.
     */
    public void addRole(Role role) {

        roles.add(role);
    }

    /**
     * Revokes a role from a user.
     *
     * @param role
     */
    public void removeRole(Role role) {

        roles.remove(role);
    }

    /**
     * Returns the colleagues of the user.
     *
     * @return the colleagues
     */
    public Set<User> getColleagues() {

        return colleagues;
    }

    public void setColleagues(Set<User> colleagues) {
        this.colleagues = colleagues;
    }

    /**
     * Adds a new colleague to the user. Adding the user himself as colleague is a no-op.
     *
     * @param collegue
     */
    public void addColleague(User collegue) {

        // Prevent from adding the user himself as colleague.
        if (this.equals(collegue)) {
            return;
        }

        colleagues.add(collegue);
        collegue.getColleagues().add(this);
    }

    /**
     * Removes a colleague from the list of colleagues.
     *
     * @param colleague
     */
    public void removeColleague(User colleague) {

        colleagues.remove(colleague);
        colleague.getColleagues().remove(this);
    }


    /**
     * @return the createdAt
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return the address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * @return the binaryData
     */
    public byte[] getBinaryData() {
        return binaryData;
    }

    /**
     * @param binaryData the binaryData to set
     */
    public void setBinaryData(byte[] binaryData) {
        this.binaryData = binaryData;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof User)) {
            return false;
        }

        User that = (User) obj;

        if (null == this.getId() || null == that.getId()) {
            return false;
        }

        return this.getId().equals(that.getId());
    }

//    /**
//     * @return the attributes
//     */
//    public Set<String> getAttributes() {
//        return attributes;
//    }
//
//    /**
//     * @param attributes the attributes to set
//     */
//    public void setAttributes(Set<String> attributes) {
//        this.attributes = attributes;
//    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return "User: " + getId() + ", " + getFirstname() + " " + getLastname() + ", " + getEmailAddress();
    }
}

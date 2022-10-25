package models;

import java.util.LinkedList;

/**
 * Represents a user of the application
 */
public class User {

    /**
     * The name of the user
     */
    private String name;

    /**
     * The email of the user
     */
    private String email;

    /**
     * The mobile of the user
     */
    private String mobile;

    /**
     * The list of bookings of the user
     */
    private LinkedList<Booking> bookings;

    public User (String name, String email, String mobile) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
    }

    /**
     * Gets the name of this User
     * @return the User's name
     */
    public String getName() {
        return name;
    }

    /**
     * Updates the name of this User
     * @param name the User's new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the mobile number of this User
     * @return the User's mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * Updates the mobile number of this User
     * @param mobile The User's new mobile number
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * Gets the email of this User
     * @return the User's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Updates the email of this User
     * @param email the User's new email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Adds a new booking for this user
     */
    public void addBooking(Booking b) {
        bookings.add(b);
    }

}

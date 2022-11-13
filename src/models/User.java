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
     * The password of the user
     */
    private String password;

    /**
     * The list of bookings of the user
     */
    private LinkedList<Booking> bookings;

    /**
     * Creates a new User object with the given parameters
     * @param name  the User's name
     * @param email    the User's email
     * @param mobile     the Session's mobile
     * @param password the Session's password
     */
    public User (String name, String email, String mobile, String password) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
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

    /**
     * Checks if the password given matches with the user
     * @param password the password to be matched
     * @return true if the password matches, false otherwise
     */
    public boolean validatePassword(String password) {
        return this.password.equals(password);
    }

}

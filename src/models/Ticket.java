package models;

import java.time.LocalDateTime;

/**
 * Represents a ticket for a particular booking
 *
 */
public class Ticket {

    /**
     * The age group of the owner of the ticket
     */
    private String ageGroup;

    /**
     * The date and time of the movie
     */
    private LocalDateTime dateTime;

    /**
     * The booking tied to the ticket
     */
    private Booking booking;

    /**
     * Creates a new Ticket from the given parameters
     @param ageGroup The Ticket's age group
     @param dateTime The Ticket's date & time
     @param booking The Ticket's booking
     */
    public Ticket (String ageGroup, LocalDateTime dateTime, Booking booking) {
        this.ageGroup = ageGroup;
        this.dateTime = dateTime;
        this.booking = booking;
    }

    /**
     * Calculates the price for the ticket
     * @return the price of the ticket
     */
    public double getTicketPrice() {
        // TODO determine logic for price
        return 0;
    }
}

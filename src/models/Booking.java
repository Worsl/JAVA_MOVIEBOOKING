package models;

import java.util.LinkedList;

/**
 * Represents a booking for a movie
 *
 */
public class Booking {

    /**
     * The transaction ID of the booking
     */
    private String TID;

    /**
     * The tickets booked for this booking
     */
    private LinkedList<Ticket> tickets;

    /**
     * The movie session of the booking
     */
    private MovieSession movieSession;

    /**
     * The person who made this booking
     */
    private User owner;

    /**
     * Creates a new Booking from the given parameters
     *
     * @param tickets The Booking's transaction ID
     * @param movieSession  The Booking's session
     * @param user The Booking's owner
     */
    public Booking(String TID, MovieSession movieSession, User owner) {
        this.TID = TID;
        this.tickets = new LinkedList<Ticket>();
        this.movieSession = movieSession;
        this.owner = owner;
    }

    /**
     * Calculates the total amount paid for this booking
     * @return the sum of the price of tickets
     */
    public double getTotalPrice() {
        double sum = 0;
        for (Ticket t: tickets) {
            sum += t.getTicketPrice();
        }

        return sum;
    }

    /**
     * Gets the transaction id of this Booking.
     * @return the TID of this booking
     */
    public String getTID() {
        return this.TID;
    }

    /**
     * Gets the owner of this booking
     * @return the owner of this booking
     */
    public User getOwner() {
        return this.owner;
    }

    /**
     * Gets the movie session of this booking
     * @return the movie session of this booking
     */
    public MovieSession getMovieSession() {
        return this.movieSession;
    }

    /**
     * Adds a new ticket to this booking.
     * @param the new ticket to be added to this booking
     */
    public void addTicket(Ticket t) {
        tickets.add(t);
    }

    /**
     * Gets of tickets of this booking
     * @return the ticket list of this booking
     */
    public LinkedList<Ticket> getTickets() {
        return this.tickets;
    }


}


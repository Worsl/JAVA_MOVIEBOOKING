package models;

import java.time.LocalDateTime;

/**
 * Represents a ticket for a particular booking
 *
 */
public class Ticket {

    /**
     * The type of this ticket
     */
    private TicketType ticketType;

    /**
     * The seat of this ticket
     */
    private Seat seat;

    /**
     * The booking associated to this ticket
     */
    private Booking booking;

    /**
     * Creates a new Ticket from the given parameters
     @param ticketType The Ticket's type
     @param seat The Ticket's seat
     @param booking The Ticket's booking
     */
    public Ticket (TicketType ticketType, Seat seat, Booking booking) {
        this.ticketType = ticketType;
        this.seat = seat;
        this.booking = booking;
    }

    /**
     * Gets the ticket type of this ticket
     * @return the ticketType of this ticket
     */
    public TicketType getTicketType() {
        return ticketType;
    }

    /**
     * Gets the seat of this ticket
     * @return the Seat of this ticket
     */
    public Seat getSeat() {
        return seat;
    }

    /**
     * Gets the booking of this ticket
     * @return the booking of this ticket
     */
    public Booking getBooking() {
        return booking;
    }

    /**
     * Calculates the price for the ticket
     * @return the price of the ticket
     */
    public double getTicketPrice() {
        return this.seat.getSeatType().multiplier *
            (
             this.booking.getMovieSession().getCinema().getCinemaClass().loungePrice +
             this.booking.getMovieSession().getMovie().getMovieType().basePrice +
             this.ticketType.agePricing
             );
    }
}

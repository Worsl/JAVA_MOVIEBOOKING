package models;

import java.util.LinkedList;

import java.time.LocalDateTime;

/**
 * Represents a single movie session.
 */
public class MovieSession {

    /**
     * The time slot of the session.
     */
    private LocalDateTime timeSlot;

    /**
     * The cinema of the session.
     */
    private Cinema cinema;

    /**
     * The movie of the session.
     */
    private Movie movie;

    /**
     * The list of seats for this session
     */
    private LinkedList<Seat> seats;

    /**
     * Creates a new Session from the given parameters
     * @param timeSlot the Session's time slot
     * @param cinema the Session's cinema
     * @param movie the Session's movie
     */
    public MovieSession (LocalDateTime timeSlot, Cinema cinema, Movie movie) {
        this.timeSlot = timeSlot;
        this.cinema = cinema;
        this.movie = movie;
        // TODO structure of the movie theatre, then create seats in constructor
        this.seats = new LinkedList<Seat>();
    }

    /**
     * Gets the time slot of this session.
     * @return this Session's time slot.
     */
    public LocalDateTime getTimeSlot() {
        return this.timeSlot;
    }

    /**
     * Gets the cinema of this session.
     * @return this Session's cinema.
     */
    public Cinema getCinema() {
        return this.cinema;
    }

    /**
     * Gets the movie of this session.
     * @return this Session's movie.
     */
    public Movie getMovie() {
        return this.movie;
    }

    /**
     * Books a particular seat given by the parameter
     * @param seatId the id of the seat to be booked
     */
    public void setSeat(String seatId) {
        for (Seat s: seats) {
            if (s.getSeatId() == seatId) {
                s.occupySeat();
                break;
            }
        }
    }

    /**
     * Count the number of seats booked for this particular session
     * @return the number of seats booked
     */
    public int countSeats() {
        int count = 0;

        for (Seat s: seats) {
            if (s.checkOccupied())
                count++;
        }

        return count;
    }

}

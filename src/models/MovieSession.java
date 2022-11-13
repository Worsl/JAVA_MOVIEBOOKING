package models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import org.fusesource.jansi.AnsiConsole;
import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;


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
    private LinkedHashMap<String, Seat> seats;

    /**
     * The unique identifier of the session.
     */
    private String sessionId;

    /**
     * Creates a new Session from the given parameters
     * @param timeSlot the Session's time slot
     * @param cinema the Session's cinema
     * @param movie the Session's movie
     * @param sessionId the Session's unique id
     */
    public MovieSession (String timeSlot, Cinema cinema, Movie movie, String sessionId) {
        this.timeSlot = LocalDateTime.parse(timeSlot, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.cinema = cinema;
        this.movie = movie;
        this.sessionId = sessionId;

        this.seats = new LinkedHashMap<String, Seat>();
        String rows[] = { "A", "B", "C", "D", "E", "F", "G", "H", "I"}, seatId;
        String backRows[] = { "J", "K" };

        for (String r: rows) {
            for (int i = 1; i <= 9; i++) {
                seatId = r + i;
                seats.put(seatId, new Seat(seatId, this, SeatType.STANDARD));
            }
        }

        for (String r: backRows) {
            seatId = r;
            seats.put(seatId + "1", new Seat(seatId + "1", this, SeatType.DISABLED));
            seats.put(seatId + "2", new Seat(seatId + "2", this, SeatType.COUPLE));
            seats.put(seatId + "3", new Seat(seatId + "3", this, SeatType.PREMIUM));
            seats.put(seatId + "4", new Seat(seatId + "4", this, SeatType.COUPLE));
            seats.put(seatId + "5", new Seat(seatId + "5", this, SeatType.DISABLED));
        }
    }

    //Overriding the default toString() Method
    @Override
    public String toString() {
        return timeSlot + " " + movie.getTitle() + " "+ cinema.getCinemaCode();
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
     * Gets all the seats of this session
     * @return the LinkedHashMap of seats
     */
    public LinkedHashMap<String, Seat> getAllSeats() {
        return this.seats;
    }

    /**
     * Gets the particular seat of this session
     * @param seatId the id of the seat to return
     * @return the Seat with the corresponding seatId
     */
    public Seat getSeat(String seatId) {
        return seats.get(seatId);
    }

    /**
     * Updates this session to be held at another cinema
     * @param cinema The cinema to be held at
     */
    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    /**
     * Updates this session's movie
     * @param movie The new movie to be screened
     */
    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    /**
     * Updates the new time slot for this session
     * @param timeSlot The new timing for this session
     */
    public void setTimeSlot(String timeSlot) {
        this.timeSlot = LocalDateTime.parse(timeSlot, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    /**
     * Books a particular seat given by the parameter
     * @param seatId the id of the seat to be booked
     * @return true if seat is successfully set, false otherwise
     */
    public boolean setSeat(String seatId) {
        boolean check = false;
        try {
            Seat seat = seats.get(seatId);
            if (seat.checkOccupied()) {
                System.out.println("This seat is already occupied!");
                return false;
            }
            seat.occupySeat();

            check = true;
        }
        catch(Exception e) {
            System.out.println("You have chosen an invalid seat.");
        }
        return check;
    }

    /**
     * Count the number of seats booked for this particular session
     * @return the number of seats booked
     */
    public int countSeats() {
        int count = 0;

        for (Seat s: seats.values()) {
            if (s.checkOccupied())
                count++;
        }

        return count;
    }

    /**
     * Gets the unique identifier for this session
     * @return the Session Id
     */
    public String getSessionId() {
        return this.sessionId;
    }

}

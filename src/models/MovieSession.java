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

    private String sessionId;

    /**
     * Creates a new Session from the given parameters
     * @param timeSlot the Session's time slot
     * @param cinema the Session's cinema
     * @param movie the Session's movie
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

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[91m";

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
     * Gets the particular seat of this session
     * @param seatId the id of the seat to return
     * @return the Seat with the corresponding seatId
     */
    public Seat getSeat(String seatId) {
        return seats.get(seatId);
    }
    /**
     * Books a particular seat given by the parameter
     * @param seatId the id of the seat to be booked
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

    public String getSessionId() {
        return this.sessionId;
    }

    public void listofavailableSeats() {
        AnsiConsole.systemInstall();

        //seats.get("A3").occupySeat();

        int count = 0;

        Color color;
        for (Seat seat : seats.values()) {
            color = RED;
            switch (seat.getSeatType()) {
            case STANDARD:
                if (!seat.checkOccupied()) color = GREEN;
                System.out.print(ansi().fg(color).a(seat.getSeatId()) + " ");
                count++;
                break;
            case COUPLE:
                if (!seat.checkOccupied()) color = MAGENTA;
                System.out.print(ansi().fgBright(color).a(seat.getSeatId() + "-" + seat.getSeatId()) + " ");
                count += 2;
                break;
            case DISABLED:
                if (!seat.checkOccupied()) color = BLUE;
                System.out.print(ansi().fg(color).a(seat.getSeatId()) + " ");
                count++;
                break;
            case PREMIUM:
                if (!seat.checkOccupied()) color = YELLOW;
                System.out.print(ansi().fg(color).a("   " + seat.getSeatId()) + "    ");
                count += 3;
                break;
            default:
                break;

            }
            if (count >= 9) {
                System.out.println();
                count = 0;
            }
        }

        AnsiConsole.systemUninstall();
    }

}

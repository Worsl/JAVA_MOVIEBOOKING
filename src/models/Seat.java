package models;


/**
 * Represents a seat for a given booking
 *
 */
public class Seat {

    /**
     * The availability of the seat
     */
    private boolean isOccupied;

    /**
     * The seat number of the seat
     */
    private String seatId;

    /**
     * The movie session of the seat
     */
    private MovieSession movieSession;

    /**
     * The type of the seat
     */
    private SeatType seatType;

    /**
     * Creates a new Seat from the given parameters
     * @param seatNo The Seat's number
     * @param movieSession The Seat's associated movieSession
     */
    public Seat (String seatId, MovieSession movieSession, SeatType seatType) {
        this.isOccupied = false;
        this.seatId = seatId;
        this.movieSession = movieSession;
        this.seatType = seatType;
    }

    @Override
    public String toString() {
        return seatId;
    }

    /**
     * Sets this seat to be occupied
     */
    public void occupySeat() {
        isOccupied = true;
    }

    /**
     * Sets this seat to be unoccupied
     */
    public void unoccupySeat() {
        isOccupied = false;
    }

    /**
     * Gets the occupancy status of this seat
     * @return true if occupied, false otherwise
     */
    public boolean checkOccupied() {
        return this.isOccupied;
    }

    /**
     * Gets the id of this seat
     * @return the seatId of this seat
     */
    public String getSeatId() {
        return this.seatId;
    }

    /**
     * Gets the type of this seat
     * @return the seatType of this seat
     */
    public SeatType getSeatType() {
        return this.seatType;
    }

}

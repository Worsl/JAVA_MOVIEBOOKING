package models;

enum seat {
    STANDARD, DISABLED, COUPLE, PREMIUM;
}

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
     * Creates a new Seat from the given parameters
     * @param seatNo The Seat's number
     * @param movieSession The Seat's associated movieSession
     */
    public Seat (String seatId, MovieSession movieSession) {
        this.isOccupied = false;
        this.seatId = seatId;
        this.movieSession = movieSession;
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

}

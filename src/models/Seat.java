package models;

/**
 * Represents a seat for a given booking
 *
 */
public class Seat {

    /**
     * The seat number of the seat
     */
    private String seatNo;

    /**
     * The availability of the seat
     */
    private boolean isOccupied;

    /**
     * The movie session of the seat
     */
    private MovieSession movieSession;

    /**
     * Creates a new Seat from the given parameters
     * @param seatNo The Seat's number
     * @param movieSession The Seat's associated movieSession
     */
    public Seat (String seatNo, MovieSession movieSession) {
        this.seatNo = seatNo;
        this.isOccupied = false;
        this.movieSession = movieSession;
    }

    /**
     * Gets the occupancy status of this seat
     * @return true if occupied, false otherwise
     */
    public boolean getStatus() {
        return this.isOccupied;
    }

    /**
     * Sets this seat to be occupied
     */
    public void occupySeat() {
        isOccupied = true;
    }

}

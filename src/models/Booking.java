package models;

/**
 * Represents a booking for a movie
 *
 */
public class Booking {

    /**
     * The movie session of the booking
     */
    private MovieSession movieSession;

    /**
     * The name of the person who booked
     */
    private String movieGoerName;

    /**
     * The mobile phone of the person who booked
     */
    private String mobile;

    /**
     * The email address of the person who booked
     */
    private String email;

    /**
     * The transaction ID of the booking
     */
    private String transactionId;

    /**
     * Creates a new Booking from the given parameters
     * @param movieSession The Booking's session
     * @param movieGoerName The Booking's owner
     * @param mobile The Booking's owner's mobile
     * @param email The Booking's owner's email
     * @param transactionId The Booking's transaction ID
     */
    public Booking (MovieSession movieSession, String movieGoerName, String mobile, String email, String transactionId) {
        this.movieSession = movieSession;
        this.movieGoerName = movieGoerName;
        this.mobile = mobile;
        this.email = email;
        this.transactionId = transactionId;
    }


}

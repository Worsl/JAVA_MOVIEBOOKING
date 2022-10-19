package models;

/**
 * Represents a single cinema within a cineplex;
 */
public class Cinema {

    /**
     * The code of the cinema.
     */
    private String cinemaCode;

    /**
     * The capacity of the cinema.
     */
    private int capacity;

    /**
     * The class of the cinema.
     */
    private String classType;

    /**
     * The Cineplex the cinema is in.
     */
    private Cineplex cineplex;

    /**
     * Creates a new Cinema from the given parameters
     * @param cinemaCode the Cinema's code
     * @param capacity the Cinema's capacity
     * @param classType the Cinema's class
     * @param cineplex the Cinema's cineplex
     */
    public Cinema(String cinemaCode, int capacity, String classType, Cineplex cineplex) {
        this.cinemaCode = cinemaCode;
        this.capacity = capacity;
        this.classType = classType;
        this.cineplex = cineplex;
    }

    /**
     * Gets the code of this Cinema.
     * @return this Cinema's name.
     */
    public String getCinemaCode() {
        return this.cinemaCode;
    }

    /**
     * Gets the capacity of this Cinema.
     * @return this Cinema's capacity.
     */
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * Gets the class of this Cinema.
     * @return this Cinema's class.
     */
    public String getClassType() {
        return this.classType;
    }

    /**
     * Gets the cineplex of this Cinema.
     * @return this Cinema's cineplex.
     */
    public Cineplex getCineplex() {
        return this.cineplex;
    }

}

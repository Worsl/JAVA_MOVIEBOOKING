package models;

import java.time.LocalTime;

/**
 * Represents a cineplex theatre
 */
public class Cineplex {

    /**
     * The name of the cineplex.
     */
    private String name;

    /**
     * The location of the cineplex.
     */
    private String location;

    /**
     * The time when the cineplex opens
     */
    private LocalTime openingHours;

    /**
     * The time when the cineplex closes
     */
    private LocalTime closingHours;

    /**
     * Creates a new Cineplex from the given parameters
     * @param name The Cineplex's name
     * @param location The Cineplex's location
     * @param openingHours The Cineplex's opening hours
     * @param closingHours The Cineplex's closing hours
     */
    public Cineplex (String name, String location, String openingHours, String closingHours) {
        this.name = name;
        this.location = location;
        this.openingHours = LocalTime.parse(openingHours);
        this.closingHours = LocalTime.parse(closingHours);
    }

    /**
     * Gets the name of this Cineplex.
     * @return this Cineplex's name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the location of this Cineplex.
     * @return this Cineplex's location.
     */
    public String getLocation() {
        return this.location;
    }

    /**
     * Gets the opening hours of this Cineplex.
     * @return this Cineplex's opening hours.
     */
    public LocalTime getOpeningHours() {
        return this.openingHours;
    }

    /**
     * Gets the closing hours of this Cineplex.
     * @return this Cineplex's closing hours.
     */
    public LocalTime getClosingHours() {
        return this.closingHours;
    }

    /**
     * Sets the name of this Cineplex.
     * @param name the Cineplex's new name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the location of this Cineplex.
     * @param location this Cineplex's new location.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Sets the opening hours of this Cineplex.
     * @param openingHours this Cineplex's new opening hours.
     */
    public void setOpeningHours(LocalTime openingHours) {
        this.openingHours = openingHours;
    }

    /**
     * Sets the closing hours of this Cineplex.
     * @return closingHorus this Cineplex's new closing hours.
     */
    public void setClosingHours(LocalTime closingHours) {
        this.closingHours = closingHours;
    }

    /**
     * Gets the availability of whether this Cineplex is open.
     * @param time The given time to check whether cineplex is open.
     * @return true if cineplex is open at given timing, false otherwise.
     */
    public boolean isOpen(LocalTime time) {
        return (openingHours.compareTo(time) <= 0) && (closingHours.compareTo(time) >= 0);
    }

}

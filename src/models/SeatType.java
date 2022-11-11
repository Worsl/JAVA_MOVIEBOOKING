package models;

/**
 * Represents the SeatType of a movie
 */
public enum SeatType {
    STANDARD(1),
    DISABLED(1),
    COUPLE(2),
    PREMIUM(1.5);

    double multiplier;

    SeatType(double multiplier) {
        this.multiplier = multiplier;
    }
}

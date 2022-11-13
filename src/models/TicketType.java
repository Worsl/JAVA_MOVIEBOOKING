package models;

/**
 * Represents the TicketType of a movie
 */

public enum TicketType {
    ADULT(10), //value associated with each movie represents the baseprice of movie
    SENIOR_CITIZEN(5),
    CHILD(3),
    STUDENT(7);

    int agePricing;

    TicketType(int number)
    {
        this.agePricing = number;
    }

    /**
     * returns the value associated with the TicketType Enum
     */
    public int getAgePricing()
    {

        return this.agePricing;
    }

}

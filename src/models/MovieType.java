package models;
/**
 * MovieType.java
 *
 * Written on Tuesday, 25 October 2022.
 */


 /**
 * Represents the MovieType of a movie
 */

public enum MovieType {

    REGULAR(2),  //value associated with each movie represents the baseprice of movie
    THREE_DIMENSIONAL(5);

    int basePrice;

    MovieType(int price)
    {
        this.basePrice = price;
    }

    int getBasePrice()
    {
        // returns the value associated with the MovieType Enum
        return this.basePrice;
    }

    public static MovieType getMovieType(String s) {
        // TODO match movie type with given string s
        return REGULAR;
    }

}

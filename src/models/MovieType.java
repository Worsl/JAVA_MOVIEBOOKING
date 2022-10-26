package models;
/**
 * MovieType.java
 *
 * Written on Tuesday, 25 October 2022.
 */

public enum MovieType {


    REGULAR(4),   //value associated with each movie represents the baseprice of movie
    THREE_DIMENSIONAL(10),
    Placeholder(0);

    int basePrice;

    MovieType(int price)
    {
        this.basePrice = price;
    }

    public static MovieType getMovieType(String s) {
        // TODO match movie type with given string s
        return Placeholder;
    }

}

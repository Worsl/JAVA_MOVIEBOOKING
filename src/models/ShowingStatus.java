package models;
/**
 * ShowingStatus.java
 *
 * Written on Tuesday, 25 October 2022.
 */


/**
 * Represents the ShowingStatus of a movie
 */

public enum ShowingStatus {

    COMING_SOON(0), 
    PREVIEW(1), 
    NOW_SHOWING(2), 
    END_OF_SHOWING(3); 

    int status;

    ShowingStatus(int number) 
    {
        this.status = number;
    }

    public int getInt()
    {
        // returns the value associated with the ShowingStatus Enum
        return this.status;
    }

    public static ShowingStatus getShowingStatus(String s) {
        // TODO match showing status with given string s
        return COMING_SOON ;
    }

}


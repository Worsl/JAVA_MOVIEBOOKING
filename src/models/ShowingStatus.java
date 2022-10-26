package models;
/**
 * ShowingStatus.java
 *
 * Written on Tuesday, 25 October 2022.
 */

public enum ShowingStatus {

    COMING_SOON, 
    PREVIEW, 
    NOT_SHOWING, 
    TO_BE_ACCOUNCED,
    Placeholder;
    
    public static ShowingStatus getShowingStatus(String s) {
        // TODO match showing status with given string s
        return Placeholder;
    }
}

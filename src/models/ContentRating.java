package models;
/**
 * ContentRating.java
 *
 * Written on Tuesday, 25 October 2022.
 */


/**
* Represents the ContentRating
*/
public enum ContentRating {
    PG, 
    PG13,
    NC16, 
    M18, 
    R21,
    Placeholder;

    public static ContentRating getContentRating(String s) {
        // TODO match showing status with given string s
        return Placeholder;
    }
}

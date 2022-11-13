package models;
/**
 * ContentRating.java
 *
 * Written on Tuesday, 25 October 2022.
 */


/**
* Represents the ContentRating enum
*/
public enum ContentRating {
    PG,
    PG13,
    NC16,
    M18,
    R21;

    /**
     * @return ContentRating ENUM based on String parsed
     */
    public static ContentRating getContentRating(String s) {
        switch(s.toUpperCase())
        {
            case "PG":{
                return PG;
            }

            case "PG13":{
                return PG13;
            }

            case "NC16":{
                return NC16;
            }

            case "M18":{
                return M18;
            }

            case "R21":{
                return R21;
            }

            default:
                return PG;

        }

    }


}

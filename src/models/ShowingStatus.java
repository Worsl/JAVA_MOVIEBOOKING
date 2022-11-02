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
        return this.status;
    }

    /**
     * @return ShowingStatus ENUM based on String parsed
     */
    public static ShowingStatus getShowingStatus(String s) {
        
        switch(s.toUpperCase())
        {

            case "COMING_SOON":{
                return COMING_SOON;
            }

            case "PREVIEW":{
                return PREVIEW;
            }

            case "NOW_SHOWING":
            {
                return NOW_SHOWING;
            }

            case "END_OF_SHOWING":
            {
                return  END_OF_SHOWING;
            }

            default:
                return END_OF_SHOWING;

        }
    }

}


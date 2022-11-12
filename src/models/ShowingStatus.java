package models;

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
     * Returns the corresponding showing status based on parameters
     * @param status The string to be matched
     * @return ShowingStatus ENUM based on String parsed
     */
    public static ShowingStatus getShowingStatus(String status) {

        switch(status.toUpperCase())
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
                return END_OF_SHOWING;
            }

            default:
                return END_OF_SHOWING;

        }
    }

}


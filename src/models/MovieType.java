package models;



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
        return this.basePrice;
    }

    /**
     * @return MovieType ENUM based on String parsed
     */
    public static MovieType getMovieType(String s) {

        switch(s.toUpperCase()){
            case "REGULAR":{
                return REGULAR;
            }

            case "THREE_DIMESIONAL":{
                return THREE_DIMENSIONAL;
            }

            default:
                return REGULAR;
        }
    }

}

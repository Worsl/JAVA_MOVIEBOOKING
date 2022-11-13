package models;
/**
 * CinemaClass.java
 *
 * Written on Tuesday, 25 October 2022.
 */


 /**
 * Represents a CinemaClass Enum
 */
public enum CinemaClass {
    PLATINIUM(5),
    STANDARD(3);

    int loungePrice;

    CinemaClass(int price)
    {
        this.loungePrice = price;
    }

    int getLoungePrice()
    {
        return this.loungePrice;
    }

    /**
     * returns the corresponding cinema class based on the string given
     * @param cls The Cinema class to be parsed as CinemaClass
     * @return The corresponding CinemaClass value
     */
    public static CinemaClass getCinemaClass(String cls) {
        if (cls.equalsIgnoreCase("platinum")) return PLATINIUM;
        else return STANDARD;
    }


}

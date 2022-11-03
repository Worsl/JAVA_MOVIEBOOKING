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


}

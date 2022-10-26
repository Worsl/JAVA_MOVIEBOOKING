package models;
/**
 * ContentRating.java
 *
 * Written on Wednesday, 26 October 2022.
 */

  /**
  * Represents the TicketType
  */

public enum TicketType {
    ADULT(10),
    SENIOR_CITIZEN(5),
    CHILD(3),
    STUDENT(7);
    
    int agePricing;
    
    TicketType(int number)
    {
        this.agePricing = number;
    }

    public static void main(String[] args) {
        
    }


}

import models.Cineplex;
import java.util.LinkedList;

/**
 * The entry point for the admin portal
 *
 * Written on Wednesday, 19 October 2022.
 */

class Admin {
     public static void main(String[] args) {
         LinkedList<Cineplex> cineplexes = Cineplex.getCineplexes();
         for (Cineplex c: cineplexes) {
             System.out.println(c.getName());
             System.out.println(c.getLocation());
             System.out.println(c.getOpeningHours());
             System.out.println(c.getClosingHours());
             System.out.println();
         }

    }
}

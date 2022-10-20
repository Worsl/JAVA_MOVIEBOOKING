import models.*;
import java.util.LinkedList;

/**
 * The entry point for the admin portal
 *
 * Written on Wednesday, 19 October 2022.
 */

class Admin {
     public static void main(String[] args) {
         LinkedList<Cineplex> cineplexes = Cineplex.getCineplexes();
         LinkedList<Cinema> cinemas = Cinema.getCinemas(cineplexes);
         LinkedList<Movie> movies = Movie.getMovies();

         // for (Cineplex c: cineplexes) {
         //     System.out.println(c.getName());
         //     System.out.println(c.getLocation());
         //     System.out.println(c.getOpeningHours());
         //     System.out.println(c.getClosingHours());
         //     System.out.println();
         // }

         // for (Cinema c: cinemas) {
         //     System.out.println(c.getCinemaCode());
         //     System.out.println(c.getClassType());
         //     System.out.println(c.getCineplex().getName());
         //     System.out.println();
         // }

         // for (Movie m: movies) {
         //     System.out.println(m.getTitle());
         //     System.out.println(m.getShowingStatus());
         //     System.out.println(m.getSynopsis());
         //     System.out.println(m.getDirector());
         //     System.out.println(m.getCast());
         //     System.out.println(m.getMovieType());
         //     System.out.println();
         // }
    }
}

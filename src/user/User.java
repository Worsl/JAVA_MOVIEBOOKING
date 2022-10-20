import models.*;
import java.util.LinkedList;

/**
 * The entry point for public users
 *
 * Written on Thursday, 20 October 2022.
 */

public class User {

    public static void main(String[] args) {
        LinkedList<Cineplex> cineplexes = Cineplex.getCineplexes();
        LinkedList<Cinema> cinemas = Cinema.getCinemas(cineplexes);
        LinkedList<Movie> movies = Movie.getMovies();
    }
}

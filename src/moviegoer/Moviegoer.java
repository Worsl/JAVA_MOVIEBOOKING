import models.*;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * The entry point for public users
 *
 * Written on Thursday, 20 October 2022.
 */

class Moviegoer {

    public static void main(String[] args) {
        LinkedList<Cineplex> cineplexes = Cineplex.getCineplexes();
        LinkedList<Cinema> cinemas = Cinema.getCinemas(cineplexes);
        LinkedList<Movie> movies = Movie.getMovies();

        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to MOBLIMA!");
        int in = 0;
        while (in != 7) {
            System.out.println("What would you like to do today?");
            System.out.println("1. List / Search movies");
            System.out.println("2. Look at a movie's details & reviews");
            System.out.println("3. Show top 5 movies");
            System.out.println("4. Review a movie");
            System.out.println("5. Book a movie");
            System.out.println("6. View booking history");
            System.out.println("7. Exit");
            in = sc.nextInt();

            switch(in) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                System.out.println("Thank you for using MOBLIMA!");
                break;
            }
        }

    }
}

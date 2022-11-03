import models.*;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Scanner;


/**
 * The entry point for public users
 *
 * Written on Thursday, 20 October 2022.
 */

class Moviegoer {

    public static void main(String[] args) {
        // Key = Cineplex Name, Value = Cineplex
        HashMap<String, Cineplex> cineplexes = DAO.getCineplexes();
        // Key = Cinema Code, Value = Cinema
        HashMap<String, Cinema> cinemas = DAO.getCinemas(cineplexes);
        // Key = Movie Title, Value = movie object
        HashMap<String, Movie> movies = DAO.getMovies();
        // Key = Movie Title, Value = List of corresponding sessions
        HashMap<String, LinkedList<MovieSession>> sessions = DAO.getSessions(cinemas, movies);
        // 
        HashMap<String, LinkedList<MovieSession>> sessions2= DAO.getSessions2(cinemas,movies);

        
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to MOBLIMA!");
        //MovieSession ms = new MovieSession("14:30", cinemas.get("CAAMK01"), movies.get("Black Adam"));
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
                Booking.createBooking(cinemas, movies, sessions, sessions2);
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

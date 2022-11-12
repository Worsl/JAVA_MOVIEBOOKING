import models.*;
import moviegoer.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.io.Console;


/**
 * The entry point for public users
 *
 * Written on Thursday, 20 October 2022.
 */

class Moviegoer {

    public static boolean validateUser(HashMap<String, User> users, String username, String password) {
        return users.containsKey(username) && users.get(username).validatePassword(password);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Key = User name, Value = User
        HashMap<String, User> users = DAO.getUsers();

        System.out.println("Welcome to MOBLIMA! Please enter your login details: ");
        Console console = System.console();
        String username = "", password = "";

        User currentUser;
        boolean check = true;

        while (check) {
            System.out.print("Username: ");
            username = sc.nextLine();
            password = new String(console.readPassword("Password: "));
            check = !validateUser(users, username, password);
            if (check) System.out.println("Incorrect username or password");
        }

        currentUser = users.get(username);

        int in = 0;
        while (in != 7) {
            // Key = Cineplex Name, Value = Cineplex
            HashMap<String, Cineplex> cineplexes = DAO.getCineplexes();
            // Key = Cinema Code, Value = Cinema
            HashMap<String, Cinema> cinemas = DAO.getCinemas(cineplexes);
            // Key = Movie Title, Value = movie object
            HashMap<String, Movie> movies = DAO.getMovies();
            // Key = Movie Title, Value = List of corresponding sessions
            HashMap<String, ArrayList<MovieSession>> sessions = DAO.getSessions(cinemas, movies);
            // Key = Session Id, Value = Session
            HashMap<String, MovieSession> sessionsById = DAO.getSessionsById(sessions);
            HashMap<String, Booking> bookings = DAO.getBookings(sessionsById, users);

            DAO.setReviews(movies, users);
            DAO.setTickets(bookings);
            MoviegoerBooking.setTickets(sessionsById, bookings);

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
                functionsByEeChern.viewMovieList(movies);
                break;
            case 2:
                functionsByEeChern.lookForMovieDetails(movies);
                break;
            case 3:
                functionsByEeChern.listTop5(movies);
                break;
            case 4:
                functionsByEeChern.reviewMovie(movies, currentUser);
                break;
            case 5:
                MoviegoerBooking.createBooking(sessions, currentUser, sc);
                break;
            case 6:
                MoviegoerBooking.viewBookingRecord(currentUser, bookings);
                break;
            case 7:
                System.out.print("Thank you for using MOBLIMA!");
                break;
            }
            System.out.println();
        }

    }
}

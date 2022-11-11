import models.*;
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
        // Key = User name, Value = User
        HashMap<String, User> users = DAO.getUsers();
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

                // Key = Movie Title, Value = List of corresponding reviews
        DAO.getReviews(movies); // updates reviews into list in movie object

        Scanner sc = new Scanner(System.in);

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
                System.out.println();
                break;
            case 2:
                functionsByEeChern.lookForMovieDetails(movies);
                System.out.println();
                break;
            case 3:
                functionsByEeChern.listTop5(movies);
                break;
            case 4:
                functionsByEeChern.reviewMovie(movies, currentUser);
                System.out.println();
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

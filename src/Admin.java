import models.*;
import admin.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.io.Console;

/**
 * The entry point for the admin portal
 *
 * Written on Wednesday, 19 October 2022.
 */

class Admin {
     public static void main(String[] args) {

         Scanner sc = new Scanner(System.in);
         Console cons;

         System.out.println("Please enter your credentials to visit the Admin Portal:");
         System.out.print("Username: ");
         String username = sc.nextLine();

         cons = System.console();
         char[] pwd = cons.readPassword("Enter your password: ");
         String password = new String(pwd);

         if (username.equals("admin") && password.equals("password"))
             {
                 System.out.println("Login sucessful");
             }
         else{
             System.out.println("Error. invalid access attempt, exiting out of system.");
             System.exit(0);
         }

        System.out.println("Welcome to MOBLIMA Admin Portal! :)");
        int in = 0;
        while (in != 10) {
            // Key = Cineplex Name, Value = Cineplex
            HashMap<String, Cineplex> cineplexes = DAO.getCineplexes();
            // Key = Cinema Code, Value = Cinema
            HashMap<String, Cinema> cinemas = DAO.getCinemas(cineplexes);
            // Key = Movie Title, Value = movie object
            HashMap<String, Movie> movies = DAO.getMovies();
            // Key = Movie Title, Value = List of corresponding sessions
            HashMap<String, ArrayList<MovieSession>> sessions = DAO.getSessions(cinemas, movies);

            System.out.println("What would you like to do today?");
            System.out.println("1. Add a new movie");
            System.out.println("2. Update a movie's details");
            System.out.println("3. Delete an existing movie");
            System.out.println("4. Show movies in system");
            System.out.println("5. Add a new movie session");
            System.out.println("6. Update a movie session");
            System.out.println("7. Delete a movie session");
            System.out.println("8. Show movie sessions in system");
            System.out.println("9. Add a holiday to system");
            System.out.println("10. Exit");
            in = sc.nextInt();
            sc.nextLine();
            switch(in) {
            case 1:
                AdminMovie.addMovie(movies, sc);
                break;
            case 2:
                AdminMovie.editMovie(sc);
                break;
            case 3:
                AdminMovie.deleteMovie(movies, sc);
                break;
            case 4:
                AdminMovie.showMovies(movies);
                break;
            case 5:
                AdminSession.newSession(cinemas, movies, sessions, sc);
                break;
            case 6:
                AdminSession.updateSession(cinemas, movies, sc);
                break;
            case 7:
                AdminSession.deleteSession(sc);
                break;
            case 8:
                AdminSession.showSession(sessions);
                break;
            case 9:
                AdminUtils.addHolidayDate(sc);
                break;
            case 10:
                System.out.println("Thank you for using MOBLIMA Admin Portal!");
                break;
            }
            System.out.println();
        }
    }
}

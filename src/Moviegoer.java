import models.*;
import moviegoer.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.io.Console;
import java.io.IOException;

import de.codeshelf.consoleui.prompt.ConsolePrompt;
import de.codeshelf.consoleui.prompt.InputResult;
import de.codeshelf.consoleui.prompt.ListResult;
import de.codeshelf.consoleui.prompt.PromtResultItemIF;
import de.codeshelf.consoleui.prompt.builder.PromptBuilder;
import jline.TerminalFactory;
import org.fusesource.jansi.AnsiConsole;

/**
 * The entry point for public users
 *
 * Written on Thursday, 20 October 2022.
 */

class Moviegoer {

    public static boolean validateUser(HashMap<String, User> users, String username, String password) {
        return users.containsKey(username) && users.get(username).validatePassword(password);
    }

    public static void main(String[] args) throws InterruptedException {
        AnsiConsole.systemInstall();
        Scanner sc = new Scanner(System.in);

        // Key = User name, Value = User
        HashMap<String, User> users = DAO.getUsers();

        System.out.println("Welcome to MOBLIMA! Please enter your login details: ");
        Console console = System.console();
        String username = "", password = "";

        User currentUser;
        boolean check = true;

        while (check) {
            try {
                ConsolePrompt prompt = new ConsolePrompt();                     // #2
                PromptBuilder promptBuilder = prompt.getPromptBuilder();        // #3
          
                promptBuilder.createInputPrompt()                                // #4
                    .name("username")                                                 // #2
                    .message("Please enter your username")                            // #3
                    .defaultValue("UserName123")                                     // #4
                    //.mask('*')                                                  // #5
                    .addPrompt();  
                    
                    promptBuilder.createInputPrompt()                                // #4
                    .name("password")                                                 // #2
                    .message("Please enter your password")                            // #3
                    .mask('*')                                                  // #5
                    .addPrompt();     
                
                HashMap<String, ? extends PromtResultItemIF> result = prompt.prompt(promptBuilder.build()); // #5
                InputResult promptusername = (InputResult) result.get("username");
                InputResult promptpassword = (InputResult) result.get("password");
    
                check = !validateUser(users,promptusername.getInput(),promptpassword.getInput());
                if (check) System.out.println("Incorrect username or password");
                username = promptusername.getInput();
              } catch (IOException e) {
                e.printStackTrace();
        }finally {
            try {
              TerminalFactory.get().restore();
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        }

        currentUser = users.get(username);

        int in = 0;
        /*while (in != 7) {
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
            LinkedList<Booking> bookings = DAO.getBookings(sessionsById, users);

            DAO.setReviews(movies, users);
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
        }*/

        try {
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
            LinkedList<Booking> bookings = DAO.getBookings(sessionsById, users);

            DAO.setReviews(movies, users);
            MoviegoerBooking.setTickets(sessionsById, bookings);
            
            ConsolePrompt prompt = new ConsolePrompt();                     // #2
            PromptBuilder promptBuilder = prompt.getPromptBuilder();
            promptBuilder.createListPrompt()                                                // #1
            .name("mainmenu")                                                            // #2
            .message("What would you like to do today?")                                          // #3
            .newItem("1").text("1. List / Search movies").add()  // without name (name defaults to text)     #4
            .newItem("2").text("2. Look at a movie's details & reviews").add()                                 // #5
            .newItem("3").text("3. Show top 5 movies").add()                                         
            .newItem("4").text("4. Review a movie").add()
            .newItem("5").text("5. Book a movie").add()
            .newItem("6").text("6. View booking history").add()
            .newItem("7").text("7. Exit").add()
            // .pageSize(10)                                                                 #6
            // .relativePageSize(66)																											   #7
            .addPrompt();

            HashMap<String, ? extends PromtResultItemIF> result = prompt.prompt(promptBuilder.build());
            ListResult listchoice = (ListResult) result.get("mainmenu");
            int listchoiceint = Integer.parseInt(listchoice.getSelectedId());
            
            switch(listchoiceint) {
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
                    AnsiConsole.systemUninstall();
                    try {
                        TerminalFactory.get().restore();
                        } catch (Exception e) {
                        e.printStackTrace();
                        }
                    MoviegoerBooking.createBooking(sessions, currentUser, sc);
                    break;
                case 6:
                    MoviegoerBooking.viewBookingRecord(currentUser, bookings);
                    break;
                case 7:
                    System.out.print("Thank you for using MOBLIMA!");
                    break;
                }
          } catch (IOException e) {
            e.printStackTrace();
            }finally {
                try {
                TerminalFactory.get().restore();
                } catch (Exception e) {
                e.printStackTrace();
                }
            }
            AnsiConsole.systemUninstall();
    }
}

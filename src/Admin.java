import models.*;
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
         // Key = Cineplex Name, Value = Cineplex
         HashMap<String, Cineplex> cineplexes = DAO.getCineplexes();
         // Key = Cinema Code, Value = Cinema
         HashMap<String, Cinema> cinemas = DAO.getCinemas(cineplexes);
         // Key = Movie Title, Value = movie object
         HashMap<String, Movie> movies = DAO.getMovies();
         // Key = Movie Title, Value = List of corresponding sessions
         HashMap<String, ArrayList<MovieSession>> sessions = DAO.getSessions(cinemas, movies);

         Scanner sc = new Scanner(System.in);
		Console cons;

	System.out.println("Please enter your credentials to visit the Admin Portal:"); //username is admin
	System.out.print("Username:");
	if (sc.nextLine() == "admin");


	cons = System.console();
	char[] pwd = cons.readPassword("Enter your password: "); //password is password
	String password = new String(pwd);
		
	if (password.equals("password"))
	{
		System.out.println("Login sucessful");
	}
	else{
		System.out.println("Error. invalid access attempt, exiting out of system.");
		System.exit(0);
	}
	     
	     
        System.out.println("Welcome to MOBLIMA Admin Portal! :)");
        int in = 0;
        while (in != 9) {
            System.out.println("What would you like to do today?");
            System.out.println("1. Add a new movie");
            System.out.println("2. Update a movie's details");
            System.out.println("3. Delete an existing movie");
            System.out.println("4. Show movies in system");
            System.out.println("5. Add a new time slot for a movie");
            System.out.println("6. Update a time slot for a movie");
            System.out.println("7. Delete an existing time slot for a movie");
            System.out.println("8. Show movie sessions in system");
            System.out.println("9. Exit");
            in = sc.nextInt();

            switch(in) {
            case 1:
            	System.out.println("Enter movie title: ");
            	String title = sc.next();

            	System.out.println("Enter movie showing status: ");
            	String showingStatus = sc.next();
            	System.out.println("Enter movie synopsis: ");
            	String synopsis = sc.next();
            	System.out.println("Enter movie director: ");
            	String director = sc.next();
            	System.out.println("Enter movie cast: ");
            	String cast = sc.next();
            	System.out.println("Enter movie type: ");
            	String type = sc.next();
            	System.out.println("Enter movie duration: ");
            	int duration = sc.nextInt();
            	System.out.println("Enter movie content rating: ");
            	String contentRating = sc.next();
            	
            	DAO.addMovie(title, showingStatus, synopsis, director, cast, type, duration, contentRating);
            	break;
            case 2:
            	System.out.println("Enter movie title: ");
            	String title2 = sc.next();
            	
            	System.out.println("Select category to update ");
            	System.out.println("1: showing status");
            	System.out.println("2: synopsis");
            	System.out.println("3: director");
            	System.out.println("4: cast");
            	System.out.println("5: movie type");
            	System.out.println("6: duration");
            	System.out.println("7: content rating");
            	int category = sc.nextInt();
            	
            	System.out.println("Enter updated text");
            	String edit = sc.next();
            	
            	DAO.updateMovie(title2, category, edit);
            	break;
            case 3:
            	System.out.println("Enter movie title: ");
            	String title3 = sc.next();
            	
            	DAO.deleteMovie(title3);
                break;
            case 4:
                DAO.showMovie();
            	break;
            case 5:
            	System.out.println("Enter cinema: ");
            	String cinema5 = sc.next();
            	System.out.println("Enter movie title: ");
            	String title5 = sc.next();
            	System.out.println("Enter time slot: ");
            	String timeSlot5 = sc.next();
            	
            	DAO.addTimeSlot(cinema5, title5, timeSlot5);
                break;
            case 6:  
            	System.out.println("Enter cinema: ");
            	String cinema6 = sc.next();
            	System.out.println("Enter movie title: ");
            	String title6 = sc.next();
            	System.out.println("Enter time slot: ");
            	String timeSlot6 = sc.next();
            	System.out.println("Enter new time slot: ");
            	String newTimeSlot6 = sc.next();
            	
            	DAO.updateTimeSlot(cinema6, title6, timeSlot6, newTimeSlot6);
            	break;
            case 7:  
            	System.out.println("Enter cinema: ");
            	String cinema7 = sc.next();
            	System.out.println("Enter movie title: ");
            	String title7 = sc.next();
            	System.out.println("Enter time slot: ");
            	String timeSlot7 = sc.next();
            	
            	DAO.deleteTimeSlot(cinema7, title7, timeSlot7);
            	break;
            case 8:
            	DAO.showTimeSlot();
                break;
            case 9:
                System.out.println("Thank you for using MOBLIMA Admin Portal!");
                break;
            }
        }
    }
}

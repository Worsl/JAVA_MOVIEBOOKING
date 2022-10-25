import models.*;
import java.util.LinkedList;
import java.util.Scanner;

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

         Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to MOBLIMA Admin Portal!");
        int in = 0;
        while (in != 7) {
            System.out.println("What would you like to do today?");
            System.out.println("1. Add a new movie");
            System.out.println("2. Update a movie's details");
            System.out.println("3. Delete an existing movie");
            System.out.println("4. Add a new time slot for a movie");
            System.out.println("5. Update a time slot for a movie");
            System.out.println("6. Delete an existing time slot for a movie");
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
                System.out.println("Thank you for using MOBLIMA Admin Portal!");
                break;
            }
        }
    }
}

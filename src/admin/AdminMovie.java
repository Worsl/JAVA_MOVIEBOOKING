package admin;

import models.*;
import java.util.HashMap;
import java.util.Scanner;

/**
 * The functions accessible to admin regarding Movie
 */
public class AdminMovie {
    public static void addMovie(HashMap<String, Movie> movies, Scanner sc) {

        System.out.println("Enter movie title: ");
        String title = sc.nextLine();

        if (movies.containsKey(title)) {
            System.out.println("Movie is already in system.");
            return;
        }
        System.out.println("Enter movie showing status: ");
        String showingStatus = sc.next(); // TODO
        System.out.println("Enter movie synopsis: ");
        String synopsis = sc.nextLine();
        System.out.println("Enter movie director: ");
        String director = sc.nextLine();
        System.out.println("Enter movie cast: ");
        String cast = sc.nextLine();
        System.out.println("Enter movie type: ");
        String type = sc.nextLine();
        System.out.println("Enter movie duration: ");
        int duration = sc.nextInt();
        System.out.println("Enter movie content rating: ");
        String contentRating = sc.next(); // TODO

        DAO.addMovie(new Movie(title, ShowingStatus.getShowingStatus(showingStatus), synopsis, director, cast, MovieType.getMovieType(type), duration, ContentRating.getContentRating(contentRating)));
    }

    public static void editMovie(Scanner sc) {
        System.out.println("Enter movie title: ");
        String title = sc.next();

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

        DAO.updateMovie(title, category, edit);
    }

    public static void deleteMovie(HashMap<String, Movie> movies, Scanner sc) {

        System.out.println("Enter movie title: ");
        String title = sc.nextLine();
        if (movies.containsKey(title)) {
            for (Movie movie: movies.values()) {
                if (movie.getTitle().equals(title)) movie.remove();
            }
            DAO.deleteMovie(movies);
        }
        else
            System.out.println("Movie title not found");
    }

    public static void showMovies(HashMap<String, Movie> movies) {
        for (Movie m: movies.values()) {
            System.out.println("Title: " + m.getTitle());
            System.out.println("Showing Status: " + m.getShowingStatus().name());
            System.out.println("Synopsis: " + m.getSynopsis());
            System.out.println("Director: " + m.getDirector());
            System.out.println("Cast: " + m.getCast());
            System.out.println("Movie Type: " + m.getMovieType().name());
            System.out.println("Duration: " + m.getDuration() + " minutes");
            System.out.println("Content Rating: " + m.getContentRating().name());
            System.out.println();
        }

    }

}

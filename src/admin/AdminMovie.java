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
        ShowingStatus status = null;
        int showingStatusOpt = 0;
        while (showingStatusOpt > 4 || showingStatusOpt <= 0) {
            System.out.println("1. Coming Soon");
            System.out.println("2. Preview");
            System.out.println("3. Now Showing");
            System.out.println("4. End of Showing");
            showingStatusOpt = sc.nextInt();
            switch (showingStatusOpt) {
                case 1:
                    status = ShowingStatus.COMING_SOON;
                    break;
                case 2:
                    status = ShowingStatus.PREVIEW;
                    break;
                case 3:
                    status = ShowingStatus.NOW_SHOWING;
                    break;
                case 4:
                    status = ShowingStatus.END_OF_SHOWING;
                    break;
                default:
                    System.out.println("You have selected an invalid choice.");
                    status = null;
                    break;
            }
        }
        sc.nextLine();
        System.out.println("Enter movie synopsis: ");
        String synopsis = sc.nextLine();
        System.out.println("Enter movie director: ");
        String director = sc.nextLine();
        System.out.println("Enter movie cast: ");
        String cast = sc.nextLine();
        System.out.println("Enter movie type: ");
        MovieType type = null;
        int typeOpt = 0;
        while (typeOpt > 2 || typeOpt <= 0) {
            System.out.println("1. Regular");
            System.out.println("2. 3D");
            typeOpt = sc.nextInt();
            switch (typeOpt) {
                case 1:
                    type = MovieType.REGULAR;
                    break;
                case 2:
                    type = MovieType.THREE_DIMENSIONAL;
                    break;
                default:
                    System.out.println("You have selected an invalid choice.");
                    type = null;
                    break;
            }
        }

        System.out.println("Enter movie duration: ");
        int duration = sc.nextInt();
        System.out.println("Enter movie content rating: ");
        ContentRating rating = null;
        int contentRatingOpt = 0;
        while (contentRatingOpt > 5 || contentRatingOpt <= 0) {
            System.out.println("1. PG");
            System.out.println("2. PG13");
            System.out.println("3. NC16");
            System.out.println("4. M18");
            System.out.println("5. R21");
            contentRatingOpt = sc.nextInt();
            switch (contentRatingOpt) {
                case 1:
                    rating = ContentRating.PG;
                    break;
                case 2:
                    rating = ContentRating.PG13;
                    break;
                case 3:
                    rating = ContentRating.NC16;
                    break;
                case 4:
                    rating = ContentRating.M18;
                    break;
                case 5:
                    rating = ContentRating.R21;
                    break;
                default:
                    System.out.println("You have selected an invalid choice.");
                    status = null;
            }
        }

        DAO.addMovie(new Movie(title, status, synopsis, director, cast, type, duration, rating));
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

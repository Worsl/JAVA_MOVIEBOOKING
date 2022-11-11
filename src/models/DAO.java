package models;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.LinkedList;
import java.io.*;
/**
 * DAO.java
 *
 * Written on Saturday, 29 October 2022.
 */

public class DAO {

    /**
     * Reads data from file and parses into a map of (Cineplex Name -> Cineplex)
     * @return a map of the available Cineplexes
     */
    public static HashMap<String, Cineplex> getCineplexes() {

        HashMap<String, Cineplex> map = new HashMap<String, Cineplex>();
        try {
            File f = new File("./data/cineplexes.csv");
            Scanner sc = new Scanner(f);
            String in, params[];
            while (sc.hasNextLine()) {
                in = sc.nextLine();
                // regex to split by comma, but not those within quotation marks
                params = in.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                map.put(params[0], new Cineplex(params[0], params[1], params[2], params[3]));
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("Cineplexes File not found");
            e.printStackTrace();
        }

        return map;

    }

    /**
     * Reads data from file and parses into a map of (Cinema Code -> Cinemas)
     * @param cineplexes the map of current available cineplexes
     * @return a map of the available cinemas
     */
    public static HashMap<String, Cinema> getCinemas(HashMap<String, Cineplex> cineplexes) {

        HashMap<String, Cinema> map = new HashMap<String, Cinema>();
        try {
            File f = new File("./data/cinema.csv");

            Scanner sc = new Scanner(f);

            String in, params[];
            while (sc.hasNextLine()) {
                in = sc.nextLine();
                // regex to split string by comma, but not those within quotation marks
                params = in.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                Cineplex cineplex = cineplexes.get(params[2]);

                if (cineplex != null)
                    map.put(params[0], new Cinema(params[0], CinemaClass.getCinemaClass(params[1]), cineplex));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Cinemas File not found");
            e.printStackTrace();
        }

        return map;

    }


    /**
     * Reads data from file and parses into a map of (Movie Title -> Movies)
     * @return a map of the available movies
     */
    public static HashMap<String, Movie> getMovies() {

        HashMap<String, Movie> map = new HashMap<String, Movie>();
        try (BufferedReader br = new BufferedReader(new FileReader("./data/movies.csv"))) {
            File f = new File("./data/movies.csv");

            // Scanner sc = new Scanner(f);
            String in = br.readLine(), params[];

            while (in != null) {
                // regex to split string by comma, but not commas within quotation marks
                params = in.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                map.put(params[0], new Movie(params[0], ShowingStatus.getShowingStatus(params[1]), params[2], params[3], params[4], MovieType.getMovieType(params[5]), Integer.parseInt(params[6]), ContentRating.getContentRating(params[7])));
                in = br.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Movies File not found");
            e.printStackTrace();
        }  catch (IOException e) {
            e.printStackTrace();
        }

        return map;

   }


    /**
     * Reads data from file and parses into a map of (Movie Title -> Session)
     * @return a map of the available sessions
     */
    public static HashMap<String, LinkedList<MovieSession>> getSessions(HashMap<String, Cinema> cinemas, HashMap<String, Movie> movies) {

        HashMap<String, LinkedList<MovieSession>> map = new HashMap<String, LinkedList<MovieSession>>();
        try {
            File f = new File("./data/moviesessions.csv");
            Scanner sc = new Scanner(f);
            String in, params[];

            while (sc.hasNextLine()) {
                in = sc.nextLine();
                // regex to split string by comma, but not commas within quotation marks
                params = in.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                String movieid = params[3];
                Cinema cinema = cinemas.get(params[0]);
                Movie movie = movies.get(params[1]);
                String id = params[4];
                if (cinema != null && movie != null)
                    if (map.get(movieid) != null)
                        map.get(movieid).add(new MovieSession(params[2], cinema, movie, id));
                    else {
                        LinkedList <MovieSession> ll = new LinkedList<MovieSession>();
                        ll.add(new MovieSession(params[2], cinema, movie, id));
                        map.put(movieid, ll);
                    }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Movies File not found");
            e.printStackTrace();
        }

        return map;

   }

   // Reading data for users to choose
   public static HashMap<String, LinkedList<MovieSession>> getSessions2(HashMap<String, Cinema> cinemas, HashMap<String, Movie> movies) {

    HashMap<String, LinkedList<MovieSession>> map = new HashMap<String, LinkedList<MovieSession>>();
    try {
        File f = new File("./data/moviesessions.csv");
        Scanner sc = new Scanner(f);
        String in, params[];

        while (sc.hasNextLine()) {
            in = sc.nextLine();
            // regex to split string by comma, but not commas within quotation marks
            params = in.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

            Cinema cinema = cinemas.get(params[0]);
            Movie movie = movies.get(params[1]);
            String movieid = (params[3]);
            String sessionid = (params[4]);
            if (cinema != null && movie != null)
                if (map.get(sessionid) != null)
                    map.get(sessionid).add(new MovieSession(params[2], cinema, movie, sessionid));
                else {
                    LinkedList <MovieSession> ll = new LinkedList<MovieSession>();
                    ll.add(new MovieSession(params[2], cinema, movie, sessionid));
                    map.put(sessionid, ll);
                }
        }
    } catch (FileNotFoundException e) {
        System.out.println("Movies File not found");
        e.printStackTrace();
    }

        return map;

   }

   /**
     * Reads data from file and parses into a map of (Movie Title -> Reviews)
     * @return a map of the reviews
     */
    public static HashMap<String, LinkedList<Review>> getReviews(HashMap<String, Movie> movies) {

        // TODO populate data from csv into map
        HashMap<String, LinkedList<Review>> map = new HashMap<String, LinkedList<Review>>();

        try {
            File f = new File("./data/reviews.csv");
            Scanner sc = new Scanner(f);
            String in, params[];

            while (sc.hasNextLine()) {
                in = sc.nextLine();
                // regex to split string by comma, but not commas within quotation marks
                params = in.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                // params 0 = rating, params 1 = movietitle, params 2 = reviewer, params 3 = email, params 4 = mobile, params 5 = comment
                int ratingScore = Integer.parseInt(params[0]);
                Movie movie = movies.get(params[1]);
                // TODO retrieve the correct reviewer
                User reviewer = new User(params[2], params[3], params[4], "");
                Review review = new Review(reviewer, ratingScore, params[5]);
                if (movie != null)
                    movie.addReview(review);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Reviews File not found");
            e.printStackTrace();
        }

        return map;

   }

    /**
    * Reads data from file and parses into a map of (User Name -> User)
    * @return a map of the available Cineplexes
    */
   public static HashMap<String, User> getUsers() {

       HashMap<String, User> map = new HashMap<String, User>();
       try {
           File f = new File("./data/users.csv");
           Scanner sc = new Scanner(f);
           String in, params[];
           while (sc.hasNextLine()) {
               in = sc.nextLine();
               // regex to split by comma, but not those within quotation marks
               params = in.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
               map.put(params[0], new User(params[0], params[1], params[2], params[3]));
           }
       } catch (FileNotFoundException e) {
           System.out.println("Users File not found");
           e.printStackTrace();
       }

       return map;

   }

   /**
    * Writes reviews into a CSV file:
     * @param ratingScore The Movie's Rating
     * @param movie The Movie's Title
     * @param reviewer The Reviewer/User's name
     * @param comment The Rating's comments
    */
   public static void writeReviewsToCSV(int ratingScore, String movie, String reviewer, String email, String mobileNumber, String comment) {
        try {
            if(comment == "") comment = " "; // replace blank comments with a single space.
            FileWriter myWriter = new FileWriter("./data/reviews.csv", true);
            myWriter.write(String.valueOf(ratingScore) + "," + movie + "," + reviewer + "," + email + "," + mobileNumber + "," + comment + "\n");
            myWriter.close();
            
        } catch (IOException e) {
            System.out.println("An error occurred when writing review to CSV, please see DAO.java");
            e.printStackTrace();
        }
    }
}

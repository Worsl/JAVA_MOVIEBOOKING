package models;

import java.util.HashMap;
import java.util.Scanner;
import de.codeshelf.consoleui.prompt.ConsolePrompt;
import de.codeshelf.consoleui.prompt.InputResult;
import de.codeshelf.consoleui.prompt.ListResult;
import de.codeshelf.consoleui.prompt.PromtResultItemIF;
import de.codeshelf.consoleui.prompt.builder.PromptBuilder;
import jline.TerminalFactory;
import org.fusesource.jansi.AnsiConsole;
import java.io.IOException;

public class functionsByEeChern {
    /**
     * Checks if the movie exists
     * @param the movie title to be checked and the list of movies
     */
    public static boolean movieExists(HashMap<String, Movie> movies, String movieTitle){
        // Iterating HashMap through for loop
        for (HashMap.Entry<String, Movie> set : movies.entrySet()) {
            String key = set.getKey();
                if(movieTitle.equals(key)){
                    return true;
                } 
        }
        return false;
    }

    /**
     * Queries the user for a movie title to find that movie object.
     * @param movies the map of movies
     * @return Movie 
     */
    public static Movie LookForMovieByTitle(HashMap<String, Movie> movies)  {
        Scanner sc = new Scanner(System.in);
        
        String movieTitle = "";
        boolean check = false;
        // check to see if the movie exists
        while (!check) {
            // System.out.println("Enter the movie title:");
            // movieTitle = sc.nextLine();
            // check = movieExists(movies, movieTitle);
            // if (!check) System.out.println("Movie does not exist, please try again.");
            try {
                ConsolePrompt prompt = new ConsolePrompt();
                PromptBuilder promptBuilder = prompt.getPromptBuilder();

                promptBuilder.createInputPrompt()
                    .name("movieTitle")
                    .message("Enter the movie title")
                    .defaultValue("Example: Black Adam")
                    //.mask('*')
                    .addPrompt();
 
                HashMap<String, ? extends PromtResultItemIF> result = prompt.prompt(promptBuilder.build());
                InputResult promptmovieTitle = (InputResult) result.get("movieTitle");
                movieTitle = promptmovieTitle.getInput();
                check = movieExists(movies, movieTitle);
                if (!check) System.out.println("Movie does not exist, please try again.");
                
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
        
        return movies.get(movieTitle);
    }

    /**
     * Function to be called from Moviegoer.java to review a movie
     * @param movies map of movies
     * @param user current user
     */
    public static void reviewMovie(HashMap<String, Movie> movies, User user) throws IOException{
        Scanner sc = new Scanner(System.in);
        
        // check to see if the movie exists
        String movieTitle = LookForMovieByTitle(movies).getTitle();

        // System.out.println("Enter a rating out of 5:");
         int movieRating = -1;
         //String movieComment;
        // while(!(movieRating >= 1 && movieRating <= 5)){
        //     if (sc.hasNextInt()) {
        //         movieRating = sc.nextInt();
        //         if(!(movieRating >= 1 && movieRating <= 5)){
        //             System.out.println("Please input an integer between 1 and 5.");
        //         }
        //     } else {
        //         System.out.println("Please input an integer.");
        //         sc.next();
        //     }
        // }
        ConsolePrompt prompt = new ConsolePrompt();
        PromptBuilder promptBuilder = prompt.getPromptBuilder();
        promptBuilder.createListPrompt()
        .name("Rating")
        .message("Please rate the movie from 1 to 5 stars: ")
        .newItem("1").text("*").add()
        .newItem("2").text("**").add()
        .newItem("3").text("***").add()
        .newItem("4").text("****").add()
        .newItem("5").text("*****").add()
                        
        .addPrompt();
        
        promptBuilder.createInputPrompt()
        .name("review")
        .message("Enter your comments: ")
        .addPrompt();

        // sc.nextLine(); //flushes the scanner buffer.
        // System.out.println("Enter your comments:");
        // String movieComment = sc.nextLine();

        HashMap<String, ? extends PromtResultItemIF> result = prompt.prompt(promptBuilder.build());
        ListResult rating = (ListResult) result.get("Rating");
        InputResult review1 = (InputResult) result.get("review");
        movieRating = Integer.parseInt(rating.getSelectedId());
        String movieComment = String.format("\"%s\"", review1.getInput());

        Review review = new Review(user, movieRating, movieComment);

        Movie movie = movies.get(movieTitle);
        movie.addReview(review);
        DAO.writeReviewsToCSV(movieRating, movieTitle, user.getName(), user.getEmail(), user.getMobile(), movieComment);
        System.out.println("Successfully added new review.");
    }

    /**
     * Function to determine between rating or sales.
     * @param movies map of movies
     */
    public static void listTop5(HashMap<String, Movie> movies) {
        Scanner sc = new Scanner(System.in);
        int in = 0;
        while (!(in >= 1 & in <= 3)) {
            System.out.println("Select your sorting criterion.");
            System.out.println("1. Average Rating");
            System.out.println("2. Tickets Sold");
            System.out.println("3. Go back.");
            in = sc.nextInt();
        }
        switch(in) {
            case 1:
                listTop5byRatings(movies);
                System.out.println();
                break;
            case 2:
                listTop5bySales(movies);
                System.out.println();
                break;
            case 3:
                System.out.println("Returning...");
                break;
        }
    }
        

    /**
     * lists top 5 movies based on their rating
     * @param movies map of movies
     */ 
    public static void listTop5byRatings(HashMap<String, Movie> movies){
        Movie[] movieList = new Movie[movies.size()];
        int counter = 0;
        for (HashMap.Entry<String, Movie> set : movies.entrySet()) {
            movieList[counter] = set.getValue();
            counter++;
        }
        sortByRating(movieList);

        counter = 1;
        int n = min(5, movies.size());
        for (int i=0; i<n; i++){
            System.out.println("(" + String.valueOf(counter) + ")      " + movieList[i].getTitle() + "      Average Rating: " + movieList[i].getAverageRating());    
            counter++;
        }
    }

    /**
     * Sorts the list of movies via insertion sort by their average rating.
     * @param movieList list of movies to sort
     * @param n size of the list of movies
     */
    private static void sortByRating(Movie[] movieList) {
        int n = movieList.length;
        for (int i = 1; i < n; ++i) {
            Movie key = movieList[i];
            int j = i - 1;
 
            /* Move elements of arr[0..i-1], that are
               greater than key, to one position ahead
               of their current position */
            while (j >= 0 && movieList[j].getAverageRating() < key.getAverageRating()) {
                movieList[j + 1] = movieList[j];
                j = j - 1;
            }
            movieList[j + 1] = key;
        }
    }

    /**
     * Lists the top 5 movies by their number of tickets sold
     * @param movies map of movies
     */
    public static void listTop5bySales(HashMap<String, Movie> movies){
        Movie[] movieList = new Movie[movies.size()];
        int counter = 0;
        for (HashMap.Entry<String, Movie> set : movies.entrySet()) {
            movieList[counter] = set.getValue();
            counter++;
        }
        sortBySales(movieList);

        counter = 1;
        int n = min(5, movies.size());
        for (int i=0; i<n; i++){
            System.out.println("(" + String.valueOf(counter) + ")      " + movieList[i].getTitle() + "      Tickets Sold: " + movieList[i].getTicketsSold());    
            counter++;
        }
    
    }

    /**
     * utility function to find the minimum of two integer values
     * @param a an int
     * @param b an int
     * @return the smaller integer, a if tied.
     */
    private static int min(int a, int b) {
        if(a<=b) return a;
        else return b;
    }

    /**
     * Sorts the list of movies via insertion sort by their number of tickets sold.
     * @param movieList list of movies to sort
     * @param n size of the list of movies
     */
    private static void sortBySales(Movie[] movieList) {
        int n = movieList.length;
        for (int i = 1; i < n; ++i) {
            Movie key = movieList[i];
            int j = i - 1;
 
            /* Move elements of arr[0..i-1], that are
               greater than key, to one position ahead
               of their current position */
            while (j >= 0 && movieList[j].getTicketsSold() < key.getTicketsSold()) {
                movieList[j + 1] = movieList[j];
                j = j - 1;
            }
            movieList[j + 1] = key;
        }
    }

    /**
     * Searches for 1 movie title and outputs its full movie details.
     * @param movies map of movies
     */
    public static void lookForMovieDetails(HashMap<String, Movie> movies){
        Movie movie = LookForMovieByTitle(movies);
        movie.viewMovieDetails();
    }

    /**
     * Prints the whole list of movie titles.
     * @param movies the map of movies
     */
    public static void viewMovieList(HashMap<String, Movie> movies){
        int counter = 1;
        for (HashMap.Entry<String, Movie> set : movies.entrySet()) {
            String key = set.getKey();
            System.out.println("(" + String.valueOf(counter) + ")      " + key);    
            counter++;
        }
    }
}

package models;

import java.util.HashMap;
import java.util.Scanner;

public class functionsByEeChern {
    
    // writes a review for the movie and update csv
    // called from moviegoer module.
    public static void reviewMovie(HashMap<String, Movie> movies){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the movie title:");
        String movieTitle = sc.nextLine();
        System.out.println("Enter your rating:");
        int movieRating = sc.nextInt();
        sc.nextLine(); //flushes the scanner buffer.
        System.out.println("Enter your comments:");
        String movieComment = sc.nextLine();
        
        // TO BE DISCUSSED - needs to be user, but how to pass?
        System.out.println("Enter your name:");
        String reviewerName = sc.nextLine();
        User user = new User(reviewerName, "NULL", "NULL");

        Review review = new Review(user, movieRating, movieComment);

        Movie movie = movies.get(movieTitle);
        movie.addReview(review);
        DAO.writeReviewsToCSV(movieRating, movieTitle, reviewerName, movieComment);

    }

    // lists top 5 movies based on their rating
    public void listTop5byRatings(){

    }

    // output movie details from hashmap
    public void viewMovieDetails(){
        
    }
}

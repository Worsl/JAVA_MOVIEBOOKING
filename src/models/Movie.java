package models;

import java.util.LinkedList;

/**
 * Represents a movie
 */
public class Movie {

    /**
     * The title of the movie.
     */
    private String title;

    /**
     * The showing status of the movie. (Coming Soon, Preview, Now Showing)
     */
    private ShowingStatus showingStatus;

    /**
     * The synopsis of the movie.
     */
    private String synopsis;

    /**
     * The director of the movie.
     */
    private String director;

    /**
     * The cast of  the movie.
     */
    private String cast;

    /**
     * The type of the movie. (3D, Blockbuster, etc)
     */
    private MovieType movieType;

    /**
     * The run time of the movie.
     */
    private int duration;

    /**
     * The list of reviews of the movie.
     */
    private LinkedList<Review> reviews;

    /**
     * The list of available sessions of the movie.
     */
    private LinkedList<MovieSession> movieSessions;

    /**
     * The content rating of the movie.
     */
    private ContentRating contentRating;

    /**
     * Creates a new Movie from the given parameters
     * @param title The Movie's title
     * @param showingStatus The Movie's showing status
     * @param synopsis The Movie's synopsis
     * @param director The Movie's director
     * @param cast The Movie's cast
     * @param movieType The Movie's type
     * @param duration The Movie's duration
     * @param contentRating The Movie's content rating
     */
    public Movie(String title, ShowingStatus showingStatus, String synopsis, String director, String cast,
            MovieType movieType, int duration, ContentRating contentRating) {
        this.title = title;
        this.showingStatus = showingStatus;
        this.synopsis = synopsis;
        this.director = director;
        this.cast = cast;
        this.movieType = movieType;
        this.duration = duration;
        this.reviews = new LinkedList<Review>();
        this.movieSessions = new LinkedList<MovieSession>();
        this.contentRating = contentRating;
    }

    /**
     * Gets the title of this Movie.
     * @return this Movie's title.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Gets the showing status of this Movie.
     * @return this Movie's showing status.
     */
    public ShowingStatus getShowingStatus() {
        return this.showingStatus;
    }

    /**
     * Gets the synopsis of this Movie.
     * @return this Movie's synopsis.
     */
    public String getSynopsis() {
        return this.synopsis;
    }

    /**
     * Gets the director of this Movie.
     * @return this Movie's director.
     */
    public String getDirector() {
        return this.director;
    }

    /**
     * Gets the cast of this Movie.
     * @return this Movie's cast.
     */
    public String getCast() {
        return this.cast;
    }

    /**
     * Gets the run time of this Movie.
     * @return this Movie's duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Gets the reviews of this Movie.
     * @return this Movie's list of reviews
     */
    public LinkedList<Review> getReviews() {
        return reviews;
    }

    /**
     * Gets the available sessions to book for this Movie.
     * @return this Movie's list of sessions
     */
    public LinkedList<MovieSession> getMovieSessions() {
        return movieSessions;
    }

    /**
     * Gets the content rating of this Movie.
     * @return this Movie's content rating
     */
    public ContentRating getContentRating() {
        return contentRating;
    }

    /**
     * Gets the type of this Movie.
     * @return this Movie's type.
     */
    public MovieType getMovieType() {
        return this.movieType;
    }

    /**
     * Gets the number of tickets sold for this Movie.
     * @return the sum of tickets sold for this Movie.
     */
    public int getTicketsSold() {
        int count = 0;
        for (MovieSession s: movieSessions) {
            count += s.countSeats();
        }
        return count;
    }

    /**
     * Adds a review for this Movie
     * @param the new review of this Movie
     */
    public void addReview(Review review) {
        this.reviews.add(review);
    }

    /**
     * Adds a new session for this Movie
     * @param the new movie session of this movie
     */
    public void addMovieSession(MovieSession movieSession) {
        this.movieSessions.add(movieSession);
    }

    /**
     * Checks if the movie object title matches with string
     * @param movieTitle to be checked
     * @return
     */
    public boolean titleMatches(String movieTitle) {
        return this.title.equals(movieTitle);
    }

    /**
     * Get the average rating of the movie
     * @return average rating of the reviews
     */
    public float getAverageRating() {
        float totalRating = 0;
        int n = 0;
        LinkedList<Review> reviews = this.getReviews();
        for (int i = 0; i < reviews.size(); i++) {
            totalRating += reviews.get(i).getRatingScore();
            n++;
        }
        if(n==0) return 0;
        else return totalRating/n;
    }

    /**
     * Outputs full movie details to System.out.
     */
    public void viewMovieDetails(){
        System.out.println("Movie Title :       " + this.getTitle());
        System.out.println("Showing Status :    " + this.getShowingStatus());
        System.out.println("Synopsis :          " + this.getSynopsis());
        System.out.println("Director :          " + this.getDirector());
        System.out.println("Cast :              " + this.getCast());
        System.out.println("Movie Type :        " + this.getMovieType());
        System.out.println("Duration :          " + this.getDuration());
        System.out.println("Content Rating :    " + this.getContentRating());

        //              For some reason, the movie sessions LinkedList is empty, so it is not tested yet.
        // LinkedList<MovieSession> movieSessions = this.getMovieSessions();
        // System.out.print(String.valueOf(movieSessions.size()));
        
        // for (int i = 0; i < movieSessions.size(); i++) {
        //     MovieSession movieSession = movieSessions.get(i);
        //     System.out.println(movieSession.getCinema() + "     " + movieSession.getTimeSlot());
        //     System.out.println("Number of Seats Occupied : " + movieSession.countSeats());
        // }
        

        System.out.println("Average Rating :    " + this.getAverageRating());
        LinkedList<Review> reviews = this.getReviews();
        int counter = 1;
        for (int i = 0; i < reviews.size(); i++) {
            Review review = reviews.get(i);
            String reviewerName = review.getReviewer().getName();
            System.out.println("(" + String.valueOf(counter) + ")      " + reviewerName + "     Rating:" + String.valueOf(review.getRatingScore()));
            System.out.println("Review : " + review.getComment());   
            counter++;
        }
    }
}

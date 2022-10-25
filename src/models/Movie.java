package models;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
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
     * Reads data from file and parses into a linked list of Movies
     * @return a list of the available movies
     */
    public static LinkedList<Movie> getMovies() {

        LinkedList<Movie> list = new LinkedList<Movie>();
        try {
            File f = new File("./data/movies.csv");
            Scanner sc = new Scanner(f);
            String in, params[];

            while (sc.hasNextLine()) {
                in = sc.nextLine();
                // regex to split string by comma, but not commas within quotation marks
                params = in.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                list.add(new Movie(params[0], ShowingStatus.getShowingStatus(params[1]), params[2], params[3], params[4], MovieType.getMovieType(params[5]), Integer.parseInt(params[6]), ContentRating.getContentRating(params[7])));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Movies File not found");
            e.printStackTrace();
        }

        return list;

    }
}

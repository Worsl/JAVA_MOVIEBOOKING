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
    private String showingStatus;

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
    private String movieType;

    /**
     * Creates a new Movie from the given parameters
     * @param title The Movie's title
     * @param showingStatus The Movie's showing status
     * @param synopsis The Movie's synopsis
     * @param director The Movie's director
     * @param cast The Movie's cast
     * @param movieType The Movie's type
     */
    public Movie(String title, String showingStatus, String synopsis, String director, String cast, String movieType) {
        this.title = title;
        this.showingStatus = showingStatus;
        this.synopsis = synopsis;
        this.director = director;
        this.cast = cast;
        this.movieType = movieType;
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
    public String getShowingStatus() {
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
     * Gets the type of this Movie.
     * @return this Movie's type.
     */
    public String getMovieType() {
        return this.movieType;
    }

    public static LinkedList<Movie> getMovies() {

        LinkedList<Movie> list = new LinkedList<Movie>();
        try {
            File f = new File("./data/movies.csv");

            Scanner sc = new Scanner(f);

            String in, params[];
            while (sc.hasNextLine()) {
                in = sc.nextLine();
                // regex to split string by comma, but not those within quotation marks
                params = in.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                list.add(new Movie(params[0], params[1], params[2], params[3], params[4], params[5]));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Movies File not found");
            e.printStackTrace();
        }

        return list;

    }
}

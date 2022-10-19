package models;

/**
 * Represents a review of a movie
 */
public class Review {

    /**
     * The reviewer's rating of the movie.
     */
    private int rating;

    /**
     * The reviewer's comment on the movie.
     */
    private String comment;

    /**
     * The reviewer of the movie.
     */
    private String reviewer;

    /**
     * The movie of the review.
     */
    private Movie movie;

    /**
     * Crates a new Review from the given parameters
     * @param rating The Review's rating
     * @param comment The Review's comment
     * @param reviewer The Review's reviewer
     * @param movie The Review's movie
     */
    public Review (int rating, String comment, String reviewer, Movie movie) {
        this.rating = rating;
        this.comment = comment;
        this.reviewer = reviewer;
        this.movie = movie;
    }

    /**
     * Gets the rating of the review
     * @return this Review's rating
     */
    public int getRating() {
        return this.rating;
    }

    /**
     * Gets the comment of the review
     * @return this Review's comment
     */
    public String getComment() {
        return this.comment;
    }

    /**
     * Gets the reviewer of the review
     * @return this Review's reviewer
     */
    public String getReviewer() {
        return this.reviewer;
    }

    /**
     * Gets the movie of the review
     * @return this Review's movie
     */
    public Movie getMovie() {
        return this.movie;
    }
}

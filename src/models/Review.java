package models;

/**
 * Represents a review of a movie
 */
public class Review {

    /**
     * The reviewer of the movie.
     */
    private User reviewer;

    /**
     * The reviewer's rating of the movie.
     */
    private int ratingScore;

    /**
     * The reviewer's comment on the movie.
     */
    private String comment;

    /**
     * Crates a new Review from the given parameters
     * @param reviewer The Review's reviewer
     * @param rating The Review's rating
     * @param comment The Review's comment
     */
    public Review (User reviewer, int ratingScore, String comment) {
        this.reviewer = reviewer;
        this.ratingScore = ratingScore;
        this.comment = comment;
    }

    /**
     * Gets the reviewer of this review
     * @return this Review's reviewer
     */
    public User getReviewer() {
        return this.reviewer;
    }

    /**
     * Gets the rating of this review
     * @return this Review's rating
     */
    public int getRatingScore() {
        return this.ratingScore;
    }

    /**
     * Updates the rating score of this review
     * @param ratingScore the new rating sacore of this review
     */
    public void setRatingScore(int ratingScore) {
        this.ratingScore = ratingScore;
    }

    /**
     * Gets the comment of this review
     * @return this Review's comment
     */
    public String getComment() {
        return this.comment;
    }

    /**
     * Updates the comment of this review.
     * @param comment The new comment of this review
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

}

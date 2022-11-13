package models;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ArrayList;
import java.io.*;
/**
 * DAO.java
 *
 * Written on Saturday, 29 October 2022.
 */

public class DAO {

    // File Paths
    private static String movieFilePath = "./data/movies.csv";
    private static String sessionFilePath = "./data/moviesessions.csv";

    /**
     *
     * Reads data from file and parses into a map of (Cineplex Name: Cineplex)
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
     * Reads data from file and parses into a map of (Cinema Code: Cinemas)
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
     * Reads data from file and parses into a map of (Movie Title: Movies)
     * @return a map of the available movies
     */
    public static HashMap<String, Movie> getMovies() {

        HashMap<String, Movie> map = new HashMap<String, Movie>();
        try (BufferedReader br = new BufferedReader(new FileReader(movieFilePath))) {

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
     * Reads data from file and parses into a map of (Movie Title: Session)
     * @param cinemas The list of cinemas available in the system
     * @param movies The list of movies available in the system
     * @return a map of the available sessions
     */
    public static HashMap<String, ArrayList<MovieSession>> getSessions(HashMap<String, Cinema> cinemas, HashMap<String, Movie> movies) {

        HashMap<String, ArrayList<MovieSession>> map = new HashMap<String, ArrayList<MovieSession>>();
        try {
            File f = new File(sessionFilePath);
            Scanner sc = new Scanner(f);
            String in, params[];

            while (sc.hasNextLine()) {
                in = sc.nextLine();
                // regex to split string by comma, but not commas within quotation marks
                params = in.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                Cinema cinema = cinemas.get(params[0]);
                Movie movie = movies.get(params[1]);
                String id = params[3];
                if (cinema != null && movie != null)
                    if (map.get(params[1]) != null)
                        map.get(params[1]).add(new MovieSession(params[2], cinema, movie, id));
                    else {
                        ArrayList <MovieSession> ll = new ArrayList<MovieSession>();
                        ll.add(new MovieSession(params[2], cinema, movie, id));
                        map.put(params[1], ll);
                    }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Movies File not found");
            e.printStackTrace();
        }

        return map;
    }

    /**
     * Reads data from file and parses into a map of (Transaction Id: bookings)
     * @param sessions The map of current ongoing sessions
     * @param users The map of current registered users
     * @return a map of the bookings made
     */
    public static HashMap<String, Booking> getBookings(HashMap<String, MovieSession> sessions, HashMap<String, User> users) {

        HashMap<String, Booking> map = new HashMap<String, Booking>();
        try {
            File f = new File("./data/bookings.csv");
            Scanner sc = new Scanner(f);
            String in, params[];
            while (sc.hasNextLine()) {
                in = sc.nextLine();
                // regex to split by comma, but not those within quotation marks
                params = in.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                map.put(params[0], new Booking(params[0], sessions.get(params[1]), users.get(params[2])));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Cineplexes File not found");
            e.printStackTrace();
        }

        return map;

    }

    //define file paths

    static String temp1 = "./data/temp1.csv";
    static String temp2 = "./data/temp2.csv";

    /**
     * Reads data from file and parses into a map of (Session Id: Session)
     * @param sessions The list of available sessions in the system;
     * @return a map of the sessions available
     */
    public static HashMap<String, MovieSession> getSessionsById(HashMap<String, ArrayList<MovieSession>> sessions) {

        HashMap<String, MovieSession> map = new HashMap<String, MovieSession>();

        for (ArrayList<MovieSession> al : sessions.values()) {
            for (MovieSession s : al) {
                map.put(s.getSessionId(), s);
            }
        }
        return map;

    }

    /**
     * Adds a new movie to the system
     * @param movie The new movie to be added
     */
    public static void addMovie(Movie movie) {

        try {

            //add movie to file
            FileWriter fw = new FileWriter(movieFilePath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            pw.println(movie.getTitle() + "," + movie.getShowingStatus().name() + ",\"" + movie.getSynopsis() + "\"," + movie.getDirector() + ",\"" + movie.getCast() + "\"," + movie.getMovieType().name() + "," + movie.getDuration() + "," + movie.getContentRating().name());
            pw.flush();
            pw.close();

            System.out.println("Movie successfully added.");

        } catch (FileNotFoundException e) {
            System.out.println("Movies File not found");
            e.printStackTrace();
        } catch (IOException e) {
        	System.out.println("IOException");
        	e.printStackTrace();
    	}
    }

    /**
     * Updates a movie in file
     * @param title The title of the movie to be updated
     * @param category The field of the movie to be updated
     * @param edit The updated value
     */
    public static void updateMovie(String title, int category, String edit) {

    	File oldFile = new File(movieFilePath);
    	File newFile = new File(temp1);

        try {
        	FileWriter fw = new FileWriter(temp1, true);
        	BufferedWriter bw = new BufferedWriter(fw);
        	PrintWriter pw = new PrintWriter(bw);
        	Scanner sc = new Scanner(new File(movieFilePath));
            String in, params[];
            boolean found = false;

            while (sc.hasNextLine()) {
                in = sc.nextLine();
                // regex to split string by comma, but not commas within quotation marks
                params = in.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                if (params[0].equals(title) && !params[1].equals("End Showing")) {
                	params[category] = edit;
                	pw.println(params[0]+","+params[1]+","+params[2]+","+params[3]+","+params[4]+","+params[5]+","+params[6]+","+params[7]);
                	found = true;
                } else {
                	pw.println(in);
                }
            }

            sc.close();
            pw.flush();
            pw.close();
            oldFile.delete();
            File dump = new File (movieFilePath);
            newFile.renameTo(dump);

            if (found) {
            	System.out.println("Movie successfully updated");
            } else {
            	System.out.println("Movie title not found");
            }

        } catch (FileNotFoundException e) {
            System.out.println("Movies File not found");
            e.printStackTrace();
        } catch (IOException e) {
        	System.out.println("IOException");
        	e.printStackTrace();
    	}

        return;
    }

    /**
     * Deletes a movie from the system
     * @param movies The updated list of movies
     */
    public static void deleteMovie(HashMap<String, Movie> movies) {

        File oldFile = new File(movieFilePath);
        File newFile = new File(temp1);

        try {
            FileWriter fw = new FileWriter(temp1, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            String in, params[];

            for (Movie m: movies.values()) {
                pw.println(m.getTitle() + "," + m.getShowingStatus().name() + "," + m.getSynopsis() + ","
                        + m.getDirector() + "," + m.getCast() + "," + m.getMovieType().name() + "," + m.getDuration()
                        + "," + m.getContentRating().name());
            }

            pw.flush();
            pw.close();
            oldFile.delete();
            File dump = new File (movieFilePath);
            newFile.renameTo(dump);

        } catch (FileNotFoundException e) {
            System.out.println("Movies File not found");
            e.printStackTrace();
        } catch (IOException e) {
        	System.out.println("IOException");
        	e.printStackTrace();
    	}

    }

    /**
     * Creates a new session to be added to the system
     * @param cinema The cinema the session would be held in
     * @param title The movie's title for this session
     * @param dateTime The planned scheduled time when this session will occur
     * @param sessionID The unique identifier assigned to this session
     */
    public static void addSession(String cinema, String title, String dateTime, String sessionID) {

        try {
        	//check sessionID not already in file
        	File f = new File(sessionFilePath);
            Scanner sc = new Scanner(f);
        	String in, params[];

        	while (sc.hasNextLine()) {
                in = sc.nextLine();
                // regex to split string by comma, but not commas within quotation marks
                params = in.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                if (params[3].equals(sessionID)) {
                	System.out.println("Movie session is already in system.");
                	return;
                }
        	}

        	sc.close();

        	//add session to file
        	FileWriter fw = new FileWriter(sessionFilePath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            pw.println(cinema + "," + title + "," + dateTime + "," + sessionID);
            pw.flush();
            pw.close();

            System.out.println("Movie session successfully added.");

        } catch (FileNotFoundException e) {
        	System.out.println("Movie Sessions File not found");
            e.printStackTrace();
        } catch (IOException e) {
        	System.out.println("IOException");
        	e.printStackTrace();
    	}

        return;
    }

    /**
     * Updates an existing session in the system
     * @param newCinema The new Cinema to be updated
     * @param newTitle The title of the new Movie to be updated
     * @param newDateTime The new time slot of the session
     * @param sessionID The unique identifier of this session
     */
    public static void updateSession(HashMap<String, MovieSession> sessions) {

        try {
            FileWriter fw = new FileWriter(sessionFilePath);
            for (MovieSession session: sessions.values()) {
                fw.write(session.getCinema().getCinemaCode() + "," + session.getMovie().getTitle() + "," + session.getTimeSlot().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "," + session.getSessionId() + "\n");
            }
            fw.close();

        } catch (IOException e) {
        	System.out.println("IOException");
        	e.printStackTrace();
    	}
    }

    /**
     * Removes an existing movie session by setting its showing status to END_OF_SHOWING
     * @param sessionID The unique identifer of the session to be removed
     */
    public static void deleteSession(LinkedList<MovieSession> sessions) {
        try {
            FileWriter fw =  new FileWriter(sessionFilePath);
            for (MovieSession session: sessions) {
                fw.write(session.getCinema().getCinemaCode() + "," + session.getMovie().getTitle() + "," + session.getTimeSlot().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "," + session.getSessionId() + "\n");
            }
            fw.close();
        }
        catch (IOException e) {
        	System.out.println("IOException");
        	e.printStackTrace();
    	}
    }

    /**
     * Reads data from file and binds the reviews to the movie
     * @param movies a map of movies
     * @param users a map of users
     */
    public static void setReviews(HashMap<String, Movie> movies, HashMap<String, User> users) {

        try {
            File f = new File("./data/reviews.csv");
            Scanner sc = new Scanner(f);
            String in, params[];

            while (sc.hasNextLine()) {
                in = sc.nextLine();
                // regex to split string by comma, but not commas within quotation marks
                params = in.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                // params 0 = rating, params 1 = movietitle, params 2 = reviewer, params 3 = comment
                int ratingScore = Integer.parseInt(params[0]);
                Movie movie = movies.get(params[1]);
                User user = users.get(params[2]);
                if (movie != null && user != null)
                    movie.addReview(new Review(user, ratingScore, params[5]));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Movies File not found");
            e.printStackTrace();
        }

   }

    /**
     * Reads data from file and binds the sessions to the movie
     * @param movies a map of movies
     * @param sessions a map of sessions
     */
    public static void setSessions(HashMap<String, Movie> movies, HashMap<String, ArrayList<MovieSession>> sessions) {

        for (String title: movies.keySet()) {
            if (sessions.containsKey(title)) {
                for (MovieSession s: sessions.get(title)) {
                    movies.get(title).addMovieSession(s);
                }

            }
        }

   }

    /**
    * Reads data from file and parses into a map of (User Name: User)
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
     * @param reviewer The Reviewer's name
     * @param mobileNumber The Reviewer's mobile number
     * @param comment The Reviewer's comments
    */
   public static void writeReviewsToCSV(int ratingScore, String movie, String reviewer, String email, String mobileNumber, String comment) {
        try {
            if(comment == "") comment = " "; // replace blank comments with a single space.
            FileWriter myWriter = new FileWriter("./data/reviews.csv", true);
            myWriter.write(String.valueOf(ratingScore) + "," + movie + "," + reviewer + "," + email + "," + mobileNumber + ",\"" + comment + "\"\n");
            myWriter.close();

        } catch (IOException e) {
            System.out.println("An error occurred when writing review to CSV, please see DAO.java");
            e.printStackTrace();
        }
    }

    /**
     * Reads data from file and parses into a linked list of tickets
     * @param bookings The map of past bookings made by users
     */
    public static void setTickets(HashMap<String, Booking> bookings) {

        LinkedList<Ticket> tickets = new LinkedList<Ticket>();
        try (BufferedReader br = new BufferedReader(new FileReader("./data/tickets.csv"))) {

            String in = br.readLine(), params[];

            while (in != null) {
                // regex to split string by comma, but not commas within quotation marks
                params = in.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                Booking booking = bookings.get(params[0]);
                if (booking != null) {
                    Ticket ticket = new Ticket
                        (TicketType.valueOf(params[2]),
                         bookings.get(params[0]).getMovieSession().getSeat(params[1]),
                         bookings.get(params[0])
                        );
                    tickets.add(ticket);
                    bookings.get(params[0]).addTicket(ticket);
                }

                in = br.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Ticket File not found");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The set containing the list of holidays.
     */
    private static HashSet<LocalDate> holidays = null;

    /**
     * Reads from holidays csv file to retrieve the list of holidays. If this file has already been read, the value would be stored in the holidays variable and the file would not be read again.
     * @return a hash set of holiday dates
     */
    public static HashSet<LocalDate> getHolidays() {
        if (holidays != null) return holidays;
        holidays = new HashSet<LocalDate>();
        try (BufferedReader br = new BufferedReader(new FileReader("./data/holidays.csv"))) {

            // Scanner sc = new Scanner(f);
            String in = br.readLine(), params[];

            while (in != null) {
                holidays.add(LocalDate.parse(in));
                in = br.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Holidays File not found");
            e.printStackTrace();
        }  catch (IOException e) {
            e.printStackTrace();
        }

        return holidays;

    }

    /**
     * Adds a new holiday Date and saves to file
     * @param date The new holiday date to be added
     */
    public static void addHolidayToCSV(String date) {
        try {
            FileWriter myWriter = new FileWriter("./data/holidays.csv", true);
            myWriter.write(date + "\n");
            myWriter.close();

        } catch (IOException e) {
            System.out.println("An error occurred when adding holiday to CSV, please see DAO.java");
            e.printStackTrace();
        }
    }


}

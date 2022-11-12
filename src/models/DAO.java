package models;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
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
    public static HashMap<String, ArrayList<MovieSession>> getSessions(HashMap<String, Cinema> cinemas, HashMap<String, Movie> movies) {

        HashMap<String, ArrayList<MovieSession>> map = new HashMap<String, ArrayList<MovieSession>>();
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
     * Reads data from file and parses into a list of bookings
     * @param sessions The map of current ongoing sessions
     * @return a list of the bookings made
     */
    public static LinkedList<Booking> getBookings(HashMap<String, MovieSession> sessions, HashMap<String, User> users) {

        LinkedList<Booking> list = new LinkedList<Booking>();

        try {
            File f = new File("./data/bookings.csv");
            Scanner sc = new Scanner(f);
            String in, params[];
            while (sc.hasNextLine()) {
                in = sc.nextLine();
                // regex to split by comma, but not those within quotation marks
                params = in.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                list.add(new Booking(params[0], sessions.get(params[1]), users.get(params[2])));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Cineplexes File not found");
            e.printStackTrace();
        }

        return list;

    }


    //define file paths
    static String movies_filepath = "./data/movies.csv";
    static String moviesessions_filepath = "./data/moviesessions.csv";
    static String temp_filepath = "./data/temp.csv";

    
    /**
     * add movie to file 
     * @return void
     */
    public static void addMovie(String title, String showingStatus, String synopsis, String director, String cast, String type, int duration, String contentRating) {
    	
        try {
        	//check movie not already in file
        	File f = new File(movies_filepath);
        	Scanner sc = new Scanner(f);
        	String in, params[];
        	
        	while (sc.hasNextLine()) {
                in = sc.nextLine();
                // regex to split string by comma, but not commas within quotation marks
                params = in.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
 
                if (params[0].equals(title)) {
                	System.out.println("Movie is already in system.");
                	return;
                }
        	}
        	sc.close();
        	
        	//add movie to file
        	FileWriter fw = new FileWriter(movies_filepath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            
            pw.println(title+","+showingStatus+","+synopsis+","+director+","+cast+","+type+","+duration+","+contentRating);
            pw.flush();
            pw.close();
            
            System.out.println("Movies successfully added.");
  
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
     * update a movie in file
     * @return void
     */
    public static void updateMovie(String title, int category, String edit) {
    	
    	File oldFile = new File(movies_filepath);
    	File newFile = new File(temp_filepath);
    	
        try {
        	FileWriter fw = new FileWriter(temp_filepath, true);
        	BufferedWriter bw = new BufferedWriter(fw);
        	PrintWriter pw = new PrintWriter(bw);
        	Scanner sc = new Scanner(new File(movies_filepath));
            String in, params[];
            boolean found = false;

            while (sc.hasNextLine()) {
                in = sc.nextLine();
                // regex to split string by comma, but not commas within quotation marks
                params = in.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
 
                if (params[0].equals(title)) {
                	params[category] = edit;
                	//System.out.println(params[category]);
                	//System.out.println(Arrays.toString(params));
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
            File dump = new File (movies_filepath);
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

   // Reading data for users to choose
   public static HashMap<String, MovieSession> getSessionsById (HashMap<String, ArrayList<MovieSession>> sessions) {

    HashMap<String, MovieSession> map = new HashMap<String, MovieSession>();

    for (ArrayList<MovieSession> al: sessions.values()) {
        for (MovieSession s: al) {
            map.put(s.getSessionId(), s);
        }
    }
    return map;

   }

    /**
     * Delete a movie from file
     * @return void
     */
    public static void deleteMovie(String title) {
    	
    	File oldFile = new File(movies_filepath);
        File newFile = new File(temp_filepath);
        
        try {
            FileWriter fw = new FileWriter(temp_filepath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
        	Scanner sc = new Scanner(new File(movies_filepath));
            String in, params[];
            boolean found = false;
            
            while (sc.hasNextLine()) {
                in = sc.nextLine();
                // regex to split string by comma, but not commas within quotation marks
                params = in.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
 
                if (!params[0].equals(title)) {
                	pw.println(in);
                } else {
                	found = true;
                }
            }
            
            sc.close();
            pw.flush();
            pw.close();
            oldFile.delete();
            File dump = new File (movies_filepath);
            newFile.renameTo(dump);
            
            if (found) {
            	System.out.println("Movie successfully deleted");
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
     * Shows movies in file
     * @return void
     */
    public static void showMovie() {
        
        try {
            System.out.println("Title");
            System.out.println("Showing Status");
            System.out.println("Synopsis");
            System.out.println("Director");
            System.out.println("Cast");
            System.out.println("Movie Type");
            System.out.println("Duration");
            System.out.println("Content Rating");
            System.out.println(" ");
            
            File f = new File(movies_filepath);
            Scanner sc = new Scanner(f);
            String in, params[];
            
            while (sc.hasNextLine()) {
                in = sc.nextLine();
                // regex to split string by comma, but not commas within quotation marks
                params = in.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                System.out.println(params[0]);
                System.out.println(params[1]);
                System.out.println(params[2]);
                System.out.println(params[3]);
                System.out.println(params[4]);
                System.out.println(params[5]);
                System.out.println(params[6]);
                System.out.println(params[7]);
                System.out.println(" ");
            }
            sc.close();
            
        } catch (FileNotFoundException e) {
            System.out.println("Movies File not found");
            e.printStackTrace();
        }

        return;
    }

    
    
    /**
     * add timeslot to a movie 
     * @return void
     */
    public static void addTimeSlot(String cinema, String title, String timeSlot) {
    	
        try {
        	//check timeslot not already in file
        	File f = new File(moviesessions_filepath);
            Scanner sc = new Scanner(f);
        	String in, params[];
        	
        	while (sc.hasNextLine()) {
                in = sc.nextLine();
                // regex to split string by comma, but not commas within quotation marks
                params = in.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                if (params[0].equals(cinema) && params[1].equals(title) && params[2].equals(timeSlot)) {
                	System.out.println("Time slot is already in system.");
                	return;
                }
        	}
        	sc.close();
        	
        	//add timeslot to file
        	FileWriter fw = new FileWriter(moviesessions_filepath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            
            pw.println(cinema+","+title+","+timeSlot);
            pw.flush();
            pw.close();
            
            System.out.println("Time slot successfully added.");
  
        } catch (FileNotFoundException e) {
        	System.out.println("Moviesessions File not found");
            e.printStackTrace();
        } catch (IOException e) {
        	System.out.println("IOException");
        	e.printStackTrace();
    	}
        
        return;
    }
    
    /**
     * update timeslot of a movie 
     * @return void
     */
    public static void updateTimeSlot(String cinema, String title, String timeSlot, String newTimeSlot) {
    	
    	File oldFile = new File(moviesessions_filepath);
        File newFile = new File(temp_filepath);
        
        try {
            FileWriter fw = new FileWriter(temp_filepath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
        	Scanner sc = new Scanner(new File(moviesessions_filepath));
            String in, params[];
            boolean found = false;

            while (sc.hasNextLine()) {
                in = sc.nextLine();
                // regex to split string by comma, but not commas within quotation marks
                params = in.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
 
                if (params[0].equals(cinema) && params[1].equals(title) && params[2].equals(timeSlot)) {
                	params[2] = newTimeSlot;
                	pw.println(params[0]+","+params[1]+","+params[2]);
                	found = true;
                } else {
                	pw.println(in);
                }
            }
            
            sc.close();
            pw.flush();
            pw.close();
            oldFile.delete();
            File dump = new File(moviesessions_filepath);
            newFile.renameTo(dump);
            
            if (found) {
            	System.out.println("Time slot successfully updated");
            } else {
            	System.out.println("Time slot not found");
            }
            
        } catch (FileNotFoundException e) {
            System.out.println("Moviesessions File not found");
            e.printStackTrace();
        } catch (IOException e) {
        	System.out.println("IOException");
        	e.printStackTrace();
    	}
        
        return;
    } 

    /**
     * Delete timeslot of a movie
     * @return void
     */
    public static void deleteTimeSlot(String cinema, String title, String timeSlot) {
    	
    	File oldFile = new File(moviesessions_filepath);
        File newFile = new File(temp_filepath);
        
        try {
            FileWriter fw = new FileWriter(temp_filepath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
        	Scanner sc = new Scanner(new File(moviesessions_filepath));
            String in, params[];
            Boolean found = false;
            
            while (sc.hasNextLine()) {
                in = sc.nextLine();
                // regex to split string by comma, but not commas within quotation marks
                params = in.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
 
                if (!params[0].equals(cinema) && !params[1].equals(title) && !params[2].equals(timeSlot)) {
                	pw.println(in);
                } else {
                	found = true;
                }
            }
            
            sc.close();
            pw.flush();
            pw.close();
            oldFile.delete();
            File dump = new File (moviesessions_filepath);
            newFile.renameTo(dump);
            
            if (found) {
            	System.out.println("Time slot successfully deleted");
            } else {
            	System.out.println("Time slot not found");
            }
            
        } catch (FileNotFoundException e) {
            System.out.println("Moviesessions File not found");
            e.printStackTrace();
        } catch (IOException e) {
        	System.out.println("IOException");
        	e.printStackTrace();
    	}
        
        return;
    }
    
    /**
     * Show timeslot of movies
     * @return void
     */
    public static void showTimeSlot() {
        
        try {
            System.out.println("Cinema   " + "Title   " + "Time Slot");
            System.out.println(" ");
        	
            File f = new File(moviesessions_filepath);
            Scanner sc = new Scanner(f);
            String in, params[];
            
            while (sc.hasNextLine()) {
                in = sc.nextLine();
                // regex to split string by comma, but not commas within quotation marks
                params = in.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                
                System.out.println(params[0]+"   "+params[1]+"   "+params[2]);
                System.out.println(" ");
            }
            sc.close();
            
        } catch (FileNotFoundException e) {
            System.out.println("Moviesessions File not found");
            e.printStackTrace();
        }

        return;
    }

    /**
     * Reads data from file and binds the reviews to the movie
     * @param movies a map of movies
     * @param users a map of users
     * @return a map of the reviews
     */
    public static HashMap<String, LinkedList<Review>> setReviews(HashMap<String, Movie> movies, HashMap<String, User> users) {

        HashMap<String, LinkedList<Review>> map = new HashMap<String, LinkedList<Review>>();

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

    /**
     * * Reads data from file and parses into a linked list of tickets
     * @return a linked list of the available Cineplexes
     */
    public static LinkedList<Ticket> getTickets(HashMap<String, Booking> bookings) {

        LinkedList<Ticket> tickets = new LinkedList<Ticket>();
        try (BufferedReader br = new BufferedReader(new FileReader("./data/movies.csv"))) {

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

        return tickets;
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

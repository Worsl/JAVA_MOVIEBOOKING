package models;

import java.util.LinkedList;

import javax.naming.ldap.LdapName;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Timestamp;
import java.util.*;
import com.opencsv.*;

/**
 * Represents a booking for a movie
 *
 */
public class Booking {

    /**
     * The transaction ID of the booking
     */
    private String TID;

    /**
     * The tickets booked for this booking
     */
    private LinkedList<Ticket> tickets;

    /**
     * The movie session of the booking
     */
    private MovieSession movieSession;

    /**
     * The person who made this booking
     */
    private User owner;

    /**
     * Creates a new Booking from the given parameters
     * @param TID The Booking's transaction ID
     * @param tickets The Booking's list of tickets
     * @param movieSession The Booking's session
     * @param user The Booking's owner
     */
    /*public Booking (String TID, LinkedList<Ticket> tickets, MovieSession movieSession, User owner) {
        this.TID = TID;
        this.tickets = tickets;
        this.movieSession = movieSession;
        this.owner = owner;
    }*/

    /**
     * Creates a new Booking from the given parameters
     *
     * @param tickets The Booking's transaction ID
     * @param movieSession  The Booking's session
     * @param user The Booking's owner
     */
    public Booking(String TID, MovieSession movieSession, User owner) {
        this.TID = TID;
        this.tickets = new LinkedList<Ticket>();
        this.movieSession = movieSession;
        this.owner = owner;
    }

    /**
     * Calculates the total amount paid for this booking
     * @return the sum of the price of tickets
     */
    public double getTotalPrice() {
        double sum = 0;
        for (Ticket t: tickets) {
            sum += t.getTicketPrice();
        }

        return sum;
    }

    /**
     * Gets the transaction id of this Booking.
     * @return the TID of this booking
     */
    public String getTID() {
        return this.TID;
    }

    /**
     * Gets the owner of this booking
     * @return the owner of this booking
     */
    public User getOwner() {
        return this.owner;
    }

    /**
     * Gets the movie session of this booking
     * @return the movie session of this booking
     */
    public MovieSession getMovieSession() {
        return this.movieSession;
    }

    /**
     * Adds a new ticket to this booking.
     * @param the new ticket to be added to this booking
     */
    public void addTicket(Ticket t) {
        tickets.add(t);
    }

    public static void createBooking(HashMap<String, Cinema> cinemas, HashMap<String, Movie> movies, HashMap<String, LinkedList<MovieSession>> sessions, HashMap<String, LinkedList<MovieSession>> sessions2) throws FileNotFoundException{
        Scanner sc1 = new Scanner(System.in);
        
        User rhys = new User("Rhys", "wongrhys@gmail.com", "90052336");
        List<Booking> listbooking = new LinkedList<Booking>();

        Scanner sc2 = new Scanner(System.in);
        System.out.println("Please pick the movie you wish to watch: " + movies.keySet());
        String choiceofmovie = sc2.nextLine();
        System.out.println("Please select the session you would like: " + sessions.get(choiceofmovie));
        String choiceofsession = sc2.nextLine();

        System.out.println("Green = Available \nRed = Booked \nPink = Couple Seats");
        System.out.println("How many tickets would you like to purchase? ");
        int numtickets = sc1.nextInt();
        for(int i=0;i<numtickets;i++) {
            sessions2.get(choiceofsession).getLast().listofavailableSeats();
            System.out.println("Please select your seat number. ");
            String seatnumber = sc2.nextLine();
            sessions2.get(choiceofsession).getLast().setSeat(seatnumber);
        }



        java.sql.Timestamp timestamp = new java.sql.Timestamp(System.currentTimeMillis());
        String transactionid = sessions2.get(choiceofsession).getLast().getCinema().getCinemaCode() + " " + timestamp;
        System.out.println("TID is -> " + transactionid);
        listbooking.add(new Booking(transactionid, sessions2.get(choiceofsession).getLast(),rhys));
        try {
            String[] bookingstring = {transactionid,sessions2.get(choiceofsession).getLast().toString(),"Rhys"};
            FileWriter fw = new FileWriter("./data/bookings.csv",true);
            CSVWriter writer = new CSVWriter(fw);

            writer.writeNext(bookingstring);
            writer.close();

            

        }
        catch (IOException e){
            e.printStackTrace();
        }
        
        



        
        
        
                

        



    }

}








/*switch(choiceofmovie) {
                    case "Black Adam":
                        System.out.println("Please select the cinema and timeslot by choosing the number: " + sessions.get("Black Adam"));
                        String choiceofsession = sc1.nextLine();

                        //Test movieSession, replace with actual data.
                        

                        System.out.println("How many ticket would you like to buy? Please enter a number.");
                        Scanner sc = new Scanner(System.in);
                        int numtickets = sc.nextInt();

                        for(int i=0;i<numtickets;i++) {
                            System.out.println("Please enter the Ticket Type you are purchasing: ");
                            //TicketType tt = new TicketType();
                            System.out.println("Which seat number would you like?: ");
                            System.out.println("Green - Available \nRed - Booked");
                            sessions2.get(choiceofsession).getLast().listofavailableSeats();
                            String seatnumber = sc1.nextLine();
                            sessions2.get(choiceofsession).getLast().setSeat(seatnumber);
                            //Ticket(Tickettype??, newseat, this.booking)
                        }
                        

                        
                        break;
                }*/
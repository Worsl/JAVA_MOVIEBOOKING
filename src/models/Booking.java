package models;

import java.util.LinkedList;

import javax.naming.ldap.LdapName;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.security.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter; 
import java.util.*;
import com.opencsv.*;
import org.fusesource.jansi.AnsiConsole;

import models.TicketType;

import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;


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

    /**
     * Gets of tickets of this booking
     * @return the ticket list of this booking
     */
    public LinkedList<Ticket> getTickets() {
        return this.tickets;
    }


    // Returns booking of the current user.

    public static void viewBookingrecord(User user) {
        try {
            
            String path = "./data/bookings.csv";
            String line = "";
            BufferedReader br = new BufferedReader(new FileReader(path));

            while((line = br.readLine()) != null) {
                String[] values = line.split(",");
                //System.out.println(values[2] + user.getName());
                if (values[2].equalsIgnoreCase(user.getName())) {
                    System.out.println(line);
                }
                
            }
        }
        catch (Exception e) { 
            e.printStackTrace();
        }
    }

    public static void createBooking(HashMap<String, Cinema> cinemas, HashMap<String, Movie> movies, HashMap<String, LinkedList<MovieSession>> sessions, HashMap<String, LinkedList<MovieSession>> sessions2, User user) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Please pick the movie you wish to watch: " + movies.keySet());
        String choiceofmovie = sc.next();

        boolean ok = false;
        MovieSession selectedSession = null;
        while (!ok) {
            System.out.println("Please select the session you would like: " + sessions.get(choiceofmovie));
            String choiceofsession = sc.next();
            if (sessions2.containsKey(choiceofsession)) {
                selectedSession = sessions2.get(choiceofsession).getLast();
                ok = true;
                break;
            }
            System.out.println("You have selected an invalid session");
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        String formatdate = now.format(format);

        String transactionid = selectedSession.getCinema().getCinemaCode() + formatdate;
        Booking newBooking = new Booking(transactionid, selectedSession, user);

        System.out.println("How many tickets would you like to purchase?");
        int noOfTickets = sc.nextInt();

        AnsiConsole.systemInstall();
        System.out.println("Green: " + ansi().fg(GREEN).a("Available").reset());
        System.out.println("Red: " + ansi().fg(RED).a("Booked").reset());
        System.out.println("Pink: " + ansi().fgBright(MAGENTA).a("Couple Seats").reset());
        System.out.println("Blue: " + ansi().fg(BLUE).a("Disabled").reset());
        System.out.println("Yellow: " + ansi().fg(YELLOW).a("Premium"));
        AnsiConsole.systemUninstall();

        while (noOfTickets > 0) {
            selectedSession.listofavailableSeats();
            System.out.println("Please select your seat number. ");
            String seatnumber = sc.next();
            if (selectedSession.setSeat(seatnumber)) {
                TicketType tt = null;
                while (tt == null) {
                    System.out.println("Select the number which age group this seat is for:");
                    System.out.println("1. Child (< 7)");
                    System.out.println("2. Student (7 - 18)");
                    System.out.println("3. Adult (18 - 65)");
                    System.out.println("4. Senior Citizen (> 65)");
                    int ageGroup = sc.nextInt();

                    switch(ageGroup) {
                        case 1:
                            tt = TicketType.CHILD;
                            break;
                        case 2:
                            tt = TicketType.STUDENT;
                            break;
                        case 3:
                            tt = TicketType.ADULT;
                            break;
                        case 4:
                            tt = TicketType.SENIOR_CITIZEN;
                            break;
                        default:
                            break;
                    }
                    if (tt == null) System.out.println("Invalid Choice. Please choose again.");
                }
                Ticket ticket = new Ticket(tt, selectedSession.getSeat(seatnumber), newBooking);
                System.out.format("This ticket price for seat " + ticket.getSeat().getSeatId() + " is S$%.2f. Confirm Purchase? (y/n)", ticket.getTicketPrice());
                if (sc.next().equalsIgnoreCase("y")) {
                    noOfTickets--;
                    newBooking.addTicket(ticket);
                }
                else selectedSession.getSeat(seatnumber).unoccupySeat();;
            }
        }

        System.out.format("The total price for your booking is S$%.2f.Confirm Purchase? (y/n)\n", + newBooking.getTotalPrice());
        if (!sc.next().equalsIgnoreCase("y")) return;


        System.out.println("Your transaction ID is -> " + transactionid + ". Thank you for your purchase!");


        try {
            //String[] bookingstring = {transactionid,sessions2.get(choiceofsession).getLast().toString(),user.getName()};
            FileWriter fw = new FileWriter("./data/bookings.csv",true);
            //CSVWriter writer = new CSVWriter(fw);

            fw.write(newBooking.getTID() + "," + newBooking.getMovieSession().getSessionid() + "," + newBooking.getOwner().getName() + "\n");
            fw.close();

        }

        catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileWriter fw = new FileWriter("./data/tickets.csv", true);
            for (Ticket t: newBooking.getTickets()) {
                fw.write(t.getBooking().TID + "," + t.getSeat().getSeatId() + "," + t.getTicketType().name()+"\n");
            }

            fw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to save ticket");
        }

    }
}


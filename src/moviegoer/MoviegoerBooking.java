package moviegoer;

import models.*;
import java.util.*;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.BufferedReader;
import com.opencsv.*;
import org.fusesource.jansi.AnsiConsole;
import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;

import de.codeshelf.consoleui.elements.ConfirmChoice.ConfirmationValue;
import de.codeshelf.consoleui.prompt.ConfirmResult;
import de.codeshelf.consoleui.prompt.ConsolePrompt;
import de.codeshelf.consoleui.prompt.InputResult;
import de.codeshelf.consoleui.prompt.ListResult;
import de.codeshelf.consoleui.prompt.PromtResultItemIF;
import de.codeshelf.consoleui.prompt.builder.PromptBuilder;
import jline.TerminalFactory;
import org.fusesource.jansi.AnsiConsole;

/**
 * The functions accessible to moviegoers regarding booking
 */
public class MoviegoerBooking {

    public static void setTickets (HashMap<String, MovieSession> sessions, HashMap<String, Booking> bookings) {
        for (Booking b : bookings.values()) {
            for (Ticket t : b.getTickets()) {
                sessions.get(b.getMovieSession().getSessionId()).setSeat(t.getSeat().getSeatId());
            }
        }

    }

    /**
     * Prints the booking records of the current login user
     * @param user The current login user
     */
    public static void viewBookingRecord(User user, HashMap<String, Booking> bookings) {
        for (Booking booking : bookings.values()) {
            if (user.equals(booking.getOwner())) {
                System.out.println("Movie:" + booking.getMovieSession().getMovie().getTitle());
                System.out.println("Date: " + booking.getMovieSession().getTimeSlot().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
                System.out.printf("You bought %d tickets: ", booking.getTickets().size());
                for (Ticket t: booking.getTickets()) {
                    System.out.print(t.getSeat().getSeatId() + " ");
                }
                System.out.println();
                System.out.println();
            }

        }
    }

    public static void createBooking
        (HashMap<String, ArrayList<MovieSession>> sessions,
         User user,
         Scanner sc) throws IOException {

        boolean ok = false;

        int movieChoice = 0;
        ArrayList<String> moviesArr = new ArrayList<String>(sessions.keySet());

        while (!ok) {
            System.out.println("Select the movie by entering the corresponding number:");
            int idx = 1;
            for (String title : moviesArr) {
                System.out.println(idx++ + ") " + title);
            }
            movieChoice = sc.nextInt();
            ok = movieChoice <= idx && movieChoice > 0;
            if (!ok)
                System.out.println("Invalid number.");
        }

        MovieSession selectedSession = null;
        ArrayList<MovieSession> sessionsArr = sessions.get(moviesArr.get(movieChoice - 1));
        ok = false;

        while (!ok) {
            System.out.println("Please select the session you would like:");

            int idx = 1;
            for (MovieSession session: sessionsArr) {
                System.out.println(idx++ + ") " + session.getCinema().getCineplex().getName() + ", " + session.getCinema().getCinemaCode() + ", " + session.getTimeSlot().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
            }

            int sessionChoice = sc.nextInt();

            ok = sessionChoice <= idx && movieChoice > 0;
            if (!ok) {
                System.out.println("You have selected an invalid session");
            }
            else {
                selectedSession = sessionsArr.get(sessionChoice - 1);
            }

        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        String formatdate = now.format(format);

        String transactionid = selectedSession.getCinema().getCinemaCode() + formatdate;
        Booking newBooking = new Booking(transactionid, selectedSession, user);

        int noOfTickets = 0;
        ok = false;
        System.out.println("How many tickets would you like to purchase?");
        while (!ok) {
            noOfTickets = sc.nextInt();
            ok = noOfTickets > 0 && noOfTickets <= 8;
            if (!ok)
                System.out.println("Please choose a number between 1 and 8");
        }

        AnsiConsole.systemInstall();
        System.out.println("Green: " + ansi().fg(GREEN).a("Standard").reset());
        System.out.println("Red: " + ansi().fg(RED).a("Booked").reset());
        System.out.println("Pink: " + ansi().fgBright(MAGENTA).a("Couple Seats").reset());
        System.out.println("Blue: " + ansi().fg(BLUE).a("Disabled").reset());
        System.out.println("Yellow: " + ansi().fg(YELLOW).a("Premium").reset());
        AnsiConsole.systemUninstall();

        while (noOfTickets > 0) {

            selectedSession.listofavailableSeats();
            if (!newBooking.getTickets().isEmpty()) {
                System.out.println("You have currently selected:");
                for (Ticket t : newBooking.getTickets())
                    System.out.println(t.getSeat().getSeatId() + " " + t.getSeat().getSeatType().name() + " " + t.getTicketType());
            }
            
            System.out.println("Please select your seat number.");
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

        AnsiConsole.systemInstall();
        ConsolePrompt prompt = new ConsolePrompt();                     // #2
        PromptBuilder promptBuilder = prompt.getPromptBuilder();
        promptBuilder.createConfirmPromp()
            .name("delivery")                                      // #2
            .message("The total price for your booking is " + newBooking.getTotalPrice() + "Confirm Purchase? (y/n)")                // #3
            .addPrompt();
        HashMap<String, ? extends PromtResultItemIF> result = prompt.prompt(promptBuilder.build());    
        ConfirmResult confirmchoice = (ConfirmResult) result.get("delivery");


        //System.out.format("The total price for your booking is S$%.2f.Confirm Purchase? (y/n)\n", + newBooking.getTotalPrice());
        if (confirmchoice.getConfirmed() == ConfirmationValue.YES) return;
        AnsiConsole.systemUninstall();;


        try {
            FileWriter fw = new FileWriter("./data/bookings.csv",true);
            fw.write(newBooking.getTID() + "," + newBooking.getMovieSession().getSessionId() + "," + newBooking.getOwner().getName() + "\n");
            fw.close();
        }

        catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileWriter fw = new FileWriter("./data/tickets.csv", true);
            for (Ticket t: newBooking.getTickets()) {
                fw.write(t.getBooking().getTID() + "," + t.getSeat().getSeatId() + "," + t.getTicketType().name()+"\n");
            }
            fw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to save ticket");
        }


        System.out.printf("You have bought %d for the movie " + newBooking.getMovieSession().getMovie().getTitle() + " at " + newBooking.getMovieSession().getTimeSlot().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))
 + "\n", newBooking.getTickets().size());
        System.out.println("Your transaction ID is -> " + transactionid + ". Thank you for your purchase!");



    }

}

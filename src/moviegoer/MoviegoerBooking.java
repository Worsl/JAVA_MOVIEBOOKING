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
import de.codeshelf.consoleui.prompt.builder.ListPromptBuilder;
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
                System.out.println("Transaction ID: " + booking.getTID());
                System.out.println("Movie: " + booking.getMovieSession().getMovie().getTitle());
                System.out.println("Date: " + booking.getMovieSession().getTimeSlot().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
                System.out.printf("You bought %d tickets: \n", booking.getTickets().size());
                int idx = 1;
                for (Ticket t: booking.getTickets()) {
                    System.out.println("(" + idx++ + ") " + t.getSeat().getSeatId() + " " + t.getSeat().getSeatType().name());
                }
                System.out.println();
            }

        }
    }

    public static void createBooking
        (HashMap<String, Movie> movies,
         HashMap<String, ArrayList<MovieSession>> sessions,
         User user,
         Scanner sc) throws IOException {

        boolean ok = false;

        int movieChoice = 0;

        ArrayList<String> moviesArr = new ArrayList<String>();
        for (String title: sessions.keySet()) {
            if (movies.containsKey(title) && movies.get(title).getShowingStatus() != (ShowingStatus.END_OF_SHOWING))
                moviesArr.add(title);
        }

        if (moviesArr.isEmpty()) {
            System.out.println("There are currently no sessions screened at the moment. Please check again later.");
            return;
        }

        AnsiConsole.systemInstall();
        ConsolePrompt prompt1 = new ConsolePrompt();
        PromptBuilder promptBuilder = prompt1.getPromptBuilder();
        ListPromptBuilder lpb = promptBuilder.createListPrompt()
        .name("MovieSelection")
        .message("Select the movie you like to book");
        int index = 1;
        for (String title: moviesArr) {
            if (movies.containsKey(title) && movies.get(title).getShowingStatus() != ShowingStatus.END_OF_SHOWING)
                lpb.newItem(String.valueOf(index)).text("" + index++ + ". "+ title).add();
        }
        lpb.addPrompt();

        HashMap<String, ? extends PromtResultItemIF> result = prompt1.prompt(promptBuilder.build());
        ListResult choice = (ListResult) result.get("MovieSelection");
        movieChoice = Integer.parseInt(choice.getSelectedId());


        MovieSession selectedSession = null;
        ArrayList<MovieSession> sessionsArr = sessions.get(moviesArr.get(movieChoice - 1));

        ConsolePrompt prompt2 = new ConsolePrompt();
        PromptBuilder promptBuilder2 = prompt2.getPromptBuilder();
        ListPromptBuilder lpb2 = promptBuilder2.createListPrompt()
        .name("MovieSessionSelection")
        .message("Select the session you like to book");
        index = 1;
        for (MovieSession session: sessionsArr) {
            lpb2.newItem(String.valueOf(index)).text("" + index++ + ". "+ session.getCinema().getCineplex().getName() + ", " + session.getCinema().getCinemaCode() + ", " + session.getCinema().getCinemaClass().name() + ", " + session.getTimeSlot().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))).add();
        }
        lpb2.addPrompt();

        HashMap<String, ? extends PromtResultItemIF> result2 = prompt2.prompt(promptBuilder2.build());
        ListResult choice2 = (ListResult) result2.get("MovieSessionSelection");
        selectedSession = sessionsArr.get(Integer.parseInt(choice2.getSelectedId()) - 1);
        try {
            TerminalFactory.get().restore();
            } catch (Exception e) {
            e.printStackTrace();
            }
        

        // ok = false;

        // while (!ok) {
        //     System.out.println("Please select the session you would like:");

        //     int idx = 1;
        //     for (MovieSession session: sessionsArr) {
        //         System.out.println(idx++ + ") " + session.getCinema().getCineplex().getName() + ", " + session.getCinema().getCinemaCode() + ", " + session.getTimeSlot().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
        //     }

        //     int sessionChoice = sc.nextInt();

        //     ok = sessionChoice <= idx && movieChoice > 0;
        //     if (!ok) {
        //         System.out.println("You have selected an invalid session");
        //     }
        //     else {
        //         selectedSession = sessionsArr.get(sessionChoice - 1);
        //     }

        // }

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
            try {
                TerminalFactory.get().restore();
                } catch (Exception e) {
                e.printStackTrace();
                }

            System.out.println("Please select your seat number.");
            String seatnumber = sc.next();
            if (selectedSession.setSeat(seatnumber)) {
                TicketType tt = null;

                ConsolePrompt prompt3 = new ConsolePrompt();
                PromptBuilder promptBuilder3 = prompt3.getPromptBuilder();
                promptBuilder3.createListPrompt()
                .name("TicketTypeSelection")
                .message("Select ticket type: ")
                .newItem("1").text("1. Child (< 7)").add()
                .newItem("2").text("2. Student (7 - 18)").add()
                .newItem("3").text("3. Adult (18 - 65)").add()
                .newItem("4").text("4. Senior Citizen (> 65)").add()
                .addPrompt();

                HashMap<String, ? extends PromtResultItemIF> result3 = prompt3.prompt(promptBuilder3.build());
                ListResult choice3 = (ListResult) result3.get("TicketTypeSelection");
                int ticketChoice = Integer.parseInt(choice3.getSelectedId());

                switch(ticketChoice) {
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

                Ticket ticket = new Ticket(tt, selectedSession.getSeat(seatnumber), newBooking);

                ConsolePrompt prompt4 = new ConsolePrompt();
                PromptBuilder promptBuilder4 = prompt4.getPromptBuilder();
                promptBuilder4.createConfirmPromp()
                    .name("confirmSeat")
                    .message("This ticket price for seat " + ticket.getSeat().getSeatId() + " is S$" + String.format("%.2f", ticket.getTicketPrice()) + ". Confirm Purchase? ")
                    .addPrompt();
                HashMap<String, ? extends PromtResultItemIF> result4 = prompt4.prompt(promptBuilder4.build());
                ConfirmResult confirmchoice = (ConfirmResult) result4.get("confirmSeat");
                if (confirmchoice.getConfirmed() == ConfirmationValue.YES) {
                    noOfTickets--;
                    newBooking.addTicket(ticket);
                }
                else selectedSession.getSeat(seatnumber).unoccupySeat();
            }

        }

        AnsiConsole.systemInstall();
        ConsolePrompt prompt = new ConsolePrompt();
        PromptBuilder promptBuilder1 = prompt.getPromptBuilder();
        promptBuilder1.createConfirmPromp()
            .name("finalBooking")
            .message("The total price for your booking is S$" + String.format("%.2f", newBooking.getTotalPrice()) + ". Confirm Purchase? ")
            .addPrompt();
        HashMap<String, ? extends PromtResultItemIF> result1 = prompt.prompt(promptBuilder1.build());
        ConfirmResult confirmChoice = (ConfirmResult) result1.get("finalBooking");

        if (confirmChoice.getConfirmed() == ConfirmationValue.NO) return;
        AnsiConsole.systemUninstall();

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


        System.out.printf("You have bought %d tickets for the movie " + newBooking.getMovieSession().getMovie().getTitle() + " at " + newBooking.getMovieSession().getTimeSlot().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))
 + "\n", newBooking.getTickets().size());
        for (Ticket t: newBooking.getTickets()) System.out.print(t.getSeat() + " ");
        System.out.println();
        System.out.println("Your transaction ID is -> " + transactionid + ". Thank you for your purchase!");



    }

}

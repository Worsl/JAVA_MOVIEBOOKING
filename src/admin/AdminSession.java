package admin;

import models.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import java.time.format.DateTimeFormatter;

/**
 * The functions accessible to Admin regarding Session
 */
public class AdminSession {

    /**
     * Creates a new session to be added to the system
     * @param cinemas The list of current cinemas in the system
     * @param movies The list of current movies in the system
     * @param sessions The list of current sessions in the system
     */
    public static void newSession(HashMap<String, Cinema> cinemas,
                                  HashMap<String, Movie> movies,
                                  HashMap<String, ArrayList<MovieSession>> sessions,
                                  Scanner sc) {

        System.out.println("Enter cinema code: ");
        String cinema = sc.next();
        if (cinemas.containsKey(cinema)) {
            sc.nextLine();
            System.out.println("Enter movie title: ");
            String title = sc.nextLine();
            if (movies.containsKey(title)) {
                System.out.println("Enter date time (YYYY-MM-DD HH:MM format): ");
                String dateTime = sc.nextLine();
                while (!(dateTime.matches("\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}"))) {
                    System.out.println("Enter date time (YYYY-MM-DD HH:MM format): ");
                    dateTime = sc.nextLine();
                }

                DAO.addSession(cinema, title, dateTime, AdminUtils.generateSessionId(sessions));
            }
            else
                System.out.println("That movie does not exist!");

        }
        else
            System.out.println("That cinema does not exist!");


    }

    /**
     * Updates an existing record of a session in the system
     * @param cinemas The list of current cinemas in the system
     * @param movies  The list of current movies in the system
     */
    public static void updateSession(HashMap<String, Cinema> cinemas,
                                     HashMap<String, Movie> movies,
                                     Scanner sc) {
        System.out.println("Enter session id: ");
        String sessionId = sc.next();

        System.out.println("Enter new cinema code: ");
        String newCinema = sc.next();
        if (cinemas.containsKey(newCinema)) {
            sc.nextLine();
            System.out.println("Enter new movie title: ");
            String newTitle = sc.nextLine();
            if (movies.containsKey(newTitle)) {
                System.out.println("Enter new date time (YYYY-MM-DD HH:MM format): ");
                String newDateTime = sc.nextLine();
                while (!(newDateTime.matches("\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}"))) {
                    System.out.println("Enter new date time (YYYY-MM-DD HH:MM format): ");
                    newDateTime = sc.nextLine();
                }
                DAO.updateSession(newCinema, newTitle, newDateTime, sessionId);
            }
            else
                System.out.println("That movie does not exist!");
        }
        else
            System.out.println("That cinema does not exist!");
    }

    /**
     * Deletes the session with the corresponding id
     * @param sc The scanner used in the entry point
     */
    public static void deleteSession(Scanner sc) {
        System.out.println("Enter session id: ");
        String sessionId = sc.next();

        DAO.deleteSession(sessionId);
    }

    /**
     * Prints all the movie sessions stored
     * @param sessions The list of current sessions in the system
     */
    public static void showSession(HashMap<String, ArrayList<MovieSession>> sessions) {

        System.out.println("Cinema    Session                SessionId        Title");
        System.out.println(" ");

        for (ArrayList<MovieSession> al: sessions.values()) {
            for (MovieSession s: al) {
                System.out.println
                    (s.getCinema().getCinemaCode() + "   " +
                     s.getTimeSlot().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")) +
                     "       " + s.getSessionId() + "              " + s.getMovie().getTitle());
            }
        }
    }
}

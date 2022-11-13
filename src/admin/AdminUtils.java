package admin;

import models.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Helper functions used in the admin program
 */
public class AdminUtils {

    /**
     * Generates a unique session Id to be assigned to a new session
     * @param sessions The list of existing sessions in our system
     * @return the newly created session Id
     */
    public static String generateSessionId(HashMap<String, ArrayList<MovieSession>> sessions) {
        int id = 0;

        for (ArrayList<MovieSession> al: sessions.values()) {
            for (MovieSession s: al) {
                id = Math.max(id, Integer.parseInt(s.getSessionId()));
            }
        }

        return String.valueOf(id + 1);
    }

    /**
     * Adds a new holiday date and saves into the system.
     */
    public static void addHolidayDate(Scanner sc) {
        System.out.println("Enter holiday date (YYYY-MM-DD format): ");
        String addedDate = sc.next();
        while (!(addedDate.matches("\\d{4}-\\d{2}-\\d{2}"))) {
            System.out.println("Enter holiday date (YYYY-MM-DD format): ");
            addedDate = sc.next();
        }
        DAO.addHolidayToCSV(addedDate);
    }
}

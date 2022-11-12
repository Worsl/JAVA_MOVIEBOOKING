package admin;

import models.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Helper functions used in the admin program
 */
public class AdminUtils {
    public static String generateSessionId(HashMap<String, ArrayList<MovieSession>> sessions) {
        int id = 0;

        for (ArrayList<MovieSession> al: sessions.values()) {
            for (MovieSession s: al) {
                id = Math.max(id, Integer.parseInt(s.getSessionId()));
            }
        }

        return String.valueOf(id + 1);
    }

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

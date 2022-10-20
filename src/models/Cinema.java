package models;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.LinkedList;

/**
 * Represents a single cinema within a cineplex;
 */
public class Cinema {

    /**
     * The code of the cinema.
     */
    private String cinemaCode;

    /**
     * The class of the cinema.
     */
    private String classType;

    /**
     * The Cineplex the cinema is in.
     */
    private Cineplex cineplex;

    /**
     * Creates a new Cinema from the given parameters
     * @param cinemaCode the Cinema's code
     * @param capacity the Cinema's capacity
     * @param classType the Cinema's class
     * @param cineplex the Cinema's cineplex
     */
    public Cinema(String cinemaCode, String classType, Cineplex cineplex) {
        this.cinemaCode = cinemaCode;
        this.classType = classType;
        this.cineplex = cineplex;
    }

    /**
     * Gets the code of this Cinema.
     * @return this Cinema's name.
     */
    public String getCinemaCode() {
        return this.cinemaCode;
    }

    /**
     * Gets the class of this Cinema.
     * @return this Cinema's class.
     */
    public String getClassType() {
        return this.classType;
    }

    /**
     * Gets the cineplex of this Cinema.
     * @return this Cinema's cineplex.
     */
    public Cineplex getCineplex() {
        return this.cineplex;
    }

    /**
     * Reads data from file and parses into a linked list of Cinemas
     */
    public static LinkedList<Cinema> getCinemas(LinkedList<Cineplex> cineplexes) {

        LinkedList<Cinema> list = new LinkedList<Cinema>();
        try {
            File f = new File("./data/cinema.csv");

            Scanner sc = new Scanner(f);

            String in, params[];
            while (sc.hasNextLine()) {
                in = sc.nextLine();
                // regex to split string by comma, but not those within quotation marks
                params = in.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                Cineplex cineplex = null;
                for (Cineplex c: cineplexes) {
                    if (c.getName().equals(params[2])) {
                        cineplex = c;
                        break;
                    }
                }

                if (cineplex != null)
                    list.add(new Cinema(params[0], params[1], cineplex));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Cinemas File not found");
            e.printStackTrace();
        }

        return list;

    }

}

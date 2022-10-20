package models;

import java.time.LocalTime;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.LinkedList;

/**
 * Represents a cineplex theatre
 */
public class Cineplex {

    /**
     * The name of the cineplex.
     */
    private String name;

    /**
     * The location of the cineplex.
     */
    private String location;

    /**
     * The time when the cineplex opens
     */
    private LocalTime openingHours;

    /**
     * The time when the cineplex closes
     */
    private LocalTime closingHours;

    /**
     * Creates a new Cineplex from the given parameters
     * @param name The Cineplex's name
     * @param location The Cineplex's location
     * @param openingHours The Cineplex's opening hours
     * @param closingHours The Cineplex's closing hours
     */
    public Cineplex (String name, String location, String openingHours, String closingHours) {
        this.name = name;
        this.location = location;
        this.openingHours = LocalTime.parse(openingHours);
        this.closingHours = LocalTime.parse(closingHours);
    }

    /**
     * Gets the name of this Cineplex.
     * @return this Cineplex's name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the location of this Cineplex.
     * @return this Cineplex's location.
     */
    public String getLocation() {
        return this.location;
    }

    /**
     * Gets the opening hours of this Cineplex.
     * @return this Cineplex's opening hours.
     */
    public LocalTime getOpeningHours() {
        return this.openingHours;
    }

    /**
     * Gets the closing hours of this Cineplex.
     * @return this Cineplex's closing hours.
     */
    public LocalTime getClosingHours() {
        return this.closingHours;
    }

    /**
     * Gets the availability of whether this Cineplex is open.
     * @param time The given time to check whether cineplex is open.
     * @return true if cineplex is open at given timing, false otherwise.
     */
    public boolean isOpen(LocalTime time) {
        return (openingHours.compareTo(time) <= 0) && (closingHours.compareTo(time) >= 0);
    }

    /**
     * Reads data from file and parses into a linked list of Cineplexes
     * @return a list of the available Cineplexes
     */
    public static LinkedList<Cineplex> getCineplexes() {

        LinkedList<Cineplex> list = new LinkedList<Cineplex>();
        try {
            File f = new File("./data/cineplexes.csv");

            Scanner sc = new Scanner(f);

            String in, params[];
            while (sc.hasNextLine()) {
                in = sc.nextLine();
                // regex to split by comma, but not those within quotation marks
                params = in.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                list.add(new Cineplex(params[0], params[1], params[2], params[3]));
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("Cineplexes File not found");
            e.printStackTrace();
        }

        return list;

    }
}

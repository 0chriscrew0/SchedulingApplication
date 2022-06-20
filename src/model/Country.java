package model;

/**
 * This class creates Country objects to be used throughout the scheduling application.
 */
public class Country {

    /**
     * the ID of the country
     */
    private int id;

    /**
     * the name of the country
     */
    private String name;

    /**
     * Creates a country object
     * @param id the ID of the country
     * @param name the name of the country
     */
    public Country(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Gets the ID of the country
     * @return the ID of the country
     */
    public int getId() {
        return this.id;
    }

    /**
     * Gets the name of the country
     * @return the name of the country
     */
    public String getName() {
        return this.name;
    }
}

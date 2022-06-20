package model;

/**
 * This class is used to create first level division objects to be used throughout the scheduling application
 */
public class FirstLevelDivision {

    /**
     * the ID of the division
     */
    private int divisionID;

    /**
     * the name of the division
     */
    private String division;

    /**
     * the ID of the country of the division
     */
    private int countryID;

    /**
     * Creates a first level division object
     * @param divisionID the ID of the division
     * @param division the name of the division
     * @param countryID the ID of the country of the division
     */
    public FirstLevelDivision(int divisionID, String division, int countryID) {
        this.divisionID = divisionID;
        this.division = division;
        this.countryID = countryID;
    }

    /**
     * Gets the ID of the division
     * @return the ID of the division
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * Gets the name of the division
     * @return the name of the division
     */
    public String getDivision() {
        return division;
    }

    /**
     * Gets the ID of the country of the division
     * @return the ID of the country of the division
     */
    public int getCountryID() {
        return countryID;
    }
}

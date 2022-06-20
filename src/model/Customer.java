package model;

/**
 * This class is used to create Customer objects to be used throughout the scheduling application
 */
public class Customer {

    /**
     * the ID of the customer
     */
    private int ID;

    /**
     * the name of the customer
     */
    private String name;

    /**
     * the address of the customer
     */
    private String address;

    /**
     * the postal code of the customer
     */
    private String postalCode;

    /**
     * the phone number of the customer
     */
    private String phone;

    /**
     * the ID of the division of the customer
     */
    private int divisionID;

    /**
     * Creates a customer obhect
     * @param ID the ID of the customer
     * @param name the name of the customer
     * @param address the address of the customer
     * @param postalCode the postal code of the customer
     * @param phone the phone number of the customer
     * @param divisionID the ID of the division of the customer
     */
    public Customer(int ID, String name, String address, String postalCode, String phone, int divisionID) {
        this.ID = ID;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionID = divisionID;
    }

    /**
     * Gets the ID of the customer
     * @return the ID of the customer
     */
    public int getID() {
        return ID;
    }

    /**
     * Gets the name of the customer
     * @return the name of the customer
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the address of the customer
     * @return the address of the customer
     */
    public String getAddress() {
        return address;
    }

    /**
     * Gets the postal code of the customer
     * @return the postal code of the customer
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Gets the phone number of the customer
     * @return the phone number of the customer
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Gets the ID of the division of the customer
     * @return the ID of the division of the customer
     */
    public int getDivisionID() {
        return divisionID;
    }
}

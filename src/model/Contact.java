package model;

/**
 * This class is used to create Contacts to be used throughout the application
 */
public class Contact {

    /**
     * the ID of the contact
     */
    private int ID;

    /**
     * the name of the contact
     */
    private String name;
    /**
     * the email of the contact
     */
    private String email;

    /**
     * Creates a contact object
     * @param ID the ID of the contact
     * @param name the name of the contact
     * @param email the email of the contact
     */
    public Contact(int ID, String name, String email) {
        this.ID = ID;
        this.name = name;
        this.email = email;
    }

    /**
     * Gets the ID of the contact
     * @return the ID of the contact
     */
    public int getID() {
        return ID;
    }

    /**
     * Gets the name of the contact
     * @return the name of the contact
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the email of the contact
     * @return the email of the contact
     */
    public String getEmail() {
        return email;
    }
}

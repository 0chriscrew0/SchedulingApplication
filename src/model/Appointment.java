package model;

import java.time.LocalDateTime;

/***
 * This class is used to create Appointment objects for use throughout the scheduling application
 */
public class Appointment {
    /**
     * The unique ID of the appointment.
     */
    private int ID;
    /**
     * The title of the appointment.
     */
    private String title;
    /**
     * description of the appointment
     */
    private String description;
    /**
     * location of the appointment
     */
    private String location;
    /**
     * type of the appointment
     */
    private String type;
    /**
     * start date and time of the appointment
     */
    private LocalDateTime start;
    /**
     * end date and time of the appointment
     */
    private LocalDateTime end;
    /**
     * ID of the customer for the appointment
     */
    private int customerID;
    /**
     * ID of the customer for the appointment
     */
    private int userID;
    /**
     * ID of the contact for the appointment
     */
    private int contactID;

    /**
     * Creates an appointment object
     * @param ID unique ID of appointment
     * @param title title of appointment
     * @param description description of appointment
     * @param location location of appointment
     * @param type type of appointment
     * @param start start date and time of appointment
     * @param end end date and time of appointment
     * @param customerID ID of the customer for the appointment
     * @param userID ID of the user for the appointment
     * @param contactID ID of the contact for the appointment
     */
    public Appointment(int ID, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerID, int userID, int contactID) {
        this.ID = ID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    /**
     * Gets ID of appointment
     * @return the ID of the appointment
     */
    public int getID() {
        return ID;
    }

    /**
     * Gets title of appointment
     * @return the title of the appointment
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets description of appointment
     * @return the description of the appointment
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets location of appointment
     * @return the location of the appointment
     */
    public String getLocation() {
        return location;
    }

    /**
     * Gets type of appointment
     * @return the type of the appointment
     */
    public String getType() {
        return type;
    }

    /**
     * Gets start date and time of appointment
     * @return the start date and time of the appointment
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Gets end date and time of appointment
     * @return the end date and time of the appointment
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Gets the ID of the customer for the appointment
     * @return the ID of the customer for the appointment
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Gets the ID of the user for the appointment
     * @return the ID of the user for the appointment
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Gets the ID of the contact for the appointment
     * @return the ID of the contact for the appointment
     */
    public int getContactID() {
        return contactID;
    }
}

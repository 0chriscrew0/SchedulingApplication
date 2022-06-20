package model;

/**
 * This class is used to create user objects to be used throughout the scheduling application
 */
public class User {

    /**
     * the ID of the user
     */
    private int userId;

    /**
     * the username of the user
     */
    private String username;

    /**
     * the password of the user
     */
    private String password;

    /**
     * Creates a user object
     * @param userId the ID of the user
     * @param username the username of the user
     * @param password the password of the user
     */
    public User(int userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the ID of the user
     * @return the ID of the user
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Gets the username of the user
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password of the user
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }
}

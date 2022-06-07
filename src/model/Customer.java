package model;

public class Customer {
    private int ID;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private int divisionID;

    public Customer(int ID, String name, String address, String postalCode, String phone, int divisionID) {
        this.ID = ID;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionID = divisionID;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public int getDivisionID() {
        return divisionID;
    }
}

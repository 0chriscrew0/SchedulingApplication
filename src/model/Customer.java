package model;

public class Customer {
    private int ID;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private String division;

    public Customer(int ID, String name, String address, String postalCode, String phone, String division) {
        this.ID = ID;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.division = division;
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

    public String getDivision() {
        return division;
    }
}

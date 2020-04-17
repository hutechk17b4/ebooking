package com.example.ungdungdatvexemphim.Models;

public class Customer {

    private int idCustomer;

    private String userName;

    private String email;

    public Customer(int idCustomer, String userName, String email) {
        this.idCustomer = idCustomer;
        this.userName = userName;
        this.email = email;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

package com.example.ungdungdatvexemphim.Models;

public class HistoryBook {
    String IDBooking;
    String TimeCreate;

    public HistoryBook(String IDBooking, String timeCreate) {
        this.IDBooking = IDBooking;
        TimeCreate = timeCreate;
    }

    public String getIDBooking() {
        return IDBooking;
    }

    public void setIDBooking(String IDBooking) {
        this.IDBooking = IDBooking;
    }

    public String getTimeCreate() {
        return TimeCreate;
    }

    public void setTimeCreate(String timeCreate) {
        TimeCreate = timeCreate;
    }
}

package com.example.ungdungdatvexemphim.Models;

public class LichTrinh {
    String IDlichtrinh;
    String IDphim;
    String IDrap;
    String StartTime;
    String EndTime;

    public LichTrinh(String IDlichtrinh, String IDphim, String IDrap, String startTime, String endTime) {
        this.IDlichtrinh = IDlichtrinh;
        this.IDphim = IDphim;
        this.IDrap = IDrap;
        StartTime = startTime;
        EndTime = endTime;
    }

    public String getIDlichtrinh() {
        return IDlichtrinh;
    }

    public void setIDlichtrinh(String IDlichtrinh) {
        this.IDlichtrinh = IDlichtrinh;
    }

    public String getIDphim() {
        return IDphim;
    }

    public void setIDphim(String IDphim) {
        this.IDphim = IDphim;
    }

    public String getIDrap() {
        return IDrap;
    }

    public void setIDrap(String IDrap) {
        this.IDrap = IDrap;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }
}

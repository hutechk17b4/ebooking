package com.example.ungdungdatvexemphim.Models;

public class LichTrinh {
   String IDlichtrinh;
    String IDrap;
    String StartTime;
    String EndTime;
    String IDngaychieu;

    public LichTrinh(String IDlichtrinh, String IDrap, String startTime, String endTime, String IDngaychieu) {
        this.IDlichtrinh = IDlichtrinh;
        this.IDrap = IDrap;
        StartTime = startTime;
        EndTime = endTime;
        this.IDngaychieu = IDngaychieu;
    }

    public String getIDlichtrinh() {
        return IDlichtrinh;
    }

    public void setIDlichtrinh(String IDlichtrinh) {
        this.IDlichtrinh = IDlichtrinh;
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

    public String getIDngaychieu() {
        return IDngaychieu;
    }

    public void setIDngaychieu(String IDngaychieu) {
        this.IDngaychieu = IDngaychieu;
    }
}

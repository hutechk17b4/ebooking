package com.example.ungdungdatvexemphim.Models;

import com.example.ungdungdatvexemphim.R;

public class Seat {
    private String ID;
    private String Cot;
    private String SeatID;
    private String Status;
    private String IDrap;
    private String HangGhe;
    private int Hinh;

    public boolean isSelected=false;
    public boolean isBooked = false;


    public Seat(String ID, String cot, String seatID, String status, String IDrap, String hangGhe, int hinh) {
        this.ID = ID;
        Cot = cot;
        SeatID = seatID;
        Status = status;
        this.IDrap = IDrap;
        HangGhe = hangGhe;
        Hinh = hinh;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCot() {
        return Cot;
    }

    public void setCot(String cot) {
        Cot = cot;
    }

    public String getSeatID() {
        return SeatID;
    }

    public void setSeatID(String seatID) {
        SeatID = seatID;
    }

    public String getIDrap() {
        return IDrap;
    }

    public void setIDrap(String IDrap) {
        this.IDrap = IDrap;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getHangGhe() {
        return HangGhe;
    }

    public void setHangGhe(String hangGhe) {
        HangGhe = hangGhe;
    }

    public int getHinh() {
        return Hinh;
    }

    public void setHinh(int hinh) {
        Hinh = hinh;
    }

    public void checkselect()
    {
        if(isSelected==true)
        {
            this.setHinh(R.drawable.seat_selected);
        }
        else {
            this.setHinh(R.drawable.seat_sale);
        }
    }

}

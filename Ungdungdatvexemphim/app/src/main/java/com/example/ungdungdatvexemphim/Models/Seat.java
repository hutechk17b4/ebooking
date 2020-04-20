package com.example.ungdungdatvexemphim.Models;

import com.example.ungdungdatvexemphim.R;

public class Seat {
    private int ID;
    private String HangGhe;
    private int Soghe;
    private int Hinh;

    public boolean isSelected=false;
    public boolean isBooked = false;




    public Seat(int ID, String hangGhe, int soghe, int hinh) {
        this.ID = ID;
        HangGhe = hangGhe;
        Soghe = soghe;
        Hinh = hinh;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getHangGhe() {
        return HangGhe;
    }

    public void setHangGhe(String hangGhe) {
        HangGhe = hangGhe;
    }

    public int getSoghe() {
        return Soghe;
    }

    public void setSoghe(int soghe) {
        Soghe = soghe;
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

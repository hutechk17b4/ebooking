package com.example.ungdungdatvexemphim.Models;

public class Ngay {
    String ID;
    String NgayChieu;
    String IDphim;

    public Ngay(String ID, String ngayChieu, String IDphim) {
        this.ID = ID;
        NgayChieu = ngayChieu;
        this.IDphim = IDphim;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNgayChieu() {
        return NgayChieu;
    }

    public void setNgayChieu(String ngayChieu) {
        NgayChieu = ngayChieu;
    }

    public String getIDphim() {
        return IDphim;
    }

    public void setIDphim(String IDphim) {
        this.IDphim = IDphim;
    }
}

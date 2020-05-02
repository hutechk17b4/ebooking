package com.example.ungdungdatvexemphim.Models;

public class Ticket {
    String IDticket;
    String gia;
    String loai;

    public Ticket(String IDticket, String gia, String loai) {
        this.IDticket = IDticket;
        this.gia = gia;
        this.loai = loai;
    }

    public String getIDticket() {
        return IDticket;
    }

    public void setIDticket(String IDticket) {
        this.IDticket = IDticket;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }
}

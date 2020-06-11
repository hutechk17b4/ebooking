package com.example.ungdungdatvexemphim.Models;

public class DataAI {
    String iduser;
    String idtheloai;
    String rate;

    public DataAI(String iduser, String idtheloai, String rate) {
        this.iduser = iduser;
        this.idtheloai = idtheloai;
        this.rate = rate;
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public String getIdtheloai() {
        return idtheloai;
    }

    public void setIdtheloai(String idtheloai) {
        this.idtheloai = idtheloai;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}

package com.example.ungdungdatvexemphim.Models;

public class SeatBooked {
   private String ID;
    private String seat_collumn;
    private String seat_row;
    private String IDLichTrinh;
    private String status;


    public SeatBooked(String ID, String seat_collumn, String seat_row, String IDLichTrinh, String status) {
        this.ID = ID;
        this.seat_collumn = seat_collumn;
        this.seat_row = seat_row;
        this.IDLichTrinh = IDLichTrinh;
        this.status = status;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getSeat_collumn() {
        return seat_collumn;
    }

    public void setSeat_collumn(String seat_collumn) {
        this.seat_collumn = seat_collumn;
    }

    public String getSeat_row() {
        return seat_row;
    }

    public void setSeat_row(String seat_row) {
        this.seat_row = seat_row;
    }

    public String getIDLichTrinh() {
        return IDLichTrinh;
    }

    public void setIDLichTrinh(String IDLichTrinh) {
        this.IDLichTrinh = IDLichTrinh;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

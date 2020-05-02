package com.example.ungdungdatvexemphim.Models;

public class DetailHistoryBook extends  HistoryBook {

    String idkhachhang;

    String IDseat_no;
    String tenphim;
    String idlichtrinh;


    public DetailHistoryBook(String IDBooking, String timeCreate) {
        super(IDBooking, timeCreate);
    }

    public DetailHistoryBook(String IDBooking, String timeCreate, String idkhachhang, String IDseat_no, String tenphim, String idlichtrinh) {
        super(IDBooking, timeCreate);
        this.idkhachhang = idkhachhang;
        this.IDseat_no = IDseat_no;
        this.tenphim = tenphim;
        this.idlichtrinh = idlichtrinh;
    }

    public String getIdkhachhang() {
        return idkhachhang;
    }

    public void setIdkhachhang(String idkhachhang) {
        this.idkhachhang = idkhachhang;
    }

    public String getIDseat_no() {
        return IDseat_no;
    }

    public void setIDseat_no(String IDseat_no) {
        this.IDseat_no = IDseat_no;
    }

    public String getTenphim() {
        return tenphim;
    }

    public void setTenphim(String tenphim) {
        this.tenphim = tenphim;
    }

    public String getIdlichtrinh() {
        return idlichtrinh;
    }

    public void setIdlichtrinh(String idlichtrinh) {
        this.idlichtrinh = idlichtrinh;
    }
}

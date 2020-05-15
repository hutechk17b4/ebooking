package com.example.ungdungdatvexemphim.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ungdungdatvexemphim.Adapters.SeatAdapter;
import com.example.ungdungdatvexemphim.Models.Seat;
import com.example.ungdungdatvexemphim.Models.SeatBooked;
import com.example.ungdungdatvexemphim.Models.Ticket;
import com.example.ungdungdatvexemphim.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChooseSeatActivity extends Activity {


    GridView gvSeat;

    Button btnXacNhanChoose;
    TextView txvTenPhim, txvTenRap ,txtTotalPrice ;
    String IDphim = "";
    String IDrap = "";
    String StartTime="";
    String EndTime="";
    String IDngaychieu="";
    String IDlichtrinh="";

    ArrayList<Ticket> arrticket;
    ArrayList<SeatBooked> arrbooked;
    ArrayList<Seat> arrSeat;
    SeatAdapter adapter;


    String urlGetSeatBooked="http://192.168.1.42/PHP_Data/getSeatBooked.php";
    String getUrlGetSeatBooked2="http://192.168.42.109/php_ebooking/getSeatBooked2.php";
    String urlgetTicket="http://192.168.42.109/PHP_Data/getTicket.php";


    String urlGetTenPhim="http://192.168.1.42/PHP_Data/getTenPhim.php";

    String urlGetSeatR="http://192.168.1.42/PHP_Data/getSeat.php";
    String urlGetIDphim="http://192.168.1.42/PHP_Data/getMovieName.php";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_seat_place);
        AnhXa();
        adapter = new SeatAdapter(this, R.layout.dong_seat, arrSeat);
        gvSeat.setAdapter(adapter);


        //================= lấy dữ liệu phim rạp truyền qua từ select time activity
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("DulieuPhimRap");
//        StartTime=bundle.getString("StartTime");
//        EndTime=bundle.getString("EndTime");
        IDlichtrinh=bundle.getString("IDlichtrinh");
        IDngaychieu=bundle.getString("IDngaychieu");

        txvTenRap.setText("Rạp: " + bundle.getString("IDrap"));



        IDrap=bundle.getString("IDrap");



        //=========================================================
        getDataSeat(IDrap);

        // lấy dữ liệu ghế


        getIDphim(IDngaychieu);


        xulySuKienData();// xử lý sự kiện truyền dữ liệu ghế đã book

    }

    private void AnhXa() {

        btnXacNhanChoose = (Button) findViewById(R.id.btnXacNhanBook);
        gvSeat = (GridView) findViewById(R.id.grvSeat);
        txvTenPhim = (TextView) findViewById(R.id.txvThongTinPhim);
        txvTenRap = (TextView) findViewById(R.id.txttenrap);
        txtTotalPrice = findViewById(R.id.txtTotalPrice);

        arrSeat = new ArrayList<>();


//        arrSeat.add(new Seat("1","x",R.drawable.seat_sale));
//        arrSeat.add(new Seat("2","x",R.drawable.seat_sale));
//        arrSeat.add(new Seat("3","x",R.drawable.seat_sale));
//        arrSeat.add(new Seat("4","x",R.drawable.seat_sale));
//        arrSeat.add(new Seat("5","x",R.drawable.seat_sale));
//        arrSeat.add(new Seat("6","x",R.drawable.seat_sale));
    }

    private void getDataSeat(final String ID) {// lấy dữ liệu ghế theo IDrap  ra hihi

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGetSeatR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                String ID = object.getString("Id");
                                String Cot = object.getString("cot");// có
                                String SeatID=object.getString("seatid");
                                String status=object.getString("status");// có
                                String idrap=object.getString("idrap");
                                String Hangghe = object.getString("hang");// có
                                Seat seat=new Seat(ID,Cot,SeatID,status,idrap,Hangghe,R.drawable.seat_sale);
                                arrSeat.add(seat);
                               // getSeatBooked();

                               getSeatBook(Cot,IDlichtrinh,Hangghe,ID,SeatID,idrap);// load seat đồng thời load seat đã book

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("IDrapPost", ID);
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }


    //========================================
    public void xulySuKienData() {// lấy và truyền dữ liệu ghế từ trong adapter hihi

        btnXacNhanChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 String tenphim=txvTenPhim.getText().toString().trim();

                String idrap=txvTenRap.getText().toString().trim();

                String[] seatsCot = new String[arrSeat.size()];// tạo mảng int (vì truyền giá trị id) seats = ... [khai báo số lượng phần tử]
                String[] seatsHang = new String[arrSeat.size()];
                String [] id=new String[arrSeat.size()];
                String []seatid=new String[arrSeat.size()];
                String [] IDrap=new String[arrSeat.size()];

            //    String [] Giave=new String[arrticket.size()];


                for (int i = 0; i < arrSeat.size(); i++) {
                    if (arrSeat.get(i).isSelected) {
                        seatsCot[i] = arrSeat.get(i).getCot();
                        seatsHang[i] = arrSeat.get(i).getHangGhe();
                        id[i]=arrSeat.get(i).getID();
                        seatid[i]=arrSeat.get(i).getSeatID();
                        IDrap[i]=arrSeat.get(i).getIDrap();
//                        getTicket(seatid[i],IDrap[i]);
//
//                        for (int j=0;j<arrticket.size();j++)
//                        {
//
//                            Giave[j]=arrticket.get(j).getGia();
//
//                        }


                        //   IDrap[i]=arrSeat.get(i).getIDrap();



                    } else {
                        seatsCot[i] = "null";
                        seatsHang[i]="null";
                        id[i]="null";
                       // btnXacNhanChoose.setClickable(false);
                    }

                }



                Intent intent = new Intent(ChooseSeatActivity.this, FinalBookingActivity.class);
                Bundle bundle = new Bundle();

//                bundle.putIntArray("HINHSEAT",seatsHinh);
//                bundle.putIntArray("IDSEAT",seatsID);
//                bundle.putIntArray("SOSEAT", seats);
                bundle.putStringArray("ID", id);
                bundle.putStringArray("SEATID", seatid);
                   bundle.putString("TENPHIM",tenphim);
                    bundle.putString("IDRAP",idrap);
                    bundle.putStringArray("IDRAPpost",IDrap);

                bundle.putString("IDlichtrinh",IDlichtrinh);
//                bundle.putString("Startime",StartTime);
//                bundle.putString("Endtime",EndTime);
                bundle.putStringArray("COTGHE",seatsCot);
                bundle.putStringArray("HANGGHE",seatsHang);
                intent.putExtra("BUNDLE_IDSEAT", bundle);
                startActivity(intent);


            }
        });

    }
    //============================================================================

    private void getIDphim(final String ID)
    {
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, urlGetIDphim,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array=new JSONArray(response);
                            for (int i=0;i<array.length();i++)
                            {
                                JSONObject object=array.getJSONObject(i);
                                String IDphim= object.getString("idphim");
                                getTenPhim(IDphim);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params= new HashMap<>();
                params.put("IDngaychieu",ID);
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }


    //==============================================================================================
    private void getTenPhim(final String ID)// lấy tên Phim theo IDphim post lên
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGetTenPhim,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                String Tenphim = object.getString("tenphim");
                                txvTenPhim.setText(Tenphim);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("IDphimPost", ID);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


//====================================================================================
        private void getSeatBook(final String cot,final String idlichtrinh, final String hang, final String id, final String seatID, final String idrap)//
    {
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, urlGetSeatBooked,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray array=new JSONArray(response);
                            for (int i=0;i<array.length();i++)
                            {
                                JSONObject object=array.getJSONObject(i);
                                String cot=object.getString("cot");
                                String hang=object.getString("hang");
                                String idlichtrinh=object.getString("Idlichtrinh");
                                String IDseatno=object.getString("Idseat_no");
                                String SeatID=object.getString("seatID");
                                String IDrap=object.getString("IDrap");
                                String status=object.getString("trangthai");
                                Seat seat=new Seat(IDseatno,cot,SeatID,status,IDrap,hang,R.drawable.seat_sold);// IDseatno la ID
                                seat.isBooked=true;

                                String[] seatsCot = new String[arrSeat.size()];
                                String[] seatsHang = new String[arrSeat.size()];
                                String [] IDseat_no=new String[arrSeat.size()];
                                for (int j=0;j<arrSeat.size();j++)
                                {
                                    seatsCot[j] = arrSeat.get(j).getCot();
                                    seatsHang[j]=arrSeat.get(j).getHangGhe();
                                    IDseat_no[j]= arrSeat.get(j).getID();
                                    if(seatsCot[j].equals(cot) && seatsHang[j].equals(hang) && IDlichtrinh.equals(idlichtrinh) && IDseat_no[j].equals(IDseatno))
                                    {

                                       arrSeat.set(j,seat);
                                     //  Toast.makeText(ChooseSeatActivity.this,seatsCot[j]+" "+seatsHang[j]+" "+ idlichtrinh+""+arrSeat.get(j).isBooked,Toast.LENGTH_SHORT).show();
                                    }

                                }


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ChooseSeatActivity.this,error.toString(),Toast.LENGTH_SHORT).show();

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params= new HashMap<>();
                params.put("seat_collumnPost",cot);
                params.put("IDLichTrinhPost",idlichtrinh);
                params.put("seat_rowPost",hang);
                params.put("IDseat_noPost",id);
                params.put("SeatIDPost",seatID);
                params.put("IDrapPost",idrap);
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }
    //============================================================
    //=============================================================
//    private void getTicket(final String seatid, final String idrap)
//    {
//        RequestQueue requestQueue=Volley.newRequestQueue(this);
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlgetTicket,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONArray array=new JSONArray(response);
//                            for (int i=0;i<array.length();i++)
//                            {
//                                JSONObject object=array.getJSONObject(i);
//                                String id= object.getString("IDticket");
//                                String gia= object.getString("gia");
//                                String loai= object.getString("loai");
//                                Ticket ticket=new Ticket(id,gia,loai);
//                                arrticket.add(ticket);
//
//
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                }
//        ){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> params= new HashMap<>();
//                params.put("seatIDPost",seatid);
//                params.put("IDrapPost",idrap);
//                return params;
//            }
//        };
//        requestQueue.add(stringRequest);
//
//    }
    //============================
//    private void showprice()
//    {
//        String Gia[]=new String[arrticket.size()];
//        final StringBuilder data = new StringBuilder();
//        for (int j=0;j<arrticket.size();j++)
//        {
//            Gia[j]=arrticket.get(j).getGia();
//            data.append(Gia[j]+" ");
//            txvPrice.setText("Giá :" +data);
//        }
//    }








}






package com.example.ungdungdatvexemphim.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ungdungdatvexemphim.Models.Seat;
import com.example.ungdungdatvexemphim.Adapters.SeatAdapter;
import com.example.ungdungdatvexemphim.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChooseSeatActivity extends AppCompatActivity {


    GridView gvSeat;

    Button btnXacNhanChoose;
    TextView txvTenPhim, txvTenRap;
    String IDphim = "";
    String IDrap = "";
    String StartTime="";
    String EndTime="";


    ArrayList<Seat> arrSeat;
    SeatAdapter adapter;

    String urlGetTenPhim="http://192.168.1.9/php_ebooking/getTenPhim.php";

    String urlGetSeatR="http://192.168.1.9/php_ebooking/getSeatRap.php";
    String urlGetSeatR2="http://192.168.1.9/php_ebooking/getSeatRap2.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_seat);
        AnhXa();
        adapter = new SeatAdapter(this, R.layout.dong_seat, arrSeat);
        gvSeat.setAdapter(adapter);


        //================= lấy dữ liệu phim rạp truyền qua từ select time activity
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("DulieuPhimRap");
        StartTime=bundle.getString("StartTime");
        EndTime=bundle.getString("EndTime");
        txvTenRap.setText("Rạp: " + bundle.getString("IDrap"));


        IDphim=bundle.getString("IDphim");
        IDrap=bundle.getString("IDrap");



        //=========================================================

        getDataSeat(IDrap);// lấy dữ liệu ghế

        getTenPhim(IDphim);// gán tên phim vào textview


        xulySuKienData();// xử lý sự kiện truyền dữ liệu ghế đã book
        //===================================================

    }


    private void AnhXa() {

        btnXacNhanChoose = (Button) findViewById(R.id.btnXacNhanBook);
        gvSeat = (GridView) findViewById(R.id.grvSeat);
        txvTenPhim = (TextView) findViewById(R.id.txvThongTinPhim);
        txvTenRap = (TextView) findViewById(R.id.txvTenRap);

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
                                int ID = object.getInt("Id");
                                int Soghe = object.getInt("SoGhe");
                                String Hangghe = object.getString("HangGhe");
                                arrSeat.add(new Seat(ID, Hangghe, Soghe, R.drawable.seat_sale));

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
                int[] seats = new int[arrSeat.size()];// tạo mảng int (vì truyền giá trị id) seats = ... [khai báo số lượng phần tử]
                String[] seats2 = new String[arrSeat.size()];
                for (int i = 0; i < arrSeat.size(); i++) {
                    if (arrSeat.get(i).isSelected) {
                        seats[i] = arrSeat.get(i).getSoghe();
                        seats2[i] = arrSeat.get(i).getHangGhe();

                    } else {
                        seats[i] = -1;
                    }

                }

                Intent intent = new Intent(ChooseSeatActivity.this, FinalBookingActivity.class);
                Bundle bundle = new Bundle();

                bundle.putIntArray("IDSEAT", seats);
                bundle.putStringArray("HANG", seats2);
               bundle.putString("TENPHIM",tenphim);
                bundle.putString("IDRAP",idrap);
                bundle.putString("Startime",StartTime);
                bundle.putString("Endtime",EndTime);
                intent.putExtra("BUNDLE_IDSEAT", bundle);
                startActivity(intent);


            }
        });

    }
    //============================================================================


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

}

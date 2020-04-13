package com.example.ungdungdatvexemphim.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ungdungdatvexemphim.Adapters.SeatAdapter;
import com.example.ungdungdatvexemphim.Models.Seat;
import com.example.ungdungdatvexemphim.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChooseSeatActivity extends AppCompatActivity {


    GridView gvSeat;

    Button btnXacNhanChoose;


    ArrayList<Seat> arrSeat;
    SeatAdapter adapter;
    String urlGetSeat = "http://192.168.1.8/php_ebooking/getSeat.php";
    String urlGetSeatB="http://192.168.1.8/php_ebooking/getSeatB.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_seat);
        AnhXa();
        adapter = new SeatAdapter(this, R.layout.dong_seat, arrSeat);
        gvSeat.setAdapter(adapter);
        getDataA();
        getDataB();
        xulySuKienData();

    }


    private void AnhXa() {

        btnXacNhanChoose = (Button) findViewById(R.id.btnXacNhanBook);
        gvSeat = (GridView) findViewById(R.id.grvSeat);
        arrSeat = new ArrayList<>();


//        arrSeat.add(new Seat("1","x",R.drawable.seat_sale));
//        arrSeat.add(new Seat("2","x",R.drawable.seat_sale));
//        arrSeat.add(new Seat("3","x",R.drawable.seat_sale));
//        arrSeat.add(new Seat("4","x",R.drawable.seat_sale));
//        arrSeat.add(new Seat("5","x",R.drawable.seat_sale));
//        arrSeat.add(new Seat("6","x",R.drawable.seat_sale));
    }

    private void getDataA() {// lấy dữ liệu ghế hàng A ra hihi
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlGetSeat, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(final JSONArray response) {
                        //  Toast.makeText(Choose_Seat_Activity.this,response.toString(),Toast.LENGTH_LONG).show();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                final JSONObject object = response.getJSONObject(i);
                                int ID = object.getInt("Id");
                                int Soghe = object.getInt("SoGhe");
                                String Hangghe = object.getString("HangGhe");
                                arrSeat.add(new Seat(ID, Hangghe, Soghe, R.drawable.seat_sale));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            //==============================================================


                        }
                        //============================================================
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(jsonArrayRequest);

    }


    //========================================
    public void xulySuKienData() {// lấy và truyền dữ liệu ghế từ trong adapter hihi
        btnXacNhanChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int[] seats = new int[arrSeat.size()];// tạo mảng seats = ... [khai báo số lượng phần tử]
                String[] seats2 = new String[arrSeat.size()];
                for (int i = 0; i < arrSeat.size(); i++) {
                    if (arrSeat.get(i).isSelected) {
                        seats[i] = arrSeat.get(i).getSoghe();
                        seats2[i] = arrSeat.get(i).getHangGhe();


                    } else {
                        seats[i] = -1;
                    }

                }

                Intent intent = new Intent(ChooseSeatActivity.this, ConfirmBooking.class);
                Bundle bundle = new Bundle();

                bundle.putIntArray("IDSEAT", seats);
                bundle.putStringArray("HANG", seats2);
                intent.putExtra("BUNDLE_IDSEAT", bundle);
                startActivity(intent);


            }
        });

    }

    private void getDataB() {// lấy dữ liệu ghế hàng B ra hihi
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlGetSeatB, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(final JSONArray response) {
                        //  Toast.makeText(Choose_Seat_Activity.this,response.toString(),Toast.LENGTH_LONG).show();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                final JSONObject object = response.getJSONObject(i);
                                int ID = object.getInt("Id");
                                int Soghe = object.getInt("SoGhe");
                                String Hangghe = object.getString("HangGhe");
                                arrSeat.add(new Seat(ID, Hangghe, Soghe, R.drawable.seat_sale));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            //==============================================================


                        }
                        //============================================================
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(jsonArrayRequest);

    }

}

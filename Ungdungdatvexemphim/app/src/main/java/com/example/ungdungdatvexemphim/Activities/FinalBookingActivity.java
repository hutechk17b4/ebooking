package com.example.ungdungdatvexemphim.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
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
import com.example.ungdungdatvexemphim.Models.SessionManagement;
import com.example.ungdungdatvexemphim.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FinalBookingActivity extends AppCompatActivity {

    TextView txvseatin4,txvTenPhim,txvrap,txvNameUser,txvTime,txvmail;
    Button btnConfirm;
    ArrayList<Seat>seatarr;
    String urlpostSeatBook="http://192.168.1.7/php_ebooking/postSeatBook.php";
    String urlgetTimeShow="http://192.168.1.7/php_ebooking/getTimeShow.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_booking);
        AnhXa();
        getINFORBOOK();
        getUserBooked();
        seatarr=new ArrayList<>();


    }
    private void AnhXa()
    {
        txvseatin4=findViewById(R.id.txvSeatin4);
        txvTenPhim=findViewById(R.id.txvMovieName);
        txvrap=findViewById(R.id.txvRap);
        txvNameUser=findViewById(R.id.txvNameUser);
        txvTime=findViewById(R.id.txvTime);
        txvmail=findViewById(R.id.txvMailUser);
        btnConfirm=findViewById(R.id.btnBookFinal);
    }

    private void getINFORBOOK()
    {
        final Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("BUNDLE_IDSEAT");

        String IDrap=bundle.getString("IDRAP");
        String Tenphim=bundle.getString("TENPHIM");

        final String IDlichtrinh=bundle.getString("IDlichtrinh");
        getTimeShow(IDlichtrinh);// gọi lại hàm getTime và truyền vào IDlich trình

        txvrap.setText(IDrap);
        txvTenPhim.setText(Tenphim);



        final String[] seatCot=bundle.getStringArray("COTGHE");
        String []seatHang=bundle.getStringArray("HANGGHE");
       // int [] seatsHinh=bundle.getIntArray("HINHSEAT");
       // final int[] seats  = bundle.getIntArray("SOSEAT");


        final StringBuilder data = new StringBuilder();
        final StringBuilder data2=new StringBuilder();
        final StringBuilder data3=new StringBuilder();
        //==================================
        for(int i=0; i<seatCot.length; i++) {

            if(!seatCot[i].equals("null")  )
            {
                data.append(seatCot[i]+seatHang[i]+" ");

                // Toast.makeText(ConfirmBooking.this,data.toString(),Toast.LENGTH_SHORT).show();
                txvseatin4.setText("Ghế:"+data);


            }
            else {

            }

            //================================
        }
        //=========hết vòng for=======================
    }
//================================================================================
    private void getTimeShow(final String ID)// lấy time start và end dựa theo idlichtrinh được truyền từ chooseSeat
    {
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, urlgetTimeShow,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array=new JSONArray(response);
                            for (int i=0;i<array.length();i++)
                            {
                                JSONObject object=array.getJSONObject(i);
                                String StartTime=object.getString("Starttime");
                                String EndTime=object.getString("Endtime");
                                txvTime.setText("start:"+StartTime+" - "+"end:"+EndTime);

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
                Map<String,String> params=new HashMap<>();
                params.put("IDLichTrinhPost",ID);
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }
//==================================================================================
    private void getUserBooked()
    {
        SessionManagement sessionManagement=new SessionManagement(FinalBookingActivity.this);
        String Name= sessionManagement.getSessionUsername();
        String mail=sessionManagement.getSessionMail();
        txvNameUser.setText(Name);
        txvmail.setText(mail);
    }
    //=======================================================================
    private void postdulieubook() {
    }


}

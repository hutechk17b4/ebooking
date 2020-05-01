package com.example.ungdungdatvexemphim.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

    TextView txvseatin4,txvTenPhim,txvrap,txvNameUser,txvTime,txvmail,txvdate;
    Button btnConfirm;
    ArrayList<Seat>seatarr;
    String urlpostSeatBook="http://192.168.1.4/PHPData/postSeatBook.php";
    String urlgetTimeShow="http://192.168.1.4/PHPData/getTimeShow.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_ticket);
        AnhXa();
        getINFORBOOK();
        getUserBooked();
        seatarr = new ArrayList<>();


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
        txvdate=findViewById(R.id.txvDate);
    }

    private void getINFORBOOK()
    {
        final Intent intent=getIntent();
        final Bundle bundle=intent.getBundleExtra("BUNDLE_IDSEAT");

        final String IDrap = bundle.getString("IDRAP");
        String Tenphim = bundle.getString("TENPHIM");

        final String IDlichtrinh = bundle.getString("IDlichtrinh");
        getTimeShow(IDlichtrinh);// gọi lại hàm getTime và truyền vào IDlich trình

        txvrap.setText(IDrap);
        txvTenPhim.setText(Tenphim);



        final String[] seatCot=bundle.getStringArray("COTGHE");
        final String []seatHang=bundle.getStringArray("HANGGHE");
        final String []ID=bundle.getStringArray("ID");
        final String []SEATID=bundle.getStringArray("SEATID");
        final String []IDrappost=bundle.getStringArray("IDRAPpost");


       // int [] seatsHinh=bundle.getIntArray("HINHSEAT");
       // final int[] seats  = bundle.getIntArray("SOSEAT");


        final StringBuilder data = new StringBuilder();

        //==================================
        for(int i=0; i<seatCot.length; i++) {

            if(!seatCot[i].equals("null") && !seatHang[i].equals("null") && !ID[i].equals("null"))
            {
                data.append(seatCot[i]+seatHang[i]+" ");

                // Toast.makeText(ConfirmBooking.this,data.toString(),Toast.LENGTH_SHORT).show();
                txvseatin4.setText("Ghế:"+data);


            }
            else {


            }
            //================================
        }
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<ID.length;i++)
                {
                    if (!ID[i].equals("null"))
                    {
                        postdulieubook(seatCot[i],seatHang[i],IDlichtrinh,ID[i],SEATID[i],IDrappost[i]);
                        Toast.makeText(FinalBookingActivity.this,"Book thành công",Toast.LENGTH_LONG).show();
                        Intent intent1=new Intent(FinalBookingActivity.this,MainActivity.class);
                        startActivity(intent1);
                    }
                    else {
                        Toast.makeText(FinalBookingActivity.this,"Vui lòng chọn ghế ",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        //================================
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
                                txvTime.setText("Start:"+StartTime+"  "+"End:  "+EndTime);

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
    private void postdulieubook(final String cot, final String hang, final String idlichtrinh, final String id, final String seatID, final String idrap) {// post dữ liệu ghế đc chọn lên csdl
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, urlpostSeatBook,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params= new HashMap<>();
                params.put("seat_collumnPost",cot);
                params.put("seat_rowPost",hang);
                params.put("IDLichTrinhPost",idlichtrinh);
                params.put("IDseat_noPost",id);
                params.put("SeatIDPost",seatID);
                params.put("IDrapPost",idrap);

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


}

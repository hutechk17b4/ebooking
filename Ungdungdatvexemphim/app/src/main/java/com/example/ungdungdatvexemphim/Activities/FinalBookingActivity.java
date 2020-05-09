package com.example.ungdungdatvexemphim.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ungdungdatvexemphim.Models.IDtheloai;
import com.example.ungdungdatvexemphim.Models.Seat;
import com.example.ungdungdatvexemphim.Models.SessionManagement;
import com.example.ungdungdatvexemphim.Models.Ticket;
import com.example.ungdungdatvexemphim.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FinalBookingActivity extends AppCompatActivity {
    String tenKH="";

    TextView txvseatin4,txvTenPhim,txvrap,txvNameUser,txvTime,txvmail,txvPrice,txvdate;

    Button btnConfirm;

    ArrayList<Ticket> arrticket;
    ArrayList<Seat>seatarr;
    ArrayList<IDtheloai> arridtheloai;



    String urlpostSeatBook="http://192.168.1.53/PHP_Data/postSeatBook.php";
    String urlgetTimeShow="http://192.168.1.53/PHP_Data/getTimeShow.php";
//    String urlgetIDKH="http://192.168.1.7/php_ebooking/getIDKachHang.php";
    String urlinsertBooking="http://192.168.1.53/PHP_Data/insertBooking.php";
    String urlgetIDngay="http://192.168.1.53/PHP_Data/getIDngay.php";
    String urlgetNgayChieu="http://192.168.1.53/PHP_Data/getNgayChieu.php";
    String urlgetIDphim="http://192.168.1.53/PHP_Data/getIDphim.php";
    String urlgetIDtheloai="http://192.168.1.53/PHP_Data/getIDtheloai.php";
    String urlInsertAI="http://192.168.1.53/PHP_Data/insertDataAI.php";


    @RequiresApi(api = Build.VERSION_CODES.O)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_ticket);
        AnhXa();
        tenKH=txvNameUser.getText().toString();

        getINFORBOOK();
        getUserBooked();

        seatarr=new ArrayList<>();
        arrticket=new ArrayList<>();
        arridtheloai=new ArrayList<>();






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

       // txvPrice=findViewById(R.id.txvprice);

        txvdate=findViewById(R.id.txtDate);

    }

    private void getINFORBOOK()
    {
        final Intent intent=getIntent();
        final Bundle bundle=intent.getBundleExtra("BUNDLE_IDSEAT");


        final String IDrap=bundle.getString("IDRAP");
        final String Tenphim=bundle.getString("TENPHIM");





        final String IDlichtrinh = bundle.getString("IDlichtrinh");
        getTimeShow(IDlichtrinh);// gọi lại hàm getTime và truyền vào IDlich trình
        getIDngaychieu(IDlichtrinh);

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
        final StringBuilder data2 = new StringBuilder();

        //==============================================

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

        //================================================================================




        //===============================================================================
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                for (int i=0;i<ID.length;i++)
                {
                    if (!ID[i].equals("null"))
                    {
                        postdulieubook(seatCot[i],seatHang[i],IDlichtrinh,ID[i],SEATID[i],IDrappost[i]);
                        insertBooking(ID[i],Tenphim,IDlichtrinh);

                        Toast.makeText(FinalBookingActivity.this,"Book thành công",Toast.LENGTH_LONG).show();
                        getIDphim(Tenphim);

                       // break;
                    }
                    else {


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
    //==============================================================================
    private void getIDngaychieu(final String ID)
    {
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, urlgetIDngay,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array=new JSONArray(response);
                            for (int i=0;i<array.length();i++)
                            {
                                JSONObject object=array.getJSONObject(i);
                                String idngaychieu=object.getString("idngaychieu");
                                GetNgay(idngaychieu);
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
    //====================================================================
    private void GetNgay(final String idngaychieu)
    {
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, urlgetNgayChieu,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array=new JSONArray(response);
                            for (int i=0;i<array.length();i++)
                            {
                                JSONObject object=array.getJSONObject(i);
                                String date=object.getString("ngaychieu");
                                txvdate.setText(date);
                            }
                        } catch (JSONException e) {


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
                params.put("IDngaychieuPost",idngaychieu);
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
                        if(response.equals("success")){
                            Intent intent1=new Intent(FinalBookingActivity.this,MainActivity.class);
                            startActivity(intent1);

                        }
                        else {
                            Toast.makeText(FinalBookingActivity.this,"vui lòng chọn ghế ",Toast.LENGTH_SHORT).show();
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

    //=========================================================


    //==============================================================================

    private void insertBooking(final String idseatno, final String tenphim, final String idlichtrinh){// insert dữ liệu book theo user vào table booking
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, urlinsertBooking,
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
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params= new HashMap<>();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
               // get time now and format
                LocalDateTime now = LocalDateTime.now();
                SessionManagement sessionManagement=new SessionManagement(FinalBookingActivity.this);
                int ID= sessionManagement.getSession();
                // get ID from session and put

                params.put("IDKhachHangPost",ID+"");
                params.put("TimeCreatePost",dtf.format(now));
                params.put("IDSeat_noPost",idseatno);
                params.put("TenPhimPost",tenphim);
                params.put("IDLichTrinhPost",idlichtrinh);

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    //======================================================================

    private void getIDphim(final String tenphim)
    {
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, urlgetIDphim,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array=new JSONArray(response);
                            for (int i=0;i<array.length();i++)
                            {
                                JSONObject object=array.getJSONObject(i);
                                String IDphim=object.getString("idphim");
                                getIDtheloai(IDphim);
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
                params.put("TenPhimPost",tenphim);

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

//========================================================================
    private void getIDtheloai(final String idphim)
    {
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, urlgetIDtheloai,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array=new JSONArray(response);
                         //   Toast.makeText(FinalBookingActivity.this,array+"",Toast.LENGTH_SHORT).show();
                            for (int i=0;i<array.length();i++)
                            {
                                JSONObject object=array.getJSONObject(i);
                                String idtheloai=object.getString("idtheloai");
                               // Toast.makeText(FinalBookingActivity.this,arridtheloai.get(i).getIDTheLoai(),Toast.LENGTH_SHORT).show();
                                insertDataAI(idtheloai);
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
                params.put("IDPhimPost",idphim);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    //======================================================================

    private void insertDataAI(final String IDtheloaipost)
    {
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, urlInsertAI,
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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                SessionManagement sessionManagement=new SessionManagement(FinalBookingActivity.this);
                int ID= sessionManagement.getSession();
                params.put("IDKhachHangPost",ID+"");
                params.put("IDTheLoaiPost",IDtheloaipost);


                return params;
            }
        };
        requestQueue.add(stringRequest);
    }




}

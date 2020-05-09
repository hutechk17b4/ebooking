package com.example.ungdungdatvexemphim.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ungdungdatvexemphim.Models.SessionManagement;
import com.example.ungdungdatvexemphim.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailBookActivity extends AppCompatActivity {
    TextView txvIDbooking,tenkhachhang,time,idseatno,tenphim,timestart;

    String urlgetDetailBook="http://192.168.1.53/PHP_Data/getHistoryBookDetail.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_book);
        AnhXa();

        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("DULIEUBOOK");
        String IDbook=bundle.getString("IDBOOK");
        getDetailHistoryBook(IDbook);
//        Toast.makeText(DetailBookActivity.this,IDbook,Toast.LENGTH_SHORT).show();
    }

    private void AnhXa()
    {
        txvIDbooking=(TextView)findViewById(R.id.txvIDbooking);
        tenkhachhang=(TextView)findViewById(R.id.txvtenKhachHang);
        time=(TextView)findViewById(R.id.txvTimecreate);
        idseatno=(TextView)findViewById(R.id.txvIDseatno);
        tenphim=(TextView)findViewById(R.id.txvTenPhim);
        timestart=(TextView)findViewById(R.id.txvTimestartshow);
    }

    private void getDetailHistoryBook(final String IDbook)
    {
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, urlgetDetailBook,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array=new JSONArray(response);
                            for (int i=0;i<array.length();i++)
                            {
                                JSONObject object=array.getJSONObject(i);
                                String idbook=object.getString("idbooking");
                                String idkhachhang=object.getString("idkhachhang");
                                String timecre=object.getString("timecreate");
                                String idseat_no=object.getString("Idseat_no");
                                String ten_phim=object.getString( "tenphim");
                                String id_lichtrinh=object.getString("idlichtrinh");
                                txvIDbooking.setText(idbook);
                                tenkhachhang.setText(idkhachhang);
                                time.setText(timecre);
                                idseatno.setText(idseat_no);
                                tenphim.setText(ten_phim);
                                timestart.setText(id_lichtrinh);

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
                SessionManagement sessionManagement=new SessionManagement(DetailBookActivity.this);
                int ID= sessionManagement.getSession();
                params.put("idkhachhangpost",ID+"");
                params.put("idbookingpost",IDbook);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}

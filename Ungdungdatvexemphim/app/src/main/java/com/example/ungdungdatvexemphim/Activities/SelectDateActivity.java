package com.example.ungdungdatvexemphim.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ungdungdatvexemphim.Adapters.NgayAdapter;
import com.example.ungdungdatvexemphim.Models.Ngay;
import com.example.ungdungdatvexemphim.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SelectDateActivity extends AppCompatActivity {

    String urlgetDate="http://192.168.1.53/PHP_Data/getDate.php";
    ListView lvDate;
    ArrayList<Ngay>arrNgay;
    NgayAdapter adapter;
    String IDphim,TenPhim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date);
        lvDate=(ListView)findViewById(R.id.lvDate);
        arrNgay=new ArrayList<>();
        adapter=new NgayAdapter(this,R.layout.dong_date_lichtrinh,arrNgay);
        lvDate.setAdapter(adapter);

        //=======================
        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("DuLieuTenPhim");
        IDphim=bundle.getString("IDPHIM");
        TenPhim=bundle.getString("TENPHIM");
        //=====================
        getDate(IDphim);
    }

    private void getDate(final String IDphim)
    {
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, urlgetDate,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array=new JSONArray(response);
                            for (int i=0;i<array.length();i++)
                            {
                                JSONObject object=array.getJSONObject(i);
                                String ID=  object.getString("Id");
                                String Ngaychieu= object.getString("ngaychieu");
                                String IDphim= object.getString("Idphim");
                                Ngay ngay=new Ngay(ID,Ngaychieu,IDphim);
                                arrNgay.add(ngay);

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
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("IDphimPost",IDphim);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}

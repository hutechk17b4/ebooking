package com.example.ungdungdatvexemphim.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ungdungdatvexemphim.Adapters.LichTrinhAdapter;
import com.example.ungdungdatvexemphim.Models.LichTrinh;
import com.example.ungdungdatvexemphim.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// MÀN HÌNH CHỌN THỜI GIAN BẮT ĐẦU //
public class SelectTimeStartActivity extends AppCompatActivity {

    String urlgetLichtrinh="http://192.168.1.101/filePHP/getLichTrinh.php";

    ListView lvTime;
    ArrayList<LichTrinh> arrLichTrinh;
    LichTrinhAdapter adapter;
    String IDphim,TenPhim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_time_start);
        lvTime=(ListView)findViewById(R.id.lvLichChieu);


        arrLichTrinh=new ArrayList<>();

        adapter=new LichTrinhAdapter(this,R.layout.dong_lichtrinh,arrLichTrinh);
        lvTime.setAdapter(adapter);

        //====================== lấy tên phim từ detail truyền qua đây rồi từ đây truyền qua tiếp màn hình book để hiển thị tên phim và tên rạp
        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("DuLieuTenPhim");
        IDphim=bundle.getString("IDPHIM");
        TenPhim=bundle.getString("TENPHIM");
      // postIDphim(IDphim);
        getLichTrinh(IDphim);


    }

    private void getLichTrinh(final String ID)
    {

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, urlgetLichtrinh,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // Toast.makeText(SelectTimeStartActivity.this,response.toString(),Toast.LENGTH_LONG).show();
                        try {
                            JSONArray array=new JSONArray(response);
                            for (int i=0;i<array.length();i++)
                            {
                                JSONObject object=array.getJSONObject(i);
                                String IDLT= object.getString("Idlichtrinh");
                                String IDP= object.getString("Idphim");
                                String IDR= object.getString("Idrap");
                                String TT= object.getString("Starttime");
                                String TE= object.getString("Endtime");
                                arrLichTrinh.add(new LichTrinh(IDLT,IDP,IDR,TT,TE));

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
                        Toast.makeText(SelectTimeStartActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("IDphimPost",ID);
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }




}

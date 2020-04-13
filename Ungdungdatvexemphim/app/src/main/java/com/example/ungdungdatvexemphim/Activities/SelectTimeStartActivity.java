package com.example.ungdungdatvexemphim.Activities;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ungdungdatvexemphim.Adapters.LichTrinhAdapter;
import com.example.ungdungdatvexemphim.Models.LichTrinh;
import com.example.ungdungdatvexemphim.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SelectTimeStartActivity extends AppCompatActivity {

    String urlgetLichtrinh="http://192.168.1.8/php_ebooking/getLichTrinh.php";
    ListView lvTime;
    ArrayList<LichTrinh> arrLichTrinh;
    LichTrinhAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_time_start);
        lvTime=(ListView)findViewById(R.id.lvLichChieu);


        arrLichTrinh=new ArrayList<>();

        adapter=new LichTrinhAdapter(this,R.layout.dong_lichtrinh,arrLichTrinh);
        lvTime.setAdapter(adapter);
        getLichTrinh();

    }

    private void getLichTrinh()
    {
        final RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, urlgetLichtrinh, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i=0;i<response.length();i++)
                        {
                            try {
                                JSONObject object=response.getJSONObject(i);
                                arrLichTrinh.add(new LichTrinh(
                                        object.getString("Idlichtrinh"),
                                        object.getString("Idphim"),
                                        object.getString("Idrap"),
                                        object.getString("Starttime"),
                                        object.getString("Endtime")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SelectTimeStartActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }


}

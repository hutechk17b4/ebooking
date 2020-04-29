package com.example.ungdungdatvexemphim.Activities;

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
import com.example.ungdungdatvexemphim.Adapters.HistoryBookAdapter;
import com.example.ungdungdatvexemphim.Models.HistoryBook;
import com.example.ungdungdatvexemphim.Models.SessionManagement;
import com.example.ungdungdatvexemphim.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HistoryBookActivity extends AppCompatActivity {

    String urlgetHistoryBook="http://192.168.1.7/php_ebooking/getHistoryBook.php";
    ListView lvHistory;
    ArrayList<HistoryBook> arrhistorybook;
    HistoryBookAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_book);
        lvHistory=(ListView)findViewById(R.id.lvHistorybook);
        arrhistorybook=new ArrayList<>();
        adapter=new HistoryBookAdapter(this,R.layout.dong_history_book,arrhistorybook);
        lvHistory.setAdapter(adapter);
        getHistoryBook();
    }

    //=====================================================================

    private void getHistoryBook()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, urlgetHistoryBook,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array=new JSONArray(response);
                            for (int i=0;i<array.length();i++)
                            {
                                JSONObject object=array.getJSONObject(i);
                                String IDbook=object.getString("IDbooking");
                                String TimeCreate=object.getString("Timecreate");
                                HistoryBook historyBook=new HistoryBook(IDbook,TimeCreate);
                                arrhistorybook.add(historyBook);
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
                        Toast.makeText(HistoryBookActivity.this,"lỗi kết nối",Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                SessionManagement sessionManagement=new SessionManagement(HistoryBookActivity.this);
                int ID= sessionManagement.getSession();
                params.put("IDKhachHangPost",ID + "");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}

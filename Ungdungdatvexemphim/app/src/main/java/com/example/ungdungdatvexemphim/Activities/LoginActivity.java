package com.example.ungdungdatvexemphim.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ungdungdatvexemphim.Models.Customer;
import com.example.ungdungdatvexemphim.Models.DataAI;
import com.example.ungdungdatvexemphim.Models.SessionManagement;
import com.example.ungdungdatvexemphim.R;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

// MÀN HÌNH ĐĂNG NHẬP
public class LoginActivity extends AppCompatActivity {


// tạo đối tượng lưu trữ
    RelativeLayout relativeLayout1, relativeLayout2;
    private Button btnLogin,btnSignUp, btnForgotPass;
    private TextInputLayout textInputUserName;
    private TextInputLayout textInputPassWord;

    private static final String file_name="testai.txt";

    ArrayList<Customer> customersarr;
    ArrayList<DataAI> data_ai_arr;

    String urlLogin = "http://10.20.78.183/PHP_Data/dangnhap.php";// link lấy thông tin đăng nhập
    String urlgetDataUser="http://10.20.78.183/PHP_Data/getDataKH.php";// link lấy dữ liệu người dùng sau khi đăng nhập
    String urlgetdataAI="http://10.20.78.183/PHP_Data/getDataAI.php";


    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
//                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");
    // tạo đối tượng xử lý
    Handler handler = new Handler();
    // tạo đối tượng có thể chạy được
    Runnable runnable = new Runnable() {
        // Override phương thức chạy
        @Override
        public void run() {
            // Xử lý layout1 để có thể nhìn thấy được
            relativeLayout1.setVisibility(View.VISIBLE);
            // Xử lý layout2 để có thể nhìn thấy được
            relativeLayout2.setVisibility(View.VISIBLE);
        }
    };
//==================================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addControls();
        customersarr =new ArrayList<>();
        data_ai_arr=new ArrayList<>();


        // cho đối tượng xử lý delay 3000mllisecond
        handler.postDelayed(runnable, 2000);
        // Xử lý sự kiện cho nút đăng nhập
//
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                String userName = textInputUserName.getEditText().getText().toString().trim();
//                String passWord = textInputPassWord.getEditText().getText().toString().trim();
                if( !validateUserName() |  !validatePassWord()){

                    Toast.makeText(LoginActivity.this, "Hãy nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
	

                }
                 else{
                    SessionManagement sessionManagement=new SessionManagement(LoginActivity.this);
                    // khởi tạo lại giá trị ban đầu cho session
                    int ID = 0;
                    String TenDN="";
                    String Mail="";
                    Customer customer=new Customer(ID,TenDN,Mail);
                    //================== lưu lại giá trị ban đầu cho session
                    sessionManagement.saveSessionMail(customer);
                    sessionManagement.saveSessionUserName(customer);
                    //==== lưu lại giá trị ban đầu cho session
                  //  int userID=sessionManagement.getSession();
                    String userMail=sessionManagement.getSessionMail();
                    String userName=sessionManagement.getSessionUsername();
                    if(!userMail.equals("error") || !userName.equals("noname"))
                    {
                        // login và add lại giá trị session
                        LoginCustomer(urlLogin);
                        getdataAI();


                    }
                    else {
                        // nếu giá trị session vẫn là -1 thì lấy lại dữ liệu và save lại session
                        getDataUser();


                    }

                }
            }
        });
            // Xử lý sự kiện cho nút đăng kí (Sign Up)
             btnSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Nếu cái view == nút đăng kí (nghĩa là bấm vào nút đăng kí)
                if (v==btnSignUp){
                    // chuyển màn hình sang giao diện đăng kí
                    Intent intent   = new Intent(LoginActivity.this,RegisterActivity.class);
                    startActivity(intent);
                }
            }
             });
    }
    //===========================================================

//    @Override
//    protected void onResume() {
//        SessionManagement sessionManagement=new SessionManagement(LoginActivity.this);
////        int ID = 0;
////        String TenDN="";
////        String Mail="";
////        Customer customer=new Customer(ID,TenDN,Mail);
//        sessionManagement.removeSession();
//        sessionManagement.removeSessionMail();
//        sessionManagement.getSessionUsername();
//        super.onResume();
//    }

    //=========================================================
    // Xử lý đăng nhập
    private void LoginCustomer(String url){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")){
                           getDataUser();// lấy dữ liệu user sau khi đăng nhập và save vào session
                           // Toast.makeText(getApplicationContext(), "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));

                        }else{
                            Toast.makeText(LoginActivity.this, "Lỗi đăng nhập!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                Log.d("BBB", "onErrorResponse: "+ error.toString());
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("TenDangNhap", textInputUserName.getEditText().getText().toString().trim());
                params.put("MatKhau", textInputPassWord.getEditText().getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


    //====================================================================================
    //=======================================================================================
    // lấy dữ liệu usersau khi đăng nhập
    public void  getDataUser()
    {

        RequestQueue requestQueue=Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, urlgetDataUser,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array=new JSONArray(response);
                            for (int i=0;i<array.length();i++)
                            {
                                JSONObject object=array.getJSONObject(i);
                                int ID=object.getInt("IdKH");
                                String TenDN=object.getString("TenDN");
                                String Mail=object.getString("Mail");


                                // save dữ liệu vào session
                                Customer customer=new Customer(ID,TenDN,Mail);
                                customersarr.add(customer);
                                //Toast.makeText(LoginActivity.this,ID+""+TenDN+""+Mail,Toast.LENGTH_LONG).show();
                                 SessionManagement sessionManagement=new SessionManagement(LoginActivity.this);
                                sessionManagement.saveSession(customer);
                                sessionManagement.saveSessionMail(customer);
                                sessionManagement.saveSessionUserName(customer);

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
                params.put("TenDangNhapKH",textInputUserName.getEditText().getText().toString().trim());
                params.put("MatKhauKH",textInputPassWord.getEditText().getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }

    //======================================================================================
    // Kiểm tra UserName
    private boolean validateUserName(){
        String usernameInput = textInputUserName.getEditText().getText().toString().trim();
        if(usernameInput.isEmpty()){
            textInputUserName.setError("Field can't be empty!");
            return false;
        }else {
//            textInputUserName.setError(null);
       textInputUserName.setErrorEnabled(false);
            return true;
        }
    }
    // Kiểm tra PassWord
    private boolean validatePassWord(){
        String passwordInput = textInputPassWord.getEditText().getText().toString().trim();
        if(passwordInput.isEmpty()){
            textInputPassWord.setError("Field can't be empty!");
            return false;
        }else if(!PASSWORD_PATTERN.matcher(passwordInput).matches()){
            textInputPassWord.setError("Password too weak!");
            return false;
        }
        else
        {
          //  textInputPassWord.setError(null);
            textInputPassWord.setErrorEnabled(false);
            return true;
        }
    }
    private void addControls() {
        // Ánh xạ
        relativeLayout1 = (RelativeLayout) findViewById(R.id.relative1);
        relativeLayout2 = (RelativeLayout) findViewById(R.id.relative2);
        btnLogin = (Button) findViewById(R.id.btnlogin) ;
        btnSignUp = (Button) findViewById(R.id.btndangki);
        textInputUserName = findViewById(R.id.text_input_username);
        textInputPassWord = findViewById(R.id.text_input_password);
    }

//    public void confirmInputLogin(View view) {
//        if( !validateUserName() | !validatePassWord()){
//            return;
//        }
//        String input = "Username: " + textInputUserName.getEditText().getText().toString();
//        input += "\n";
//        input += "Password: " + textInputPassWord.getEditText().getText().toString();
//
//        Toast.makeText(this, input, Toast.LENGTH_LONG).show();
//    }
    //================================================================
    private void getdataAI()
    {
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, urlgetdataAI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array=new JSONArray(response);
                            for (int i=0;i<array.length();i++)
                            {

                                JSONObject object=array.getJSONObject(i);

                                     String idKH= object.getString("idkhachhang");
                                       String idTL= object.getString("idtheloai");
                                       String Rate= object.getString("rate");
                                       Toast.makeText(LoginActivity.this,idKH+"::"+idTL+"::"+Rate,Toast.LENGTH_SHORT).show();
                                       ghiFileAI(idKH,idTL,Rate);




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
        );
        requestQueue.add(stringRequest);


    }
    //=================================================================
    private void ghiFileAI(String iduser, String idtheloai, String rate) {
        // Get the directory for the user's public pictures directory.
        String aaa="::";
        String bbb="\n";
        FileOutputStream fos;
        try {
            fos = openFileOutput(file_name,MODE_APPEND);
            fos.write(iduser.getBytes());
            fos.write(aaa.getBytes());
            fos.write(idtheloai.getBytes());
            fos.write(aaa.getBytes());
            fos.write(rate.getBytes());
            fos.write(bbb.getBytes());


            //  fos.close();
           // Toast.makeText(LoginActivity.this,"save to"+getFilesDir()+"/"+file_name,Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}






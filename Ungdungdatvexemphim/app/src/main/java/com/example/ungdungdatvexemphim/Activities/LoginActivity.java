package com.example.ungdungdatvexemphim.Activities;

import android.content.Intent;
import android.os.Bundle;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ungdungdatvexemphim.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

// MÀN HÌNH ĐĂNG NHẬP
public class LoginActivity extends AppCompatActivity {


// tạo đối tượng lưu trữ
    RelativeLayout relativeLayout1, relativeLayout2;
    private Button btnLogin,btnSignUp, btnForgotPass;
    private TextInputLayout textInputUserName;
    private TextInputLayout textInputPassWord;
    String urlLogin = "http://192.168.1.8/film_booking/dangnhap.php";
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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addControls();

        // cho đối tượng xử lý delay 3000mllisecond
        handler.postDelayed(runnable, 3000);
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
                    LoginCustomer(urlLogin);
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
    // Xử lý đăng nhập
    private void LoginCustomer(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")){
                            Toast.makeText(getApplicationContext(), "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
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


}
    // Vì tạo Function onClick bên activity_login rồi nên không có setOnClickListener()





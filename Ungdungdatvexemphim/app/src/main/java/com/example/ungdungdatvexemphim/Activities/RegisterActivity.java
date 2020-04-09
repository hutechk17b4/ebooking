package com.example.ungdungdatvexemphim.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
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

// MÀN HÌNH ĐĂNG KÝ
public class RegisterActivity extends AppCompatActivity {
// Tạo đối tượng

    private TextInputLayout textInputUserName;
    private TextInputLayout textInputPassWord;
    private TextInputLayout textInputEmail;
    private Button btnRegister;
    String urlInsert = "http://192.168.1.5:2207/moviebooking/insertCustomer.php";
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Ánh xạ đối tượng relativeLayout
        addControls();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String userName = textInputUserName.getEditText().getText().toString().trim();
//                String passWord = textInputPassWord.getEditText().getText().toString().trim();
//                String email = textInputEmail.getEditText().getText().toString().trim();
                if(!validateUserName() | !validatePassWord() | !validateEmail()){
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else{
                    AddCustomer(urlInsert);
                }
            }
        });

    }
    // Xử lý
    private void AddCustomer(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")){
                            Toast.makeText(RegisterActivity.this, "Đăng kí thành công!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                        }else{
                            Toast.makeText(RegisterActivity.this, "Lỗi đăng kí!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this, "Xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                Log.d("AAA", "Lỗi:\n" + error.toString());
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username",textInputUserName.getEditText().getText().toString().trim());
                params.put("password",textInputPassWord.getEditText().getText().toString().trim());
                params.put("email",textInputEmail.getEditText().getText().toString().trim());

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    // Ánh xạ
    private void addControls() {
        textInputEmail = findViewById(R.id.textInput_Email);
        textInputUserName = findViewById(R.id.textInput_UserName);
        textInputPassWord = findViewById(R.id.textInput_Password);
        btnRegister = findViewById(R.id.buttonRegister);
    }
    // Kiểm tra User Name
    private boolean validateUserName(){
        String usernameInput = textInputUserName.getEditText().getText().toString().trim();
        if(usernameInput.isEmpty()){
            textInputUserName.setError("Field can't be empty!");
            return false;
        }else if (usernameInput.length() > 15) {
            textInputUserName.setError("Username too long!");
            return false;
        }else {
            textInputUserName.setError(null);
            return  true;
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
            //textInputPassWord.setError(null);
           textInputPassWord.setErrorEnabled(false);
            return true;
        }
    }
    // Kiểm tra Email
    private boolean validateEmail() {
        String emailInput = textInputEmail.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()) {
            textInputEmail.setError("Field can't be empty!");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            textInputEmail.setError("Please enter a valid email!");
            return false;
        }
        else
         {
           // textInputEmail.setError(null);
        textInputEmail.setErrorEnabled(false);
            return true;
        }
    }
}






//    public void confirmInputRegister(View view) {
//        if(!validateEmail() | !validateUserName() | !validatePassWord()){
//            return;
//        }
//        String input = "Email: " + textInputEmail.getEditText().getText().toString();
//        input += "\n";
//        input += "Username: " + textInputUserName.getEditText().getText().toString();
//        input += "\n";
//        input += "Password: " + textInputPassWord.getEditText().getText().toString();
//
//     Toast.makeText(this, input, Toast.LENGTH_LONG).show();
//    }



//        String emailInput = textInputEmail.getEditText().getText().toString().trim();
//        String usernameInput = textInputUserName.getEditText().getText().toString().trim();
//        String passwordInput = textInputPassWord.getEditText().getText().toString().trim();
//
//        if (emailInput.isEmpty()) {
//            textInputEmail.setError("Email is required!");
//            textInputEmail.requestFocus();
//            return;
//        }
//        if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
//            textInputUserName.setError("Enter a valid email!");
//            textInputEmail.requestFocus();
//            return;
//        }
//        if (usernameInput.isEmpty()) {
//            textInputEmail.setError("Username is required!");
//            textInputEmail.requestFocus();
//            return;




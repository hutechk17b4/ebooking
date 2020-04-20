package com.example.ungdungdatvexemphim.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ungdungdatvexemphim.R;

public class AccountActivity extends AppCompatActivity {
    Button btnLogout;
    TextView textViewAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        AnhXa();
        event_logout();

    }

    private void AnhXa()
    {
        textViewAccount = findViewById(R.id.text_view_account);
        btnLogout=findViewById(R.id.btnLogOut);
    }
    private void event_logout()
    {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(AccountActivity.this,LoginActivity.class);
//                startActivity(intent);
//                SessionManagement sessionManagement=new SessionManagement(AccountActivity.this);
//                sessionManagement.removeSession();
//                int ID=sessionManagement.getSession();
//                Toast.makeText(AccountActivity.this,ID+"",Toast.LENGTH_LONG).show();
            }
        });
    }
}

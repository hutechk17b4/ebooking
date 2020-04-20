package com.example.ungdungdatvexemphim.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ungdungdatvexemphim.Models.SessionManagement;
import com.example.ungdungdatvexemphim.R;

public class AccountActivity extends AppCompatActivity {
    Button btnlogout;
    TextView txvname,txvmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        AnhXa();
        getNameUser_MailUser();
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event_logout();
            }
        });



    }

    private void AnhXa()
    {
        txvname=(TextView)findViewById(R.id.tenUser);
        txvmail=(TextView)findViewById(R.id.mailUser);
        btnlogout=(Button)findViewById(R.id.btnLog_out);
    }
    private void event_logout()
    {
        SessionManagement sessionManagement=new SessionManagement(AccountActivity.this);
        sessionManagement.removeSessionMail();
        sessionManagement.removeSessionUsername();
        Intent intent=new Intent(AccountActivity.this,LoginActivity.class);
        startActivity(intent);

      //  int ID=sessionManagement.getSession();
        String name=sessionManagement.getSessionUsername();
        String mail=sessionManagement.getSessionMail();
        Toast.makeText(AccountActivity.this,mail+" "+name,Toast.LENGTH_LONG).show();

    }

    private void getNameUser_MailUser()
    {
        SessionManagement sessionManagement=new SessionManagement(AccountActivity.this);
        String username= sessionManagement.getSessionUsername();
        String mailuser=sessionManagement.getSessionMail();
        txvname.setText(username);
        txvmail.setText(mailuser);
    }
}

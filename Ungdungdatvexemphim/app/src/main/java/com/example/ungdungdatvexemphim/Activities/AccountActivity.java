package com.example.ungdungdatvexemphim.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
    TextView txvname,txvmail,txvHistoryBook;
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
        //====================================
        clickHistory();

    }
    private void AnhXa()
    {
        txvname=(TextView)findViewById(R.id.tenUser);
        txvmail=(TextView)findViewById(R.id.mailUser);
        btnlogout=(Button)findViewById(R.id.btnLog_out);
        txvHistoryBook=(TextView)findViewById(R.id.txvHistory);
    }
    private void event_logout()
    {
        AlertDialog.Builder alertdialog=new AlertDialog.Builder(this);
        alertdialog.setTitle("LOGOUT!");
        alertdialog.setIcon(R.drawable.ic_exit_to_app_black_24dp);
        alertdialog.setMessage("Are you sure for logout this app ?");
        alertdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SessionManagement sessionManagement=new SessionManagement(AccountActivity.this);
                sessionManagement.removeSessionMail();
                sessionManagement.removeSessionUsername();
                sessionManagement.removeSession();
                Intent intent=new Intent(AccountActivity.this,LoginActivity.class);
                startActivity(intent);

                //  int ID=sessionManagement.getSession();
                int ID=sessionManagement.getSession();
                String name=sessionManagement.getSessionUsername();
                String mail=sessionManagement.getSessionMail();
                Toast.makeText(AccountActivity.this,mail+" "+name+""+ID,Toast.LENGTH_LONG).show();

            }
        });
        alertdialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertdialog.show();
    }

    private void getNameUser_MailUser()
    {
        SessionManagement sessionManagement=new SessionManagement(AccountActivity.this);
        String username= sessionManagement.getSessionUsername();
        String mailuser=sessionManagement.getSessionMail();
        txvname.setText(username);
        txvmail.setText(mailuser);
    }
    private void clickHistory()
    {
        txvHistoryBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AccountActivity.this,HistoryBookActivity.class);
                startActivity(intent);
            }
        });
    }
}

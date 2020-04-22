package com.example.ungdungdatvexemphim.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ungdungdatvexemphim.Models.Seat;
import com.example.ungdungdatvexemphim.Models.SessionManagement;
import com.example.ungdungdatvexemphim.R;

import java.util.ArrayList;

public class FinalBookingActivity extends AppCompatActivity {

    TextView txvseatin4,txvTenPhim,txvrap,txvNameUser,txvTime,txvmail;
    Button btnConfirm;
    ArrayList<Seat>seatarr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_booking);
        AnhXa();
        getINFORBOOK();
        getUserBooked();
        seatarr=new ArrayList<>();
        sukienbooked();
    }
    private void AnhXa()
    {
        txvseatin4=findViewById(R.id.txvSeatin4);
        txvTenPhim=findViewById(R.id.txvMovieName);
        txvrap=findViewById(R.id.txvRap);
        txvNameUser=findViewById(R.id.txvNameUser);
        txvTime=findViewById(R.id.txvTime);
        txvmail=findViewById(R.id.txvMailUser);
        btnConfirm=findViewById(R.id.btnBookFinal);
    }

    private void getINFORBOOK()
    {
        final Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("BUNDLE_IDSEAT");

        String IDrap=bundle.getString("IDRAP");
        String tenphim=bundle.getString("TENPHIM");
        final String startTime=bundle.getString("Startime");
        String endTime=bundle.getString("Endtime");
        String IDlichtrinh=bundle.getString("IDlichtrinh");

        txvrap.setText(IDrap);
        txvTenPhim.setText(tenphim);
        txvTime.setText("start:"+startTime+" - "+"end:"+endTime);
        Toast.makeText(FinalBookingActivity.this,IDlichtrinh,Toast.LENGTH_SHORT).show();


        final int [] seatsID=bundle.getIntArray("IDSEAT");
        int [] seatsHinh=bundle.getIntArray("HINHSEAT");
        final int[] seats  = bundle.getIntArray("SOSEAT");
        String []seats2=bundle.getStringArray("HANG");
        final StringBuilder data = new StringBuilder();
        //==================================
        for(int i=0; i<seats.length; i++) {

            if(seats[i] != -1 )
            {
                data.append(seats[i]+seats2[i]+" ");
                // Toast.makeText(ConfirmBooking.this,data.toString(),Toast.LENGTH_SHORT).show();
                txvseatin4.setText("Ghế:"+data);

            }
            else {

            }


//            final Seat seat=new Seat(seatsID[i],seats2[i],seats[i],seatsHinh[i]);
//            if(seat.isBooked)
//            {
//                seat.getHinh();
//            }


        }
        //================================
    }
//==================================================================================
    private void getUserBooked()
    {
        SessionManagement sessionManagement=new SessionManagement(FinalBookingActivity.this);
        String Name= sessionManagement.getSessionUsername();
        String mail=sessionManagement.getSessionMail();
        txvNameUser.setText(Name);
        txvmail.setText(mail);
    }
    //=======================================================================
    private void sukienbooked()
    {

    }
}

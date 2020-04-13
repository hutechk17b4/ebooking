package com.example.ungdungdatvexemphim.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ungdungdatvexemphim.Models.Seat;
import com.example.ungdungdatvexemphim.R;

import java.util.ArrayList;

public class ConfirmBooking extends AppCompatActivity {

    ArrayList<Seat> seats;
    TextView txvseatin4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_booking);
        txvseatin4=findViewById(R.id.txvSeatin4);

        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("BUNDLE_IDSEAT");

        int[] seats  = bundle.getIntArray("IDSEAT");
        String []seats2=bundle.getStringArray("HANG");
        StringBuilder data = new StringBuilder();
        for(int i=0; i<seats.length; i++) {

            if(seats[i] != -1 )
            {
                data.append(seats[i]+seats2[i]+" ");
                // Toast.makeText(ConfirmBooking.this,data.toString(),Toast.LENGTH_SHORT).show();
                txvseatin4.setText(data);
            }
            else {

            }
//            for (int j=0;j<seats2.length;j++)
//            {
//                data.append(seats2[j]+"");
//            }

        }
    }
}

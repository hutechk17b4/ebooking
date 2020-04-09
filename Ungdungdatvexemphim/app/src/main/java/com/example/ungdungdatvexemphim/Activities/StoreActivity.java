package com.example.ungdungdatvexemphim.Activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ungdungdatvexemphim.R;

public class StoreActivity extends AppCompatActivity {

    TextView textViewStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        textViewStore = findViewById(R.id.text_view_store) ;
    }
}

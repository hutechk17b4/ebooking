package com.example.ungdungdatvexemphim.Activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ungdungdatvexemphim.R;

public class HomePageActivity extends AppCompatActivity {

    TextView textViewHomePage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        textViewHomePage = findViewById(R.id.text_view_home_page);
    }
}

package com.example.ungdungdatvexemphim.Activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ungdungdatvexemphim.R;

public class NewsActivity extends AppCompatActivity {

    TextView textViewNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        textViewNews = findViewById(R.id.text_view_news);
    }
}

package com.example.ungdungdatvexemphim.Activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ungdungdatvexemphim.R;

public class FindActivity extends AppCompatActivity {

    TextView textViewFind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        textViewFind = findViewById(R.id.text_view_find);
    }
}

package com.kunai.otobusbiletall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class HakkindaActivity extends AppCompatActivity {
    ImageView backHk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hakkinda);

        backHk = findViewById(R.id.backHk);
        backHk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HakkindaActivity.this,AnasayfaActivity.class);
                startActivity(intent);
            }
        });
    }
}
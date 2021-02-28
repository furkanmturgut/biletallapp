package com.kunai.otobusbiletall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

public class AyarlarActivity extends AppCompatActivity {
    Switch switcBildirim;
    ImageView backAyarlar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayarlar);

        switcBildirim = findViewById(R.id.switchBildirim);
        backAyarlar = findViewById(R.id.backAyarlar);

        backAyarlar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AyarlarActivity.this,AnasayfaActivity.class);
                startActivity(intent);
            }
        });

        switcBildirim.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //Push Notification's


            }
        });


    }
}
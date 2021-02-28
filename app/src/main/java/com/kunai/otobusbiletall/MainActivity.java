package com.kunai.otobusbiletall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser != null){

            Intent intent = new Intent(MainActivity.this,AnasayfaActivity.class);
            startActivity(intent);

        }
    }

    public void girisYap(View view){
        Intent intent = new Intent(MainActivity.this,GirisYapActivity.class);
        startActivity(intent);
    }

    public void kayitOl(View view){
        Intent intent = new Intent(MainActivity.this,KaydolActivity.class);
        startActivity(intent);
    }

}
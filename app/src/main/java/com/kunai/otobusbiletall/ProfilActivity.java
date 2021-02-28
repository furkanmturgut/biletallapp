package com.kunai.otobusbiletall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfilActivity extends AppCompatActivity {

    ImageView backProfil;
    TextView textKartNumarasi,textAdSoyad,textMail;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);


        backProfil = findViewById(R.id.backProfil);
        backProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfilActivity.this,AnasayfaActivity.class);
                startActivity(intent);
            }
        });

        init();
        profilVeriCekme();

    }

    private void init() {
        dialog = new Dialog(this);
        backProfil = findViewById(R.id.backProfil);
        textAdSoyad = findViewById(R.id.textAdSoyad);
        textKartNumarasi = findViewById(R.id.textKartNumarasi);
        textMail = findViewById(R.id.textMail);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

    }

    public void profilVeriCekme(){
        dialog.setContentView(R.layout.loading_layout);
        dialog.setTitle("Yükleniyor...");
        dialog.show();

        String UID = firebaseAuth.getUid();

        firebaseFirestore.collection("Kullanici Bilet").document(UID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                String adSoyad = task.getResult().getString("isim");
                String kartNo = task.getResult().getString("kartNumarasi");
                String mail = task.getResult().getString("mail");
                textAdSoyad.setText(adSoyad);
                textKartNumarasi.setText(kartNo);
                textMail.setText(mail);

                dialog.dismiss();
            }
           });

    }
}
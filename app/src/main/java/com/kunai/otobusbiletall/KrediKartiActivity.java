package com.kunai.otobusbiletall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Contacts;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class KrediKartiActivity extends AppCompatActivity {
    EditText kartNumarasi,kartTarihi,cvcNumarasi;
    Button kartDevam;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kredi_karti);

        init();
        kartDevam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String UID = firebaseAuth.getUid();
                String kartNo = kartNumarasi.getText().toString();
                String kartDate = kartTarihi.getText().toString();
                String cvcNo = cvcNumarasi.getText().toString();

                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("kartNumarasi",kartNo);
                hashMap.put("kartTarih",kartDate);
                hashMap.put("cvcNumarasi",cvcNo);
                hashMap.put("ID",UID);

                DocumentReference documentReference = firebaseFirestore.collection("Kart Bilgileri").document(UID);
                documentReference.update(hashMap);

                DocumentReference documentReferences = firebaseFirestore.collection("Kullanici Bilet").document(UID);
                documentReferences.update(hashMap);

                Intent intent = new Intent(KrediKartiActivity.this,AnasayfaActivity.class);
                startActivity(intent);


            }
        });

    }

    private void init() {
        kartDevam = findViewById(R.id.kartDevam);
        kartNumarasi = findViewById(R.id.kartNumarasi);
        kartTarihi = findViewById(R.id.kartTarihi);
        cvcNumarasi = findViewById(R.id.cvcNumarasi);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }
}
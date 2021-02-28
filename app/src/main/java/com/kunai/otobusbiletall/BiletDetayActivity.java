package com.kunai.otobusbiletall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class BiletDetayActivity extends AppCompatActivity {
    Button iletisimBtn;
    EditText zorunluYas,zorunluMail,zorunluHES,zorunluTelefon;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bilet_detay);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        init();
        iletisimBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = zorunluMail.getText().toString();
                String telefon = zorunluTelefon.getText().toString();
                String yas = zorunluYas.getText().toString();
                String hes = zorunluHES.getText().toString();

                HashMap<String, Object> hashMap = new HashMap<>();
                String UID = firebaseAuth.getUid();
                hashMap.put("mail",mail);
                hashMap.put("telefon",telefon);
                hashMap.put("yas",yas);
                hashMap.put("hes",hes);

                DocumentReference documentReference = firebaseFirestore.collection("Kullanici Bilet").document(UID);
                documentReference.update(hashMap);
                //Toast.makeText(BiletDetayActivity.this, "Success", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(BiletDetayActivity.this,KrediKartiActivity.class);
                startActivity(intent);



            }
        });
    }

    private void init() {
        iletisimBtn = findViewById(R.id.iletisimBtn);
        zorunluHES = findViewById(R.id.zorunluHES);
        zorunluMail = findViewById(R.id.zorunluEmail);
        zorunluTelefon = findViewById(R.id.zorunluTelefon);
        zorunluYas = findViewById(R.id.zorunluYas);
    }
}
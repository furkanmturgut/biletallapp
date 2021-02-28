package com.kunai.otobusbiletall;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

public class BiletlerimActivity extends AppCompatActivity {
    BiletlerimRecyclerViewaAdapter biletlerimRecyclerViewaAdapter;
    ArrayList<String> seyahatBilgiList;
    ArrayList<String> tarihBilgiList;
    ArrayList<String> saatBilgiList;
    ArrayList<String> fiyatBilgiList;
    ImageView backBilet;
    RecyclerView biletlerimRecyclerView;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biletlerim);

        backBilet = findViewById(R.id.backBilet);
        backBilet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BiletlerimActivity.this,AnasayfaActivity.class);
                startActivity(intent);
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        seyahatBilgiList = new ArrayList<>();
        tarihBilgiList = new ArrayList<>();
        saatBilgiList = new ArrayList<>();
        fiyatBilgiList = new ArrayList<>();
        profilBilgiCekme();
        biletlerimRecyclerView = findViewById(R.id.biletlerimRecyclerView);
        biletlerimRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        biletlerimRecyclerViewaAdapter = new BiletlerimRecyclerViewaAdapter(seyahatBilgiList,tarihBilgiList,saatBilgiList,fiyatBilgiList);
        biletlerimRecyclerView.setAdapter(biletlerimRecyclerViewaAdapter);


    }

    public void profilBilgiCekme(){
    //Colleciton Reference's
        String UID = firebaseAuth.getUid();

        DocumentReference documentReference = firebaseFirestore.collection("Kullanici Bilet").document(UID);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                String seyahat  = task.getResult().getString("seferler");
                String saat = task.getResult().getString("saat");
                String fiyat = task.getResult().getString("fiyat");
                String mail = task.getResult().getString("mail");

                seyahatBilgiList.add(seyahat);
                saatBilgiList.add(saat);
                fiyatBilgiList.add(fiyat);
                tarihBilgiList.add(mail);

                biletlerimRecyclerViewaAdapter.notifyDataSetChanged();
            }
        });



    }

}
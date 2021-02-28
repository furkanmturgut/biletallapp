package com.kunai.otobusbiletall;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Contacts;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BiletListeleActivity extends AppCompatActivity {
    ArrayList<String> biletler;
    ArrayList<String> tarihler;
    ArrayList<String> saatler;
    ArrayList<String> fiyatlar;
    FirebaseFirestore firebaseFirestore;

    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerView recyclerView;
    FirebaseAuth firebaseAuth;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bilet_listele);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        dialog = new Dialog(this);

        biletler = new ArrayList<>();
        tarihler = new ArrayList<>();
        saatler = new ArrayList<>();
        fiyatlar = new ArrayList<>();


        biletQuery();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerViewAdapter = new RecyclerViewAdapter(biletler,tarihler,fiyatlar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerViewAdapter);

    }

    private void biletQuery() {
        dialog.setContentView(R.layout.loading_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();
        String UID = firebaseAuth.getUid();

     CollectionReference collectionReference = firebaseFirestore.collection("Kullanici Aramasi");

                collectionReference.whereEqualTo("id", UID).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value != null) {
                            for (final DocumentSnapshot documentSnapshot : value.getDocuments()) {
                                Map<String, Object> data = documentSnapshot.getData();

                                final String comment = (String) data.get("seferler");


                                firebaseFirestore.collection("Gun Seferleri")
                                        .whereEqualTo("seferler", comment)
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                        Toast.makeText(BiletListeleActivity.this, "Sefer : " + document.getData(), Toast.LENGTH_SHORT).show();
                                                        String iller = (String) document.get("seferler");
                                                        String saat = (String) document.get("saat");
                                                        String buy = (String) document.get("fiyat");

                                                        HashMap<String , Object> hashMap = new HashMap<>();
                                                        hashMap.put("saat",saat);
                                                        hashMap.put("fiyat",buy);

                                                        DocumentReference documentReference = firebaseFirestore.collection("Kullanici Bilet").document(firebaseAuth.getUid());
                                                        documentReference.update(hashMap);




                                                        biletler.add(iller);
                                                        tarihler.add(saat);
                                                        fiyatlar.add(buy);

                                                        recyclerViewAdapter.notifyDataSetChanged();

                                                        dialog.dismiss();
                                                    }
                                                } else {
                                                    Toast.makeText(BiletListeleActivity.this, "Değer dönmedi", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                            }
                        }

                    }
                });

            }

}
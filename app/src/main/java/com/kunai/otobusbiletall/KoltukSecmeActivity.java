package com.kunai.otobusbiletall;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;


public class KoltukSecmeActivity extends AppCompatActivity {
   GridLayout mainGrid;
    TextView totalPrice;
    TextView totalBookedSeats;
    int totalSeats = 0;
    FirebaseFirestore firebaseFirestore;
    Button btnBook;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_koltuk_secme);

        mainGrid = (GridLayout) findViewById(R.id.mainGrid);
        totalBookedSeats = (TextView) findViewById(R.id.total_seats);
        totalPrice = (TextView) findViewById(R.id.total_cost);
        firebaseFirestore = FirebaseFirestore.getInstance();
        btnBook = findViewById(R.id.btnBook);
        firebaseAuth = FirebaseAuth.getInstance();

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KoltukSecmeActivity.this,BiletDetayActivity.class);
                startActivity(intent);
            }
        });


        setToggleEvent(mainGrid);



    }

    private void setToggleEvent(GridLayout mainGrid) {

        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView
            final CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cardView.getCardBackgroundColor().getDefaultColor() == -1) {
                        ++totalSeats;
                        String totaller = String.valueOf(totalSeats);
                        HashMap<String,Object> hashMap = new HashMap<>();
                        String ID  = firebaseAuth.getUid();
                        hashMap.put("toplamBilet",totaller);
                        DocumentReference documentReference = firebaseFirestore.collection("Kullanici Bilet").document(ID);
                        documentReference.update(hashMap);

                        //Change background color
                        cardView.setCardBackgroundColor(Color.parseColor("#00FF00"));

                        Toast.makeText(KoltukSecmeActivity.this, "Seçilen koltuk numarası :" + (finalI + 1), Toast.LENGTH_SHORT).show();

                    } else {
                        //Change background color
                        --totalSeats;
                        String totaller = String.valueOf(totalSeats);
                        HashMap<String,Object> hashMap = new HashMap<>();
                        String ID  = firebaseAuth.getUid();
                        hashMap.put("toplamBilet",totaller);
                        DocumentReference documentReference = firebaseFirestore.collection("Kullanici Bilet").document(ID);
                        documentReference.set(hashMap);



                        cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));

                        Toast.makeText(KoltukSecmeActivity.this, "Kaldırılan koltuk numarası :" + (finalI + 1), Toast.LENGTH_SHORT).show();
                    }
                    totalBookedSeats.setText("" + totalSeats);

                }
            });
        }
    }


    }

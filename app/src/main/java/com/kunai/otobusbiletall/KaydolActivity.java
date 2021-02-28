package com.kunai.otobusbiletall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KaydolActivity extends AppCompatActivity {
    EditText editTextFullname,editTextMail,editTextPassword;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    Boolean mailkontrol;
    TextView textGirisYapYolla;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaydol);



        init();
        textGirisYapYolla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KaydolActivity.this,GirisYapActivity.class);
                startActivity(intent);
            }
        });
    }

    public void init(){
        editTextFullname = findViewById(R.id.editTextFullname);
        editTextMail = findViewById(R.id.editTextMail);
        editTextPassword = findViewById(R.id.editTextPassword);
        textGirisYapYolla = findViewById(R.id.textGirisYapYolla);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

    }

    public void kullaniciKayitOl(View view){
        final String fullName = editTextFullname.getText().toString();
        final String mail = editTextMail.getText().toString();
        String password = editTextPassword.getText().toString();
        mailkontrol = mail.contains("@");
        mailkontrol = mail.contains(".com");

        if (TextUtils.isEmpty(fullName)|| TextUtils.isEmpty(mail) || TextUtils.isEmpty(password)){
            Snackbar snackbar = Snackbar.make(view,"Tüm alanı doldurunuz", BaseTransientBottomBar.LENGTH_LONG);
            snackbar.show();
        }else  if(password.length()<6){
            Snackbar snackbar = Snackbar.make(view,"En az 6 karakter giriniz", BaseTransientBottomBar.LENGTH_LONG);
            snackbar.show();
        }else if (mailkontrol != true){
            Snackbar snackbar = Snackbar.make(view,"Geçerli mail adresi giriniz", BaseTransientBottomBar.LENGTH_LONG);
            snackbar.show();
        }else {
            firebaseAuth.createUserWithEmailAndPassword(mail,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    HashMap<String,Object> hashMap = new HashMap<>();
                    String ID  = firebaseAuth.getUid();
                    hashMap.put("fullName",fullName);
                    hashMap.put("mail",mail);
                    hashMap.put("id",ID);

                    HashMap<String, Object> name = new HashMap<>();
                    name.put("isim",fullName);

                    DocumentReference documentReference = firebaseFirestore.collection("Kullanici").document(ID);
                    documentReference.set(hashMap);

                    DocumentReference documentReferences = firebaseFirestore.collection("Kullanici Bilet").document(ID);
                    documentReferences.set(name);


                    Intent intent = new Intent(KaydolActivity.this,AnasayfaActivity.class);
                    startActivity(intent);
                    finish();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(KaydolActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }



}
package com.kunai.otobusbiletall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

public class GirisYapActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    EditText editTextMailGiris,editTextPassGiris;
    Button girisYap;
    TextView textUyeOl;
    Boolean mailkontrol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris_yap);



        init();
        textUyeOl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GirisYapActivity.this,KaydolActivity.class);
                startActivity(intent);
            }
        });


    }

    private void init() {
        firebaseAuth = FirebaseAuth.getInstance();
        editTextMailGiris = findViewById(R.id.editTextMailGiris);
        editTextPassGiris = findViewById(R.id.editTextPasswordGiris);

        girisYap = findViewById(R.id.girisYap);
        textUyeOl = findViewById(R.id.textUyeOl);


    }

    public void userLogin(View view){
        String mail = editTextMailGiris.getText().toString();
        String password = editTextPassGiris.getText().toString();
        mailkontrol = mail.contains("@");
        mailkontrol = mail.contains(".com");

        if ( TextUtils.isEmpty(mail) || TextUtils.isEmpty(password)){
            Snackbar snackbar = Snackbar.make(view,"Tüm alanı doldurunuz", BaseTransientBottomBar.LENGTH_LONG);
            snackbar.show();
        }else  if(password.length()<6){
            Snackbar snackbar = Snackbar.make(view,"En az 6 karakter giriniz", BaseTransientBottomBar.LENGTH_LONG);
            snackbar.show();
        }else if (mailkontrol != true){
            Snackbar snackbar = Snackbar.make(view,"Geçerli mail adresi giriniz", BaseTransientBottomBar.LENGTH_LONG);
            snackbar.show();
        }else{
            firebaseAuth.signInWithEmailAndPassword(mail,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Intent intent = new Intent(GirisYapActivity.this,AnasayfaActivity.class);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(GirisYapActivity.this, "İşlemleri kontrol ediniz.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
package com.kunai.otobusbiletall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;

public class AnasayfaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;
    Spinner spinnerFrom,spinnerTo;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    TextView sonuc,tarihSec;
    String tarih,aramaSonuc;
    LinearLayout deneme;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    String[] nereden ={"Adana", "Adıyaman", "Afyon", "Ağrı", "Amasya", "Ankara", "Antalya", "Artvin", "Aydın", "Balıkesir", "Bilecik", "Bingöl", "Bitlis", "Bolu", "Burdur", "Bursa", "Çanakkale", "Çankırı", "Çorum", "Denizli", "Diyarbakır", "Edirne", "Elazığ", "Erzincan", "Erzurum", "Eskişehir", "Gaziantep", "Giresun", "Gümüşhane", "Hakkari", "Hatay", "Isparta", "İçel (Mersin)", "İstanbul", "İzmir", "Kars", "Kastamonu", "Kayseri", "Kırklareli", "Kırşehir", "Kocaeli", "Konya", "Kütahya", "Malatya", "Manisa", "Kahramanmaraş", "Mardin", "Muğla", "Muş", "Nevşehir", "Niğde", "Ordu", "Rize", "Sakarya", "Samsun", "Siirt", "Sinop", "Sivas", "Tekirdağ", "Tokat", "Trabzon", "Tunceli", "Şanlıurfa", "Uşak", "Van", "Yozgat", "Zonguldak", "Aksaray", "Bayburt", "Karaman", "Kırıkkale", "Batman", "Şırnak", "Bartın", "Ardahan", "Iğdır", "Yalova", "Karabük", "Kilis", "Osmaniye", "Düzce"};
    String[] nereye = {"Adana", "Adıyaman", "Afyon", "Ağrı", "Amasya", "Ankara", "Antalya", "Artvin", "Aydın", "Balıkesir", "Bilecik", "Bingöl", "Bitlis", "Bolu", "Burdur", "Bursa", "Çanakkale", "Çankırı", "Çorum", "Denizli", "Diyarbakır", "Edirne", "Elazığ", "Erzincan", "Erzurum", "Eskişehir", "Gaziantep", "Giresun", "Gümüşhane", "Hakkari", "Hatay", "Isparta", "İçel (Mersin)", "İstanbul", "İzmir", "Kars", "Kastamonu", "Kayseri", "Kırklareli", "Kırşehir", "Kocaeli", "Konya", "Kütahya", "Malatya", "Manisa", "Kahramanmaraş", "Mardin", "Muğla", "Muş", "Nevşehir", "Niğde", "Ordu", "Rize", "Sakarya", "Samsun", "Siirt", "Sinop", "Sivas", "Tekirdağ", "Tokat", "Trabzon", "Tunceli", "Şanlıurfa", "Uşak", "Van", "Yozgat", "Zonguldak", "Aksaray", "Bayburt", "Karaman", "Kırıkkale", "Batman", "Şırnak", "Bartın", "Ardahan", "Iğdır", "Yalova", "Karabük", "Kilis", "Osmaniye", "Düzce"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anasayfa);

        //FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();


        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(AnasayfaActivity.this,drawer,toolbar,0,0);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.inflateHeaderView(R.layout.drawer_baslik);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        tarihSec = findViewById(R.id.tarihSec);

        tarihSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int gun = calendar.get(Calendar.DAY_OF_MONTH);
                int ay = calendar.get(Calendar.MONTH);
                int yil = calendar.get(Calendar.YEAR);

                DatePickerDialog datePicker = new DatePickerDialog(AnasayfaActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int yil, int ay, int gun) {
                        tarih = gun + "/" + (ay + 1) + "/" + yil;
                        tarihSec.setText(tarih);

                    }
                }, yil, ay, gun);
                datePicker.show();

            }
        });







        //Spinner İşlem
        sonuc = findViewById(R.id.sonuc);
        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        ArrayAdapter arrayAdapters = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,nereye);
        spinnerTo.setAdapter(arrayAdapters);
        spinnerTo.setOnItemSelectedListener(this);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,nereden);
        spinnerFrom.setAdapter(arrayAdapter);
        spinnerFrom.setOnItemSelectedListener(this);

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        sonuc.setVisibility(View.INVISIBLE);
        sonuc.setText(spinnerTo.getSelectedItem().toString()+"-"+spinnerFrom.getSelectedItem().toString());
        aramaSonuc = sonuc.getText().toString();
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void buttonBiletAra(View view){

        String ID = firebaseAuth.getUid();
        String fiyat = "100TL";
        HashMap<Object,Object> hashMap = new HashMap<>();
        hashMap.put("seferler",aramaSonuc);
        hashMap.put("tarih",tarih);
        hashMap.put("id",ID);
        hashMap.put("fiyat",fiyat);

        HashMap<String,Object> arama = new HashMap<>();
        arama.put("seferler",aramaSonuc);



        DocumentReference documentReference = firebaseFirestore.collection("Kullanici Aramasi").document(ID);
        documentReference.set(hashMap);

        DocumentReference documentReferences = firebaseFirestore.collection("Kullanici Bilet").document(ID);
        documentReferences.update(arama);


        Intent intent = new Intent(AnasayfaActivity.this,BiletListeleActivity.class);
        startActivity(intent);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case  R.id.profil:
                Intent intentProfil = new Intent(AnasayfaActivity.this,ProfilActivity.class);
                startActivity(intentProfil);
                break;

            case R.id.biletlerim:
                Intent intentBilet = new Intent(AnasayfaActivity.this,BiletlerimActivity.class);
                startActivity(intentBilet);
                break;

            case R.id.hakkinda:
                Intent intentHk = new Intent(AnasayfaActivity.this,HakkindaActivity.class);
                startActivity(intentHk);
                break;

            case R.id.ayarlar:
                Intent intentAyarlar = new Intent(AnasayfaActivity.this,AyarlarActivity.class);
                startActivity(intentAyarlar);

            case R.id.cikis:
                firebaseAuth.signOut();
                Intent intent = new Intent(AnasayfaActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
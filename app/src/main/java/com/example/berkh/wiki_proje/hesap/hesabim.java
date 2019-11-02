package com.example.berkh.wiki_proje.hesap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.berkh.wiki_proje.Kategoriler.MainActivity;
import com.example.berkh.wiki_proje.Kategoriler.bilgi_kategori;
import com.example.berkh.wiki_proje.Model.ProductsByCategory;
import com.example.berkh.wiki_proje.R;
import com.example.berkh.wiki_proje.sepet;

public class hesabim extends AppCompatActivity implements View.OnClickListener {

    ImageButton btn_cikis,btnhome;
    TextView txt_ad_soyad,siparislerim,aldiklarim,tekip_ettiklerim,mesajlarim,bilgilerim,ayarlar;
    String email,pass;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;


    private TextView mTextMessage;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent c = new Intent(hesabim.this, MainActivity.class);
                    return true;
                case R.id.navigation_sepet:
                    Intent i = new Intent(hesabim.this, sepet.class);
                    startActivity(i);
                    return true;
                case R.id.navigation_hesap:
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hesabim);
        btn_cikis = findViewById(R.id.btn_cikis);
        btn_cikis.setOnClickListener(this);
        btnhome = findViewById(R.id.btn_home);
        btnhome.setOnClickListener(this);
        txt_ad_soyad = findViewById(R.id.txt_ad_soyad);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_main);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        String ad = get_shared();
        txt_ad_soyad.setText(ad);

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==btnhome.getId())
        {
            Intent i = new Intent(hesabim.this, MainActivity.class);
            startActivity(i);
        }
        else {
            SharedPreferences sharedPref = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("Email", "");
            editor.putString("Password", "");
            editor.apply();
            Intent i = new Intent(hesabim.this, MainActivity.class);
            startActivity(i);
        }
    }


    public String get_shared()
    {
        sharedPref = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        String mail = sharedPref.getString("Email","");

        String[] parts = mail.split("@",2);
        email = parts[0];

        return email;
    }

    public void nesne_tanıt()
    {
        btn_cikis = findViewById(R.id.btn_cikis);
        btn_cikis.setOnClickListener(this);
        btnhome = findViewById(R.id.btn_home);
        btnhome.setOnClickListener(this);
        txt_ad_soyad = findViewById(R.id.txt_ad_soyad);
        siparislerim = findViewById(R.id.txt_siparisler);
        siparislerim.setOnClickListener(this);
        aldiklarim = findViewById(R.id.txt_aldıklarım);
        aldiklarim.setOnClickListener(this);
        tekip_ettiklerim = findViewById(R.id.txt_takip);
        tekip_ettiklerim.setOnClickListener(this);
        mesajlarim = findViewById(R.id.txt_mesaj);
        mesajlarim.setOnClickListener(this);
        bilgilerim = findViewById(R.id.txt_bilgiler);
        bilgilerim.setOnClickListener(this);

        ayarlar = findViewById(R.id.txt_ayar);
        ayarlar.setOnClickListener(this);


    }
}

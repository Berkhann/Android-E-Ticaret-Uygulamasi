package com.example.berkh.wiki_proje;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.berkh.wiki_proje.Kategoriler.MainActivity;
import com.example.berkh.wiki_proje.Model.sepet_urun_model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class sepet extends AppCompatActivity implements View.OnClickListener{

    private TextView mTextMessage,toplam_hepsi;
    RecyclerView liste;
    Database db;
    List<sepet_urun_model> urun_list = new ArrayList<>();
    ArrayList<HashMap<String, String>> ürün_liste;
    sepet_urun_model s = new sepet_urun_model();
    sepet_adapter adapter;
    Double toplam_fiyat = 0.0;
    ImageButton cikis;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent i = new Intent(sepet.this, MainActivity.class);
                    startActivity(i);
                    return true;
                case R.id.navigation_buy:
                    Toast.makeText(getApplicationContext(),"SATIN AL",Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_emptyons:
                    db.resetTables();
                    adapter = null;
                    liste.setAdapter(adapter);
                    toplam_fiyat = 0.0;
                    toplam_hepsi.setText( new DecimalFormat("##.##").format(toplam_fiyat)+" TL");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sepet);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        liste = findViewById(R.id.liste_sepet);
        liste.setLayoutManager(new LinearLayoutManager(this));

        db = new Database(getApplicationContext());
        ürün_liste = db.urunler();//urun listesini alıyoruz

        toplam_hepsi = findViewById(R.id.txt_toplam_hepsi);

        cikis = findViewById(R.id.btn_cikis);



        for(int i=0;i<ürün_liste.size();i++){

            s.urun_adi = ürün_liste.get(i).get("urun_adi");
            s.urun_id = Integer.parseInt(ürün_liste.get(i).get("urun_id"));
            s.fiyat = ürün_liste.get(i).get("urun_fiyat");
            s.adet = ürün_liste.get(i).get("urun_adet");
            urun_list.add(s);

            toplam_fiyat = toplam_fiyat+(Double.parseDouble(s.fiyat)*Double.parseDouble(s.adet));


            s= new sepet_urun_model();
        }

        adapter = new sepet_adapter(urun_list,R.layout.sepet_urun,getApplicationContext());

        toplam_hepsi.setText( new DecimalFormat("##.##").format(toplam_fiyat)+" TL");

        liste.setAdapter(adapter);


    }

    @Override
    public void onClick(View view) {

    }
}

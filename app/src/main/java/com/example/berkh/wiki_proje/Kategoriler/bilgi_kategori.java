package com.example.berkh.wiki_proje.Kategoriler;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


import com.example.berkh.wiki_proje.hesap.Login_page;
import com.example.berkh.wiki_proje.Model.Categories;
import com.example.berkh.wiki_proje.R;
import com.example.berkh.wiki_proje.hesap.hesabim;
import com.example.berkh.wiki_proje.sepet;

import java.util.ArrayList;

public class bilgi_kategori extends AppCompatActivity implements View.OnClickListener{


    ArrayList<Categories> list;

    TextView AnaSayfa;
    ImageButton hesap;

    private TextView mTextMessage;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent c = new Intent(bilgi_kategori.this, MainActivity.class);
                    startActivity(c);
                    return true;
                case R.id.navigation_sepet:
                    Intent i = new Intent(bilgi_kategori.this, sepet.class);
                    startActivity(i);
                    return true;
                case R.id.navigation_hesap:
                    SharedPreferences sharedPref = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    String email, pass;
                    email = sharedPref.getString("Email", "");
                    pass = sharedPref.getString("Password", "");

                    if (email == "" && pass == "") {
                        Intent a = new Intent(bilgi_kategori.this, Login_page.class);
                        startActivity(a);
                    } else {
                        Intent a = new Intent(bilgi_kategori.this, hesabim.class);
                        startActivity(a);
                    }
                    return true;
            }
            return false;
        }
    };




    private Bilgi_Tekil mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main);


        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_main);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        Intent i = getIntent();
         list = (ArrayList<Categories>) i
                .getSerializableExtra("list");


        bilgi();
       // getProducts_Drawer_Category();








    }


    public void bilgi()
    {


        final RecyclerView liste_subkategori = findViewById(R.id.liste_kat2);
        liste_subkategori.setLayoutManager((new LinearLayoutManager(this)));




        Bilgi_Tekil bilgi_k = new Bilgi_Tekil(list,R.layout.activity_bilgi_kategori,getApplicationContext());
        liste_subkategori.setAdapter(bilgi_k);


    }



   /* private void getProducts_Drawer_Category() {

        final RecyclerView liste_kategori = findViewById(R.id.liste_kategori);
        liste_kategori.setLayoutManager((new LinearLayoutManager(this)));

        WikiposRest servis = WikiposClient.getInstance();
        Call<Categories[]> cagri = servis.BilgileriAl_users(StaticVariables.JSON_TOKEN);
        cagri.enqueue(new Callback<Categories[]>() {
            public void onResponse(Call<Categories[]> call, Response<Categories[]> response) {
                List<Categories> bilgiler = Arrays.asList(response.body());
                Ana_Kategori_Tekil tekil_bilgi = new Ana_Kategori_Tekil(bilgiler, R.layout.activity_bilgi_kategori, getApplicationContext());
                liste_kategori.setAdapter(tekil_bilgi);
            }


            @Override
            public void onFailure(Call<Categories[]> call, Throwable t) {

            }
        });
    }*/




    public void onClick(View view) {
        if(view.getId() == AnaSayfa.getId()) {
            Intent i = new Intent(bilgi_kategori.this, MainActivity.class);
            startActivity(i);
        }
        else
        {

            SharedPreferences sharedPref = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            String email,pass;
            email = sharedPref.getString("Email","");
            pass = sharedPref.getString("Password","");
            if(email == "" && pass == "") {
                Intent i = new Intent(bilgi_kategori.this, Login_page.class);
                startActivity(i);
            }
            else
            {
                Intent i = new Intent(bilgi_kategori.this, hesabim.class);
                startActivity(i);
            }


        }
    }
}











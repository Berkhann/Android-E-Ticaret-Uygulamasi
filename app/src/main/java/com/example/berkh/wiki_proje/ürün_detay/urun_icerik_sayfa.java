package com.example.berkh.wiki_proje.ürün_detay;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.berkh.wiki_proje.Api.WikiposClient;
import com.example.berkh.wiki_proje.Api.WikiposRest;
import com.example.berkh.wiki_proje.Database;
import com.example.berkh.wiki_proje.Kategoriler.MainActivity;
import com.example.berkh.wiki_proje.Kategoriler.bilgi_kategori;
import com.example.berkh.wiki_proje.hesap.Login_page;
import com.example.berkh.wiki_proje.Model.Product;
import com.example.berkh.wiki_proje.Model.ProductsByCategory;
import com.example.berkh.wiki_proje.Model.StaticVariables;
import com.example.berkh.wiki_proje.R;
import com.example.berkh.wiki_proje.hesap.hesabim;
import com.example.berkh.wiki_proje.sepet;
import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPicker;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class urun_icerik_sayfa extends AppCompatActivity implements View.OnClickListener{

     ProductsByCategory.Product product_from_category;
     ImageButton btn_home,btn_account,exit;
     ImageView sepeteekle;
     int productID,adet,CurrentInventoryQuantity;
     String urunadi,fiyat,barcode;
     ScrollableNumberPicker S;

    private TextView mTextMessage;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent c = new Intent(urun_icerik_sayfa.this, MainActivity.class);
                    startActivity(c);
                    return true;
                case R.id.navigation_sepet:
                    Intent i = new Intent(urun_icerik_sayfa.this, sepet.class);
                    startActivity(i);
                    return true;
                case R.id.navigation_hesap:
                    SharedPreferences sharedPref = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    String email, pass;
                    email = sharedPref.getString("Email", "");
                    pass = sharedPref.getString("Password", "");

                    if (email == "" && pass == "") {
                        Intent a = new Intent(urun_icerik_sayfa.this, Login_page.class);
                        startActivity(a);
                    } else {
                        Intent a = new Intent(urun_icerik_sayfa.this, hesabim.class);
                        startActivity(a);
                    }
                    return true;
            }
            return false;
        }
    };

     List<ProductsByCategory.Product> rastgele_liste = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urun_icerik_sayfa);



        sepeteekle = findViewById(R.id.sepete_ekle);
        sepeteekle.setOnClickListener(this);


        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_main);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        urun_bilgilerini_yerlestir();

        rastgele_getir();

        S = findViewById(R.id.number_picker);



    }



    public void rastgele_getir()
    {
        int size =getIntent().getIntExtra("size",0);

        final RecyclerView liste_urun = findViewById(R.id.list_rastgele);
        liste_urun.setLayoutManager((new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)));

        Intent i = getIntent();
        rastgele_liste = (List<ProductsByCategory.Product>)i.getSerializableExtra("rastgele");
        Rastgele_urun_cek adapter = new Rastgele_urun_cek(rastgele_liste,R.layout.urun_rastgele_gorunum,getApplicationContext());
        liste_urun.setAdapter(adapter);




    }




    public void urun_bilgilerini_yerlestir()
    {

        Intent i = getIntent();
        product_from_category = (ProductsByCategory.Product)i.getSerializableExtra("product");


        WikiposRest servis = WikiposClient.getInstance();
        Call<Product> cagri = servis.get_Product_detay(StaticVariables.JSON_TOKEN,product_from_category.ID);
        cagri.enqueue(new Callback<Product>() {
            public void onResponse(Call<Product> call, Response<Product> response) {
                Product product = response.body();
                urun_detay_yerlestir(product);

            }


            @Override
            public void onFailure(Call<Product> call, Throwable t) {

            }
        });
    }

    public void urun_detay_yerlestir(Product P)
    {

        TextView Name,Price,OldPrice,Description,Barcode,Stok;
        ImageView img;

        productID = P.ID;
        urunadi = P.Name;
        fiyat = P.Price.toString();
        barcode = P.Barcode;

        Name = findViewById(R.id.txt_urun_adi);
        Price = findViewById(R.id.txt_urun_fiyat);
        OldPrice = findViewById(R.id.txt_old_price);
        Description = findViewById(R.id.txt_description);
        Barcode = findViewById(R.id.txt_Barcode);
        Stok = findViewById(R.id.txt_stok);
        img = findViewById(R.id.urun_foto);

        Name.setText(P.Name);
        Price.setText(new DecimalFormat("##.##").format(P.Price)+"₺");
        OldPrice.setText(new DecimalFormat("##.##").format(P.OldPrice)+"₺");
        OldPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        if(P.Description==null)
        {
            Description.setText(P.Description);
        }
        else
        {
            String s = android.text.Html.fromHtml(P.Description).toString();
            Description.setText(s);
        }


        Barcode.setText(P.Barcode);
        Stok.setText(Integer.toString(P.CurrentInventoryQuantity));

        S.setMaxValue(P.CurrentInventoryQuantity);

        S.setMinValue(1);
        CurrentInventoryQuantity = P.CurrentInventoryQuantity;

        String S = product_from_category.ImageFile.Source;
        String[] parts = S.split(".jpg",2);
        String part1 = parts[0];


        resim_goster(("https://wikiapis.com"+part1+"_small.jpg"),img);

    }


    public void resim_goster(String url, ImageView imageView)
    {

        Picasso.with(getApplicationContext())
                .load(url)
                .error(R.mipmap.ic_launcher)
                .into(imageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Log.e("İmage","Resim Yüklenemedi.");
                    }
                });

    }


    @Override
    public void onClick(View view) {

         if(sepeteekle.getId()==view.getId()) {
            if (CurrentInventoryQuantity == 0) {
                Toast.makeText(getApplicationContext(), "Ürün stokta yok.", Toast.LENGTH_SHORT).show();
            } else {
                adet = S.getValue();
                Database db = new Database(getApplicationContext());
                db.urunEkle(urunadi, Integer.toString(productID), fiyat, barcode, Integer.toString(adet));
                Toast.makeText(getApplicationContext(), Integer.toString(adet)+" "+urunadi + " sepete eklendi.", Toast.LENGTH_SHORT).show();
            }
        }


    }
}

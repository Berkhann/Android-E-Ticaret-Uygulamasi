package com.example.berkh.wiki_proje.Product_list;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.berkh.wiki_proje.Api.WikiposClient;
import com.example.berkh.wiki_proje.Api.WikiposRest;
import com.example.berkh.wiki_proje.Kategoriler.MainActivity;
import com.example.berkh.wiki_proje.Kategoriler.bilgi_kategori;
import com.example.berkh.wiki_proje.Model.Filter;
import com.example.berkh.wiki_proje.Model.ProductsByCategory;
import com.example.berkh.wiki_proje.Model.StaticVariables;
import com.example.berkh.wiki_proje.Model.Pagination;
import com.example.berkh.wiki_proje.R;
import com.example.berkh.wiki_proje.hesap.Login_page;
import com.example.berkh.wiki_proje.hesap.hesabim;
import com.example.berkh.wiki_proje.sepet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class urun extends AppCompatActivity {


    String categoryID;

    private TextView mTextMessage;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent c = new Intent(urun.this, MainActivity.class);
                    return true;
                case R.id.navigation_sepet:
                    Intent i = new Intent(urun.this, sepet.class);
                    startActivity(i);
                    return true;
                case R.id.navigation_hesap:
                    SharedPreferences sharedPref = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    String email, pass;
                    email = sharedPref.getString("Email", "");
                    pass = sharedPref.getString("Password", "");

                    if (email == "" && pass == "") {
                        Intent a = new Intent(urun.this, Login_page.class);
                        startActivity(a);
                    } else {
                        Intent a = new Intent(urun.this, hesabim.class);
                        startActivity(a);
                    }
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urun);

        Intent mIntent = getIntent();
        int intValue = mIntent.getIntExtra("kategori_id", 0);

        categoryID = Integer.toString(intValue);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_main);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);



        getProducts();

    }





    private void getProducts(){

        final RecyclerView liste_urun = findViewById(R.id.urun_liste);
        liste_urun.setLayoutManager((new LinearLayoutManager(this)));

        Callback<ProductsByCategory> call = new Callback<ProductsByCategory>() {
            @Override
            public void onResponse(Call<ProductsByCategory> call, Response<ProductsByCategory> response) {
                if(response.code()==200){
                    Log.i("urun","Products fetched successfully");
                    ProductsByCategory pbc = response.body();
                    ProductsByCategory products = response.body();
                    take_product_to_list adapter = new take_product_to_list(products, R.layout.bilgi,getApplicationContext());
                    liste_urun.setAdapter(adapter);


                }
                else{
                    Log.e("urun","Error at http request! "+response.code()+" Message: "+response.message());
                }
            }

            @Override
            public void onFailure(Call<ProductsByCategory> call, Throwable t) {
                Log.e("urun","Error at http request!");
            }
        };
        Filter filter = new Filter(new Pagination(10,1,"Name",false));
        WikiposRest rest = WikiposClient.getInstance();
        rest.getProductsByCategory(StaticVariables.JSON_TOKEN,filter,categoryID).enqueue(call);
    }


}





package com.example.berkh.wiki_proje.Kategoriler;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.example.berkh.wiki_proje.Api.WikiposClient;
import com.example.berkh.wiki_proje.Api.WikiposRest;
import com.example.berkh.wiki_proje.Database;
import com.example.berkh.wiki_proje.Model.Filter;
import com.example.berkh.wiki_proje.Model.Pagination;
import com.example.berkh.wiki_proje.Model.ProductsByCategory;
import com.example.berkh.wiki_proje.Product_list.take_product_to_list;
import com.example.berkh.wiki_proje.hesap.Login_page;
import com.example.berkh.wiki_proje.Model.Categories;
import com.example.berkh.wiki_proje.Model.StaticVariables;
import com.example.berkh.wiki_proje.Product_list.urun;
import com.example.berkh.wiki_proje.R;
import com.example.berkh.wiki_proje.hesap.hesabim;
import com.example.berkh.wiki_proje.sepet;
import com.example.berkh.wiki_proje.ürün_detay.Rastgele_urun_cek;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener{



    ImageButton btn_hesap;

    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private HashMap<Categories,List<Categories>> listHash;
    FloatingActionButton fab;
    private TextView mTextMessage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Database db = new Database(getApplicationContext());    
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_main);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        listele();

        rastgele_al_product("1326",R.id.r_balıkcilik);
        rastgele_al_product("1325",R.id.r_dalis);
        rastgele_al_product("1324",R.id.r_kamp);
        rastgele_al_product("1323",R.id.r_outdoor);



    }


    public void rastgele_al_product(String categoryID,int rcycler_id)
    {
        final RecyclerView liste_urun =  findViewById(rcycler_id);
        liste_urun.setLayoutManager((new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)));


        Callback<ProductsByCategory> call = new Callback<ProductsByCategory>() {
            @Override
            public void onResponse(Call<ProductsByCategory> call, Response<ProductsByCategory> response) {
                if(response.code()==200){
                    Log.i("urun","Products fetched successfully");
                    List<ProductsByCategory.Product> prod = Arrays.asList(response.body().Products);
                    Rastgele_urun_cek adapter = new Rastgele_urun_cek(prod,R.layout.urun_rastgele_gorunum_anasayfa,getApplicationContext());
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





    public void listele()
    {
        listView = findViewById(R.id.extlistview);


        WikiposRest servis = WikiposClient.getInstance();
        Call<Categories[]> cagri = servis.BilgileriAl_users(StaticVariables.JSON_TOKEN);//APİ Moduülünü kullanarak istekte bulunuyoruz.
        cagri.enqueue(new Callback<Categories[]>() {
            public void onResponse(Call<Categories[]> call, Response<Categories[]> response) {//Response'nin bodysine göre bir yönlendime var. Body null değilse işlemler devam eder.

                final List<Categories> bilgiler = Arrays.asList(response.body());
                listAdapter = new ExpandableListAdapter(getApplicationContext(),bilgiler,listHash);
                listView.setAdapter(listAdapter);

                listener(bilgiler);


            }

            @Override
            public void onFailure(Call<Categories[]> call, Throwable t) {

            }
        });
    }


    private void listener(final List<Categories> bilgiler) {
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {

                if(bilgiler.get(i).SubCategories.get(i1).SubCategories.size() != 0) {
                    Intent intent = new Intent(getApplicationContext(), bilgi_kategori.class);
                    intent.putExtra("list", (Serializable) bilgiler.get(i).SubCategories.get(i1).SubCategories);
                    intent.putExtra("kategori_id",bilgiler.get(i).ID);
                    getApplicationContext().startActivity(intent);
                }
                else if(bilgiler.get(i).SubCategories.get(i1).SubCategories.size() == 0)
                {
                    Intent intent = new Intent(getApplicationContext(), urun.class);
                    intent.putExtra("kategori_id",bilgiler.get(i).SubCategories.get(i1).ID);
                    getApplicationContext().startActivity(intent);

                }




                return false;
            }
        });

    }





    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        if(btn_hesap.getId()==view.getId()) {
            SharedPreferences sharedPref = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            String email, pass;
            email = sharedPref.getString("Email", "");
            pass = sharedPref.getString("Password", "");

            if (email == "" && pass == "") {
                Intent i = new Intent(MainActivity.this, Login_page.class);
                startActivity(i);
            } else {
                Intent i = new Intent(MainActivity.this, hesabim.class);
                startActivity(i);
            }
        }


    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_sepet:
                    Intent i = new Intent(MainActivity.this, sepet.class);
                    startActivity(i);
                    return true;
                case R.id.navigation_hesap:
                    SharedPreferences sharedPref = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    String email, pass;
                    email = sharedPref.getString("Email", "");
                    pass = sharedPref.getString("Password", "");

                    if (email == "" && pass == "") {
                        Intent a = new Intent(MainActivity.this, Login_page.class);
                        startActivity(a);
                    } else {
                        Intent a = new Intent(MainActivity.this, hesabim.class);
                        startActivity(a);
                    }
                    return true;
            }
            return false;
        }
    };




}

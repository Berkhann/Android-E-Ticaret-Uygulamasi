package com.example.berkh.wiki_proje.hesap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.berkh.wiki_proje.Api.WikiposClient;
import com.example.berkh.wiki_proje.Api.WikiposRest;

import com.example.berkh.wiki_proje.Model.StaticVariables;
import com.example.berkh.wiki_proje.Model.kgiris_body;
import com.example.berkh.wiki_proje.Model.user_info;
import com.example.berkh.wiki_proje.R;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login_page extends AppCompatActivity implements View.OnClickListener {

    TextView k_adi, sifre, hide;
    Button btn_giris, kaydol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        k_adi = findViewById(R.id.txt_kullanici_adi);
        sifre = findViewById(R.id.txt_sifre);
        btn_giris = findViewById(R.id.btn_giris);

        btn_giris.setOnClickListener(this);


    }

    public void giris() {
        Callback<user_info> call = new Callback<user_info>() {
            @Override
            public void onResponse(Call<user_info> call, Response<user_info> response) {
                if (response.body().success == true)
                    {
                        SharedPreferences sharedPref = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("Email", k_adi.getText().toString());
                        editor.putString("Password", sifre.getText().toString());
                        editor.putString("ID",Integer.toString(response.body().content.session.user.iD));
                        editor.apply();
                        Intent i = new Intent(Login_page.this, hesabim.class);
                        startActivity(i);
                    }
                else{
                        k_adi.setText(null);
                        sifre.setText(null);
                        Toast.makeText(getApplicationContext(), "Email yada Şifre yanlış!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override public void onFailure (Call < user_info > call, Throwable t){
                    Log.e("urun", "Error at http request!");
                }
            };
            kgiris_body k_giris = new kgiris_body(k_adi.getText().toString(), sifre.getText().toString());
            WikiposRest servis = WikiposClient.getInstance();
            servis.get_Content(StaticVariables.JSON_TOKEN,k_giris).
            enqueue(call);
        }


        @Override public void onClick (View view){
            giris();

        }
    }


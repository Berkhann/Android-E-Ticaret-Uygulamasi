package com.example.berkh.wiki_proje.Api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WikiposClient {
    private WikiposRest apiService;
    private static WikiposClient client;

    private WikiposClient(){
        String baseUrl = "https://wikiapis.com/api/"; // Adres başlangıcı
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().create()))// Cevirici eklendi
                .client(builder.build())
                .build();
        apiService = retrofit.create(WikiposRest.class);
    }

    public static WikiposRest getInstance(){
        if(client == null){
            client = new WikiposClient();
        }
        return client.getApiService();
    }

    public static void removeInstance(){
        if(client != null){
            client = null;
        }
    }

    private WikiposRest getApiService(){
        return apiService;
    }
}

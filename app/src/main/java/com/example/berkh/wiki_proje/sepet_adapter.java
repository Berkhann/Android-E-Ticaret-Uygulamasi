package com.example.berkh.wiki_proje;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


import com.example.berkh.wiki_proje.Kategoriler.MainActivity;
import com.example.berkh.wiki_proje.Kategoriler.bilgi_kategori;
import com.example.berkh.wiki_proje.Model.Categories;
import com.example.berkh.wiki_proje.Model.sepet_urun_model;
import com.example.berkh.wiki_proje.R;
import com.example.berkh.wiki_proje.hesap.hesabim;
import com.example.berkh.wiki_proje.itemClickListener;
import com.example.berkh.wiki_proje.Product_list.urun;
import com.example.berkh.wiki_proje.ürün_detay.urun_icerik_sayfa;


import java.io.Serializable;
import java.util.List;

public class sepet_adapter extends RecyclerView.Adapter<sepet_adapter.BilgiYerleskesi>{



    private List<sepet_urun_model> bilgiler_ana_kategori;
    private int sira_layout;
    private Context context;


    public static class BilgiYerleskesi extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView ad,barcode,fiyat,adet,toplam,toplam_hepsi;
        ImageButton cikis;
        private com.example.berkh.wiki_proje.itemClickListener itemClickListener;
        RecyclerView liste;

        public BilgiYerleskesi(View v) {

            super(v);

            ad = v.findViewById(R.id.txt_sepet_ad);
            fiyat = v.findViewById(R.id.txt_sepet_fiyat);
            cikis = v.findViewById(R.id.imgbtn_cikis);
            liste = v.findViewById(R.id.liste_sepet);
            adet = v.findViewById(R.id.txt_adet);
            toplam = v.findViewById(R.id.txt_toplamfiyat);
            toplam_hepsi = v.findViewById(R.id.txt_toplam_hepsi);



            cikis.setOnClickListener(this);
            itemView.setOnClickListener(this);

        }

        public void setItemClickListener(com.example.berkh.wiki_proje.itemClickListener itemClickListener)
        {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {

            itemClickListener.onClick(v,getAdapterPosition(),false);
        }
    }

    public sepet_adapter(List<sepet_urun_model> urun, int siraLayout, Context context)
    {
        this.bilgiler_ana_kategori = urun;
        this.sira_layout = siraLayout;
        this.context = context;



    }



    @NonNull
    @Override
    public sepet_adapter.BilgiYerleskesi onCreateViewHolder(@NonNull ViewGroup v, int i) {


        View view = LayoutInflater.from(v.getContext()).inflate(sira_layout, v, false);

        return new sepet_adapter.BilgiYerleskesi(view);
    }



    @Override
    public void onBindViewHolder(@NonNull final BilgiYerleskesi bilgiYerleskesi, final int i) {




            Float toplam = Float.parseFloat(bilgiler_ana_kategori.get(i).adet)*Float.parseFloat(bilgiler_ana_kategori.get(i).fiyat);

            bilgiYerleskesi.ad.setText(bilgiler_ana_kategori.get(i).urun_adi);
            bilgiYerleskesi.fiyat.setText(bilgiler_ana_kategori.get(i).fiyat.toString()+" TL");
            bilgiYerleskesi.toplam.setText(toplam+" TL");
            bilgiYerleskesi.adet.setText("x"+bilgiler_ana_kategori.get(i).adet);



        bilgiYerleskesi.setItemClickListener(new itemClickListener() {


            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(view.getId()== bilgiYerleskesi.cikis.getId())
                {
                    Database db = new Database(context);
                    db.ürünSil(bilgiler_ana_kategori.get(i).urun_id);
                }

            }
        });


    }

    @Override
    public int getItemCount() {

        return bilgiler_ana_kategori.size();
    }





}

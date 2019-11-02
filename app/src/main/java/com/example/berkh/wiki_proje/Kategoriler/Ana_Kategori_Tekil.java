package com.example.berkh.wiki_proje.Kategoriler;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.berkh.wiki_proje.Model.Categories;
import com.example.berkh.wiki_proje.R;
import com.example.berkh.wiki_proje.itemClickListener;

import java.io.Serializable;
import java.util.List;

public class Ana_Kategori_Tekil extends RecyclerView.Adapter<Ana_Kategori_Tekil.BilgiYerleskesi>{

    private List<Categories> bilgiler_ana_kategori;
    private int sira_layout_kategori;
    private Context context_kategori;
    Categories gson;

    public static class BilgiYerleskesi extends RecyclerView.ViewHolder implements View.OnClickListener

    {
        TextView UserId;//Kullanılacak viewler belirlendi
        com.example.berkh.wiki_proje.itemClickListener itemClickListener;

        public BilgiYerleskesi(View v) {

            super(v);

            UserId =  v.findViewById(R.id.txttxt); // Textview'e atama yaptık.


            itemView.setOnClickListener(this);

        }
       public void setItemClickListener(itemClickListener itemClickListener)
        {
            this.itemClickListener = itemClickListener;
        }


        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),false);

        }


    }

    public Ana_Kategori_Tekil(List<Categories> icerik, int siraLayout, Context context)//Constructor ile Gelen Değerleri Globalde olanlara atadık.
    {
        this.bilgiler_ana_kategori = icerik;
        this.sira_layout_kategori = siraLayout;
        this.context_kategori = context;
    }

    @NonNull
    @Override
    public Ana_Kategori_Tekil.BilgiYerleskesi onCreateViewHolder(@NonNull ViewGroup v, int i) {

        View view = LayoutInflater.from(v.getContext()).inflate(sira_layout_kategori,v,false);

        return new Ana_Kategori_Tekil.BilgiYerleskesi(view);

    }


    @Override
    public void onBindViewHolder(@NonNull final Ana_Kategori_Tekil.BilgiYerleskesi bilgiYerleskesi, final int i) {


        bilgiYerleskesi.UserId.setText(bilgiler_ana_kategori.get(i).Name);//Önceden ayarlanılan Textview Set edildi.




        bilgiYerleskesi.setItemClickListener(new itemClickListener() {


            @Override
            public void onClick(View view, int position, boolean isLongClick) {

                Intent intent = new Intent(context_kategori,bilgi_kategori.class);//İntent geçişleri yapıldı.
                intent.putExtra("list", (Serializable) bilgiler_ana_kategori.get(i).SubCategories);
                context_kategori.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return bilgiler_ana_kategori.size();
    }





}

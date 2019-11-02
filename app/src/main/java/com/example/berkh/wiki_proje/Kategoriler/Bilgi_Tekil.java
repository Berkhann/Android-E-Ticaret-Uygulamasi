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
import com.example.berkh.wiki_proje.Product_list.urun;


import java.io.Serializable;
import java.util.List;

public class Bilgi_Tekil extends RecyclerView.Adapter<Bilgi_Tekil.BilgiYerleskesi>{



    private List<Categories> bilgiler_ana_kategori;
    private int sira_layout;
    private Context context;


    public static class BilgiYerleskesi extends RecyclerView.ViewHolder implements View.OnClickListener
    {
         TextView UserId;
      private com.example.berkh.wiki_proje.itemClickListener itemClickListener;


        public BilgiYerleskesi(View v) {

            super(v);

            UserId =  v.findViewById(R.id.txttxt);

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

    public Bilgi_Tekil(List<Categories> subkategor_icerik, int siraLayout, Context context)
    {
        this.bilgiler_ana_kategori = subkategor_icerik;
        this.sira_layout = siraLayout;
        this.context = context;


    }



    @NonNull
    @Override
    public Bilgi_Tekil.BilgiYerleskesi onCreateViewHolder(@NonNull ViewGroup v, int i) {


              View view = LayoutInflater.from(v.getContext()).inflate(sira_layout, v, false);

              return new Bilgi_Tekil.BilgiYerleskesi(view);
    }



    @Override
    public void onBindViewHolder(@NonNull BilgiYerleskesi bilgiYerleskesi, final int i) {



        bilgiYerleskesi.UserId.setText(bilgiler_ana_kategori.get(i).Name);


        bilgiYerleskesi.setItemClickListener(new itemClickListener() {


            @Override
            public void onClick(View view, int position, boolean isLongClick) {
            if(bilgiler_ana_kategori.get(i).SubCategories.size() != 0) {
                    Intent intent = new Intent(context, bilgi_kategori.class);
                    intent.putExtra("list", (Serializable) bilgiler_ana_kategori.get(i).SubCategories);
                    intent.putExtra("kategori_id",bilgiler_ana_kategori.get(i).ID);
                    context.startActivity(intent);
                }
            else if(bilgiler_ana_kategori.get(i).SubCategories.size() == 0)
            {
                Intent intent = new Intent(context, urun.class);
                intent.putExtra("kategori_id",bilgiler_ana_kategori.get(i).ID);
                context.startActivity(intent);

            }

            }
        });



    }

    @Override
    public int getItemCount() {

        return bilgiler_ana_kategori.size();
    }





}

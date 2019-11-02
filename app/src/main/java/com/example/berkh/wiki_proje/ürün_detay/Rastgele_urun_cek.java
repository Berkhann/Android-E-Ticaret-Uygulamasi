package com.example.berkh.wiki_proje.ürün_detay;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.berkh.wiki_proje.Model.ProductsByCategory;
import com.example.berkh.wiki_proje.R;
import com.example.berkh.wiki_proje.itemClickListener;
import com.squareup.picasso.Picasso;


import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Rastgele_urun_cek extends RecyclerView.Adapter<Rastgele_urun_cek.BilgiYerleskesi>{



    private List<ProductsByCategory.Product> urunler;
    private int sira_layout;
    private Context context;


    public static class BilgiYerleskesi extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView name,price;
        ImageView img;
        private com.example.berkh.wiki_proje.itemClickListener itemClickListener;


        public BilgiYerleskesi(View v) {

            super(v);

            name =  v.findViewById(R.id.txt_rast_name);
            img = v.findViewById(R.id.txt_rast_img);
            price=v.findViewById(R.id.rast_price);

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

    public Rastgele_urun_cek(List<ProductsByCategory.Product> urunler, int siraLayout, Context context)
    {
        this.urunler = urunler;
        this.sira_layout = siraLayout;
        this.context = context;


    }



    @NonNull
    @Override
    public Rastgele_urun_cek.BilgiYerleskesi onCreateViewHolder(@NonNull ViewGroup v, int i) {


        View view = LayoutInflater.from(v.getContext()).inflate(sira_layout, v, false);

        return new Rastgele_urun_cek.BilgiYerleskesi(view);
    }



    @Override
    public void onBindViewHolder(@NonNull BilgiYerleskesi bilgiYerleskesi, final int i) {


        bilgiYerleskesi.name.setText(urunler.get(i).Name);
        bilgiYerleskesi.price.setText(new DecimalFormat("##.##").format(urunler.get(i).Price)+"₺");

        String S = urunler.get(i).ImageFile.Source;
        String[] parts = S.split(".jpg",2);
        String part1 = parts[0];


        resim_goster(("https://wikiapis.com"+part1+"_small.jpg"),bilgiYerleskesi.img);



        bilgiYerleskesi.setItemClickListener(new itemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {

                Intent intent = new Intent(context, urun_icerik_sayfa.class);
                intent.putExtra("product", (Serializable) urunler.get(i));
                intent.putExtra("size",urunler.size());
                intent.putExtra("rastgele", (Serializable) rastgele_diz());

                context.startActivity(intent);


            }
        });

    }

    public List<ProductsByCategory.Product> rastgele_diz()
    {
        List<ProductsByCategory.Product> rast_list = new ArrayList<>();
        if(urunler.size()<=5) {
            for (int a = 0; a < urunler.size(); a++)
            {
                rast_list.add(urunler.get(a));
            }
        }
        else
        {
            for (int a = 0; a < 5; a++)
            {
                rast_list.add(urunler.get(a));
            }
        }

        return rast_list;
    }


    @Override
    public int getItemCount() {

        return urunler.size();
    }


    public void resim_goster(String url, ImageView imageView)
    {

        Picasso.with(context)
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





}

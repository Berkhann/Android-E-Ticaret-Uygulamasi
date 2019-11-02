package com.example.berkh.wiki_proje.Product_list;

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
import com.example.berkh.wiki_proje.ürün_detay.urun_icerik_sayfa;
import com.squareup.picasso.Picasso;


import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class take_product_to_list extends RecyclerView.Adapter<take_product_to_list.BilgiYerleskesi>{



    private List<ProductsByCategory.Product> urun_bilgi;
    private ProductsByCategory urun;

    private int sira_layout;
    private Context context;


    public static class BilgiYerleskesi extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView name,price;
        private com.example.berkh.wiki_proje.itemClickListener itemClickListener;
        ImageView img;

        public BilgiYerleskesi(View v) {

            super(v);

            name = v.findViewById(R.id.txtProdName);
            price = v.findViewById(R.id.txtProdPrice);
            img = v.findViewById(R.id.img_prod);

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

    public take_product_to_list(ProductsByCategory urunler, int siraLayout, Context context)
    {

        this.urun = urunler;
        this.urun_bilgi = Arrays.asList(urunler.Products);
        this.sira_layout = siraLayout;
        this.context = context;


    }



    @NonNull
    @Override
    public take_product_to_list.BilgiYerleskesi onCreateViewHolder(@NonNull ViewGroup v, int i) {


        View view = LayoutInflater.from(v.getContext()).inflate(sira_layout, v, false);

        return new take_product_to_list.BilgiYerleskesi(view);
    }



    @Override
    public void onBindViewHolder(@NonNull BilgiYerleskesi bilgiYerleskesi, final int i) {


        bilgiYerleskesi.name.setText(urun_bilgi.get(i).Name);
        bilgiYerleskesi.price.setText(new DecimalFormat("##.##").format(urun_bilgi.get(i).Price)+"₺");
        String S = urun_bilgi.get(i).ImageFile.Source;

        String[] parts = S.split(".jpg",2);
        String part1 = parts[0];

        resim_goster(("https://wikiapis.com"+part1+"_small.jpg"),bilgiYerleskesi.img);


        bilgiYerleskesi.setItemClickListener(new itemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {

                    Intent intent = new Intent(context, urun_icerik_sayfa.class);
                    intent.putExtra("product", (Serializable) urun_bilgi.get(i));
                    intent.putExtra("size",urun_bilgi.size());
                    intent.putExtra("rastgele", (Serializable) restgele_diz());

                   context.startActivity(intent);


            }
        });

    }


    public List<ProductsByCategory.Product> restgele_diz()
    {
        List<ProductsByCategory.Product> rast_list = new ArrayList<>();
        if(urun_bilgi.size()<=5) {
            for (int a = 0; a < urun_bilgi.size(); a++)
            {
                rast_list.add(urun_bilgi.get(a));
            }
        }
        else
        {
            for (int a = 0; a < 5; a++)
            {
                rast_list.add(urun_bilgi.get(a));
            }
        }

        return rast_list;
    }



    public int getItemCount() {

        return urun_bilgi.size();
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
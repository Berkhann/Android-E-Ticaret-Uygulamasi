package com.example.berkh.wiki_proje.Model;

import android.content.Intent;

import java.io.Serializable;

public class ProductsByCategory implements Serializable {
    public Manufacturer[] Manufacturers;
    public Product[] Products;

    public int ProductCount;

    public class Manufacturer implements Serializable {
        public int ID;
        public String Name;
    }

    public class Product implements Serializable {
        public int ID;
        public String Name;
        public Double Price;
        public Double OldPrice;
        public String Barcode;
        public int CurrentInventoryQuantity;
        public float MinPrice;
        public float MaxPrice;
        public float OldMinPrice;
        public float OldMaxPrice;
        public String Path;
        public int AverageRating;
        public ImageFile ImageFile;
    }

    public class ImageFile implements Serializable{
        public int Quality;
        public String Name;
        public String Source;
    }
}

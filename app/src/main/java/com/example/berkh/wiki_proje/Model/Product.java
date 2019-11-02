package com.example.berkh.wiki_proje.Model;

import android.content.pm.FeatureInfo;

import java.io.Serializable;

public class Product implements Serializable{

    public int ID;
    public String Path;
    public  String Name;
    public  Double Price;
    public   Double OldPrice;
    public   String Description;
    public   int SKU;
    public String Barcode;
    public   boolean SoldWithRewards;
    public   int RewardsValue;
    public   Double PriceWithRewards;
    public   int CurrentInventoryQuantity;
    public  Double AverageRating;
    public  ImageFiles imageFiles;
    public  Manufacturer manufacturer;
    public  Groups groups;
    public   Categories categories;
    public   String Tags;
    public   Features features;
    public Attributes attributes;
    public Variants variants;

    public class ImageFiles implements Serializable
    {
        public int Quality;
        public String Name;
        public String Source;
    }

    public class Manufacturer implements Serializable {
        public int ID;
        public String Name;
        public String Path;
    }
    public class Groups
    {

    }
    public class Features
    {

    }
    public class Attributes
    {

    }
    public class Variants
    {

    }


}

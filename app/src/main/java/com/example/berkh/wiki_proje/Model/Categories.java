package com.example.berkh.wiki_proje.Model;

import java.io.Serializable;
import java.util.List;

public class Categories implements Serializable{

    public int ID;
    public String Name;
    public String Path;
    public String Color;
    public List<Categories> SubCategories;
}



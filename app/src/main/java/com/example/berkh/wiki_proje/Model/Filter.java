package com.example.berkh.wiki_proje.Model;

import java.util.ArrayList;

public class Filter {
    public Pagination Pagination;
    public ArrayList<Filter> Filters = new ArrayList<>();


    public Filter(Pagination pagination){
        this.Pagination = pagination;
    }
}
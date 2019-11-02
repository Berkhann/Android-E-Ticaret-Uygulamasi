package com.example.berkh.wiki_proje.Model;

import java.io.Serializable;

public class Pagination implements Serializable {
    public int PageSize;
    public int Page;
    public String OrderField;
    public Boolean OrderByDesc;

    public Pagination(int pageSize, int page, String orderField, boolean orderByDesc){
        this.PageSize = pageSize;
        this.Page = page;
        this.OrderField = orderField;
        this.OrderByDesc = orderByDesc;
    }

}
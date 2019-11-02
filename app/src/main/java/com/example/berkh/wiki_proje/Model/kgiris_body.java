package com.example.berkh.wiki_proje.Model;

import java.io.Serializable;

public class kgiris_body implements Serializable{
    public String Email;
    public String Password;

    public kgiris_body(String Email,String Password) {
        this.Email=Email;
        this.Password=Password;
    }
}

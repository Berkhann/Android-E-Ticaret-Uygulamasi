package com.example.berkh.wiki_proje.Model;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class user_info {

    @SerializedName("Content")
    @Expose
    public Content content;
    @SerializedName("Success")
    @Expose
    public Boolean success;
    @SerializedName("Message")
    @Expose
    public Object message;

    public class Content {

        @SerializedName("Tokens")
        @Expose
        public List<Token> tokens = null;
        @SerializedName("Session")
        @Expose
        public Session session;

    }

    public class Session {

        @SerializedName("User")
        @Expose
        public User user;
        @SerializedName("LanguageID")
        @Expose
        public Integer languageID;

    }

    public class Token {

        @SerializedName("Token")
        @Expose
        public String token;
        @SerializedName("Branch")
        @Expose
        public String branch;

    }

    public class User {

        @SerializedName("ID")
        @Expose
        public Integer iD;
        @SerializedName("Title")
        @Expose
        public Object title;
        @SerializedName("FirstName")
        @Expose
        public String firstName;
        @SerializedName("LastName")
        @Expose
        public String lastName;
        @SerializedName("PhoneNumber")
        @Expose
        public String phoneNumber;
        @SerializedName("Fax")
        @Expose
        public Object fax;
        @SerializedName("Email")
        @Expose
        public String email;
        @SerializedName("Birthday")
        @Expose
        public Object birthday;
        @SerializedName("CompanyName")
        @Expose
        public String companyName;
        @SerializedName("Gender")
        @Expose
        public Integer gender;
        @SerializedName("Credit")
        @Expose
        public Object credit;
        @SerializedName("IyzicoCardUserKey")
        @Expose
        public Object iyzicoCardUserKey;
        @SerializedName("Rewards")
        @Expose
        public Integer rewards;
        @SerializedName("SubscribeNewsletter")
        @Expose
        public Boolean subscribeNewsletter;

    }
}
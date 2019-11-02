package com.example.berkh.wiki_proje.Api;

import com.example.berkh.wiki_proje.Model.Categories;
import com.example.berkh.wiki_proje.Model.Filter;
import com.example.berkh.wiki_proje.Model.Product;
import com.example.berkh.wiki_proje.Model.ProductsByCategory;
import com.example.berkh.wiki_proje.Model.kgiris_body;
import com.example.berkh.wiki_proje.Model.user_info;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface WikiposRest {
    @POST("Catalog/Products/ProductsByCategory/{CategoryID}")// kategorilerin içindeki ürünlere ulaşıyoruz.
    Call<ProductsByCategory> getProductsByCategory(@Header("Authorization") String authorization, @Body Filter paginationObj, @Path("CategoryID") String categoryID);
    @GET("Catalog/Products/CategoryTree")//Kategori ağacına ulaşıyoruz
    Call<Categories[]> BilgileriAl_users(@Header("Authorization") String authorization);
    @GET("Catalog/Products/Product/{ProductID}")//Ürüne direk erişiyoruz.
    Call<Product> get_Product_detay(@Header("Authorization") String authorization,@Path("ProductID") int ProductID);
    @POST("CRM/Customers/Login")
    Call<user_info> get_Content(@Header("Authorization") String authorization, @Body kgiris_body body);



}

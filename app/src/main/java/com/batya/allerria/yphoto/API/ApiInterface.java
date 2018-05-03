package com.batya.allerria.yphoto.API;
import com.batya.allerria.yphoto.Models.Gallery;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("?key=8868841-cbc4d6256f90454a781a8d022&image_type=photo&q=cat")
    Call<Gallery> getGallery();
}

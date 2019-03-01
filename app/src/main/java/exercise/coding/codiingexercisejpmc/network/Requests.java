package exercise.coding.codiingexercisejpmc.network;

import java.util.ArrayList;

import exercise.coding.codiingexercisejpmc.model.Album;
import retrofit2.Call;
import retrofit2.http.GET;

public interface Requests {

    @GET("albums")
    Call<ArrayList<Album>> getAlbums();
}

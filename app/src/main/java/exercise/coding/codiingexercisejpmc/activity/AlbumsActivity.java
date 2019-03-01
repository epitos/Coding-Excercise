package exercise.coding.codiingexercisejpmc.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import exercise.coding.codiingexercisejpmc.R;
import exercise.coding.codiingexercisejpmc.adapter.AlbumAdapter;
import exercise.coding.codiingexercisejpmc.model.Album;
import exercise.coding.codiingexercisejpmc.network.Requests;
import exercise.coding.codiingexercisejpmc.utils.ApiUtils;
import exercise.coding.codiingexercisejpmc.utils.Utils;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumsActivity extends AppCompatActivity {

    private static final String TAG = AlbumsActivity.class.getSimpleName();

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView noDataAvailableTextView;
    private Requests requests;
    private ArrayList<String> albumsTitleList;
    private AlbumAdapter adapter;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums);
        bindViews();
        albumsTitleList = new ArrayList<>();
        requests = ApiUtils.getRequests();
        realm = Realm.getDefaultInstance();
        getAlbums();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private void setRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this,  DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
    }

    private void bindViews() {
        recyclerView = (RecyclerView) findViewById(R.id.album_list);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        noDataAvailableTextView = (TextView) findViewById(R.id.no_data_available_title);
    }

    private void getAlbums() {
        if (Utils.hasNetworkConnection(this)) {
            getAlbumsFromServer();
        } else {
            getAlbumsFromDb();
        }
    }

    private void getAlbumsFromDb() {
        RealmResults<Album> albums = realm.where(Album.class).findAllAsync();
        albums.load();

        if (albums.size() == 0) {
            noDataAvailableTextView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
        } else {
            for(Album album:albums) {
                albumsTitleList.add(album.getTitle());
            }
            adapter = new AlbumAdapter(albumsTitleList);
            setRecyclerView();
            progressBar.setVisibility(View.GONE);
        }
    }

    private void getAlbumsFromServer() {
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();

        requests.getAlbums().enqueue(new Callback<ArrayList<Album>>() {
            @Override
            public void onResponse(Call<ArrayList<Album>> call, Response<ArrayList<Album>> response) {
                if (response.isSuccessful()) {
                    for (Album album: response.body()) {
                        albumsTitleList.add(album.getTitle());
                    }
                    Collections.sort(albumsTitleList);
                    adapter = new AlbumAdapter(albumsTitleList);
                    setRecyclerView();
                    progressBar.setVisibility(View.GONE);

                    for (int i = 0; i < albumsTitleList.size(); i++) {
                        final Album album = new Album();
                        album.setTitle(albumsTitleList.get(i));
                        realm.beginTransaction();
                        realm.copyToRealm(album);
                        realm.commitTransaction();
                    }
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Album>> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }
}

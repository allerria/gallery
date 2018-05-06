package com.batya.allerria.yphoto;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;

import com.batya.allerria.yphoto.API.Api;
import com.batya.allerria.yphoto.API.ApiInterface;
import com.batya.allerria.yphoto.Models.Gallery;
import com.batya.allerria.yphoto.Models.Image;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GalleryActivity extends AppCompatActivity {

    ApiInterface apiInterface;
    private Gallery gallery = new Gallery();
    private RecyclerView rv;
    private GridLayoutManager lm;
    private GalleryAdapter adapter;
    private Integer totalItemCount, firstVisibleItem, visibleItemCount, page = 1;
    private Boolean loading = true;
    private String q = "cute cat";
    private SearchView searchView;

    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            visibleItemCount = lm.getChildCount();
            totalItemCount = lm.getItemCount();
            firstVisibleItem = lm.findFirstVisibleItemPosition();
            if (!loading) {
                if (firstVisibleItem + visibleItemCount >= totalItemCount && firstVisibleItem > 0) {
                    Log.d("TAG", "loading" + firstVisibleItem + " " + totalItemCount);
                    ++page;
                    loading = true;
                    loadData();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        searchView = findViewById(R.id.search_view);
        searchView.setQueryHint("Искать...");
        searchView.onActionViewExpanded();
        searchView.setIconified(false);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                q = query;
                adapter.clearData();
                searchView.clearFocus();
                loadData();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        apiInterface = Api.getClient().create(ApiInterface.class);
        rv = (RecyclerView) findViewById(R.id.gallery_recycler_view);
        rv.setHasFixedSize(true);
        lm = new GridLayoutManager(this, 3);
        rv.setLayoutManager(lm);
        adapter = new GalleryAdapter(this);
        rv.setAdapter(adapter);
        rv.addOnScrollListener(recyclerViewOnScrollListener);
        if (savedInstanceState != null) {
            Log.d("TAG", "trying to restore position " + savedInstanceState.getInt("position"));
            adapter.addData(savedInstanceState.<Image>getParcelableArrayList("data"));
            lm.scrollToPosition(savedInstanceState.getInt("position"));
        } else {
            loadData();
        }
    }

    protected void loadData() {
        Call<Gallery> call = apiInterface.getGallery(q, page);
        call.enqueue(new Callback<Gallery>() {
            @Override
            public void onResponse(Call<Gallery> call, Response<Gallery> response) {
                Log.d("TAG", response.code() + "");
                if (response.code() == 200) {
                    adapter.addData(response.body().getImages());
                    adapter.notifyDataSetChanged();
                }
                Log.d("TAG", "Bla");
                loading = false;
            }

            @Override
            public void onFailure(Call<Gallery> call, Throwable t) {
                Log.d("TAG", t.toString());
                call.cancel();
                loading = false;
            }
        });
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", lm.findFirstVisibleItemPosition());
        outState.putParcelableArrayList("data", new ArrayList<Parcelable>(adapter.getData()));
    }

}

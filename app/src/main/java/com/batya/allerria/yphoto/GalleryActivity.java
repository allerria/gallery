package com.batya.allerria.yphoto;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.batya.allerria.yphoto.API.Api;
import com.batya.allerria.yphoto.API.ApiInterface;
import com.batya.allerria.yphoto.Models.Gallery;
import com.batya.allerria.yphoto.GalleryAdapter;
import com.batya.allerria.yphoto.Models.Image;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GalleryActivity extends AppCompatActivity {

    ApiInterface apiInterface;
    private TextView mTextMessage;
    private Gallery gallery = new Gallery();
    private RecyclerView rv;
    private GridLayoutManager lm;
    private GalleryAdapter adapter;
    private Integer totalItemCount, firstVisibleItem, visibleItemCount, page = 1;
    private Boolean loading = true;
    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            visibleItemCount = lm.getChildCount();
            totalItemCount = lm.getItemCount();
            firstVisibleItem = lm.findFirstVisibleItemPosition();
            if (!loading) {
                if ((visibleItemCount + firstVisibleItem) >= totalItemCount && firstVisibleItem >= 0) {
                    ++page;
                    loading = true;
                    loadData();
                }
            }
        }
    };

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_gallery:
                    mTextMessage.setText(R.string.title_gallery);
                    return true;
                case R.id.navigation_search:
                    mTextMessage.setText(R.string.title_search);
                    return true;
                case R.id.navigation_likes:
                    mTextMessage.setText(R.string.title_likes);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        apiInterface = Api.getClient().create(ApiInterface.class);
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        rv = (RecyclerView) findViewById(R.id.gallery_recycler_view);
        rv.setHasFixedSize(false);
        lm = new GridLayoutManager(this, 3);
        rv.setLayoutManager(lm);
        adapter = new GalleryAdapter(this);
        rv.setAdapter(adapter);
        rv.addOnScrollListener(recyclerViewOnScrollListener);
        loadData();
    }
    protected void loadData() {
        Call<Gallery> call = apiInterface.getGallery("cute Cat", page);
        call.enqueue(new Callback<Gallery>() {
            @Override
            public void onResponse(Call<Gallery> call, Response<Gallery> response) {
                Log.d("TAG", response.code() + "");
                if (response.code() == 200) {
                    /*
                    gallery.addImages(response.body().getImages());
                    adapter.setData(gallery.getImages());
                    */
                    adapter.addData(response.body().getImages());
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
}

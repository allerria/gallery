package com.batya.allerria.yphoto;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by allerria on 05.05.18.
 */

public class ImageActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        getIncomingIntent();
    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("image_url")) {
            setImage(getIntent().getStringExtra("image_url"));
        }
    }

    private void setImage(String imageUrl) {
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.ic_dashboard_black_24dp)
                .into(imageView);
    }
}

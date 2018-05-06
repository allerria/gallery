package com.batya.allerria.yphoto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.batya.allerria.yphoto.Models.Image;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by allerria on 04.05.18.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    private List<Image> data = new ArrayList<>();
    private Context context;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public SquareImageView squareImageView;


        public ViewHolder(SquareImageView v) {
            super(v);
            squareImageView = v;
        }

    }

    public GalleryAdapter(Context context) {
        this.context = context;
    }

    public void addData(List<Image> data) {
        this.data.addAll(data);
        Log.d("TAG", "addedData" + this.data.size());
    }

    public void clearData() {
        this.data.clear();
    }

    public List<Image> getData() {
        return this.data;
    }

    @Override
    public GalleryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        SquareImageView v = (SquareImageView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_item, parent, false);
        final ViewHolder vh = new ViewHolder(v);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) parent.getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        vh.squareImageView.getLayoutParams().width = displayMetrics.widthPixels / 3;
        vh.squareImageView.getLayoutParams().height = displayMetrics.widthPixels / 3;
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Image current = data.get(position);
        Glide.with(context)
                .load(current.getPreviewURL())
                .centerCrop()
                .into(holder.squareImageView);

        holder.squareImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "onClick: ");
                Intent intent = new Intent(context, ImageActivity.class);
                intent.putExtra("image_url", data.get(position).getWebformatURL());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

package com.batya.allerria.yphoto;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
        // each data item is just a string in this case
        public SquareImageView squareImageView;


        public ViewHolder(SquareImageView v) {
            super(v);
            squareImageView = v;
        }

    }

    public GalleryAdapter(Context context) {
        this.context = context;
    }

    /*public void setData(List<Image> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }*/

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
        // create a new view
        SquareImageView v = (SquareImageView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_item, parent, false);
        v.setMinimumWidth(parent.getMeasuredWidth() / 3);
        v.setMaxHeight(parent.getMeasuredWidth() / 3);
        v.setMinimumHeight(parent.getMeasuredWidth() / 3);
        v.setMaxWidth(parent.getMeasuredWidth() / 3);
        final ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Image current = data.get(position);
        Glide.with(context)
                .load(current.getPreviewURL())
                .into(holder.squareImageView);

        holder.squareImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "onClick: ");
                Intent intent = new Intent(context, ImageActivity.class);
                intent.putExtra("image_url", data.get(position).getLargeImageURL());
                context.startActivity(intent);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return data.size();
    }
}

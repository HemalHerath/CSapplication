package com.example.beiber.csapplication;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by BEIBER on 7/15/2017.
 */

public class ImageListAdapter extends ArrayAdapter<ProUpload> {

    private Activity context;
    private int resourse;
    private List<ProUpload>listImage;

    public ImageListAdapter(@NonNull Activity context, @LayoutRes int resource, @NonNull List<ProUpload> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resourse = resource;
        listImage = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View v = inflater.inflate(resourse,null);
        TextView tvName = (TextView)v.findViewById(R.id.tvName);
        TextView tvAddress = (TextView)v.findViewById(R.id.tvAddress);
        TextView tvTel = (TextView)v.findViewById(R.id.tvTel);
        ImageView img = (ImageView)v.findViewById(R.id.imgView);

        tvName.setText(listImage.get(position).getName());
        tvAddress.setText(listImage.get(position).getAddress());
        tvTel.setText(listImage.get(position).getTel());

        Glide.with(context).load(listImage.get(position).getUrl()).into(img);
        return v;
    }
}

package com.finalyearapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CategoryAdapter extends BaseAdapter {

    Context context;
    /*String[] nameArray;
    String[] imageArray;*/
    ArrayList<CategoryList> arrayList;

    /*public CategoryAdapter(Context context, String[] nameArray, String[] imageArray) {
        this.context = context;
        this.nameArray = nameArray;
        this.imageArray = imageArray;
    }*/

    public CategoryAdapter(Context context, ArrayList<CategoryList> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.custom_category,null);

        ImageView imageView = view.findViewById(R.id.custom_category_image);
        TextView name = view.findViewById(R.id.custom_category_name);

        /*name.setText(nameArray[i]);
        Glide.with(context).load(imageArray[i]).placeholder(R.mipmap.ic_launcher).into(imageView);*/

        name.setText(arrayList.get(i).getName());
        Glide.with(context).load(arrayList.get(i).getImage()).placeholder(R.mipmap.ic_launcher).into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Image Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, arrayList.get(i).getName(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}

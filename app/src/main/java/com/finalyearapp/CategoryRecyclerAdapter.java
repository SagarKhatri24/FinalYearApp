package com.finalyearapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.MyHolder> {

    Context context;
    ArrayList<CategoryList> arrayList;
    SharedPreferences sp;

    public CategoryRecyclerAdapter(Context context, ArrayList<CategoryList> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        sp = context.getSharedPreferences(ConstantSp.PREF,Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_category,parent,false);
        return new MyHolder(view);
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.custom_category_image);
            name = itemView.findViewById(R.id.custom_category_name);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.name.setText(arrayList.get(position).getName());
        Glide.with(context).load(arrayList.get(position).getImage()).placeholder(R.mipmap.ic_launcher).into(holder.imageView);

        /*holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Image Clicked", Toast.LENGTH_SHORT).show();
            }
        });*/

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.edit().putInt(ConstantSp.CATEGORYID,arrayList.get(position).getId()).commit();
                //Toast.makeText(context, arrayList.get(position).getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, SubCategoryActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}

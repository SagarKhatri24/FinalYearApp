package com.finalyearapp;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

public class SubCategoryActivity extends AppCompatActivity {

    int[] idArray = {1,2,3,4,5};
    int[] categoryIdArray = {3,3,4,4,3};
    String[] nameArray = {
            "Top Wear",
            "Bottom Wear",
            "Gaming",
            "Laptop",
            "Footwear"
    };

    ArrayList<SubCategoryList> arrayList;

    RecyclerView recyclerView;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sub_category);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sp = getSharedPreferences(ConstantSp.PREF,MODE_PRIVATE);

        recyclerView = findViewById(R.id.sub_category_recycler);

        //recyclerView.setLayoutManager(new LinearLayoutManager(CategoryRecyclerActivity.this));

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        //recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));

        arrayList = new ArrayList<>();
        for(int i=0;i<nameArray.length;i++){
            if(sp.getInt(ConstantSp.CATEGORYID,0)==categoryIdArray[i]) {
                SubCategoryList list = new SubCategoryList();
                list.setSubCategoryId(idArray[i]);
                list.setCategoryId(categoryIdArray[i]);
                list.setName(nameArray[i]);
                arrayList.add(list);
            }
        }
        //CategoryAdapter adapter = new CategoryAdapter(CategoryActivity.this,nameArray,imageArray);
        SubCategoryAdapter adapter = new SubCategoryAdapter(SubCategoryActivity.this,arrayList);
        recyclerView.setAdapter(adapter);

    }
}
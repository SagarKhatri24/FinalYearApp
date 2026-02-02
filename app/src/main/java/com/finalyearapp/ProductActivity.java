package com.finalyearapp;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {

    int[] productIdArray = {1,2,3};
    int[] subCategoryIdArray= {1,1,2};
    String[] vendorNameArray = {
            "GM TRENDS",
            "TRIPR",
            "MK BROTHERS"
    };
    String[] nameArray = {
            "Men Printed Round Neck Cotton Blend Blue T-Shirt",
            "Men Solid Henley Neck Cotton Blend Black, Beige T-Shirt",
            "Men Printed, Graphic Print Black Track Pants"
    };
    String[] priceArray = {"399","999","1499"};
    String[] afterDiscountArray = {"241","215","205"};
    String[] discountArray = {"39","78","86"};
    String[] imageArray = {
            "https://rukminim2.flixcart.com/image/612/612/xif0q/t-shirt/p/s/r/m-6002-never-royal-blue-m-gm-trends-original-imahj6jgp3ywwwgh.jpeg?q=70",
            "https://rukminim2.flixcart.com/image/612/612/xif0q/t-shirt/y/c/3/l-tblbghn-d213-tripr-original-imahj8x8nae4aazn.jpeg?q=70",
            "https://rukminim2.flixcart.com/image/612/612/xif0q/track-pant/v/b/6/xl-spider-mk-brothers-original-imahj6xuj2ryrver.jpeg?q=70"
    };

    ArrayList<ProductList> arrayList;

    RecyclerView recyclerView;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sp = getSharedPreferences(ConstantSp.PREF,MODE_PRIVATE);

        recyclerView = findViewById(R.id.product_recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(ProductActivity.this));

        //recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        //recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));

        arrayList = new ArrayList<>();
        for(int i=0;i<nameArray.length;i++){
            if(sp.getInt(ConstantSp.SUBCATEGORYID,0)==subCategoryIdArray[i]) {
                ProductList list = new ProductList();
                list.setProductId(productIdArray[i]);
                list.setSubCategoryId(subCategoryIdArray[i]);
                list.setVendorName(vendorNameArray[i]);
                list.setName(nameArray[i]);
                list.setPrice(priceArray[i]);
                list.setAfterDiscount(afterDiscountArray[i]);
                list.setDiscount(discountArray[i]);
                list.setImage(imageArray[i]);
                arrayList.add(list);
            }
        }
        ProductAdapter adapter = new ProductAdapter(ProductActivity.this,arrayList);
        recyclerView.setAdapter(adapter);

    }
}
package com.hvac.vtronix.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hvac.vtronix.Adpater.stockProducts.CategoryListWiseRecyclerViewAdapter;
import com.hvac.vtronix.Adpater.stockProducts.ProductListWiseRecyclerViewAdapter;
import com.hvac.vtronix.POJOClass.StockDetails;
import com.hvac.vtronix.POJOClass.TempStock;
import com.hvac.vtronix.R;
import com.hvac.vtronix.Utils.Utils;
import com.hvac.vtronix.ui.productSpecs.ProductsSpecsViewModel;

import java.util.ArrayList;
import java.util.List;

public class FirstPageCategory extends AppCompatActivity {

    String TAG = FirstPageCategory.class.getSimpleName();

    String stringCatName,stringCatDescp,stringAssignTitle;
    TextView mTitleTextView;
    ImageView imageViewBackPressed;
    private TextView textViewDescription;
    private SharedPreferences preferences;

    private RecyclerView recyclerView,recyclerViewProductOnly;

    //Category Inside CategoriesBeanX wise product showed in App
    private CategoryListWiseRecyclerViewAdapter listRecyclerViewAdapter;
    private List<StockDetails.CategoriesBeanX.CategoriesBean> categoriesBeanArrayList = new ArrayList<>();

    private ProductListWiseRecyclerViewAdapter productListWiseRecyclerViewAdapter;
    private List<ProductsSpecsViewModel> productsSpecsViewModelList = new ArrayList<>();

    private List<String> checkingList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_first_page);

        ActionBar mActionBar = getSupportActionBar();
        if(mActionBar!=null) {
            mActionBar.setDisplayHomeAsUpEnabled(false);
            mActionBar.setDisplayShowHomeEnabled(false);
            mActionBar.setDisplayShowTitleEnabled(false);
        }
        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView        =   mInflater.inflate(R.layout.custom_actionbar, null);
        mTitleTextView          =   mCustomView.findViewById(R.id.title_text);
        imageViewBackPressed    =   mCustomView.findViewById(R.id.imageView_back_icon);

        imageViewBackPressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if(mActionBar!=null)
        {
            mActionBar.setCustomView(mCustomView);
            mActionBar.setDisplayShowCustomEnabled(true);
        }

        textViewDescription     =    findViewById(R.id.textView_main_cat_descript);
        recyclerView            =    findViewById(R.id.recyclerview_cat_inner_product);
        recyclerViewProductOnly =    findViewById(R.id.recyclerview_product_inner_);

        Intent iin= getIntent();
        Bundle extras = iin.getExtras();
        if(extras!=null) {

         //   StockDetailsRecyclerViewAdapter.java Intent Values
            stringCatName          = (String) extras.get("CatName");
            stringCatDescp         = (String) extras.get("CatDesc");

            stringAssignTitle      =  (String)  extras.get("CatName");
            Log.e(TAG, "onCreate: Category main name :"+stringCatName);

            textViewDescription.setText(stringCatDescp);

            preferences = PreferenceManager.getDefaultSharedPreferences(FirstPageCategory.this);
            SharedPreferences.Editor editor = preferences.edit();

            editor.putString("tempCatName",stringCatName);
            editor.putString("tempCatDesc",stringCatDescp);
            editor.putString("tempAssignName",stringAssignTitle);
            // Save the changes in SharedPreferences
            editor.apply(); // commit changes
        }

        try {

            String jsonFileString = Utils.getJsonFromAssets(FirstPageCategory.this, "data.json");
            Gson gson = new Gson();
            StockDetails resultStock = gson.fromJson(jsonFileString, StockDetails.class);

            List<StockDetails.CategoriesBeanX> categories = resultStock.getCategories();

            if(categories.size()>0)
            {
                for (int i = 0; i < categories.size(); i++) {

                    if (categories.get(i).getCategories().size() > 0)
                    {
                        List<StockDetails.CategoriesBeanX.CategoriesBean> categoriesBeanList =
                                categories.get(i).getCategories();

                        if (categories.get(i).getName().equals(stringCatName)) {

                            for (int l = 0; l < categoriesBeanList.size(); l++) {
                                Log.e(TAG, "onCreate: First Details array :" +
                                        categoriesBeanList.get(l).getName()
                                        + "\t ssss:" + categoriesBeanList.get(l).getProduct_count());

                                StockDetails.CategoriesBeanX.CategoriesBean result_r = new StockDetails.CategoriesBeanX.CategoriesBean();

                                result_r.setName(categoriesBeanList.get(l).getName());
                                result_r.setImage(categoriesBeanList.get(l).getImage());
                                result_r.setProduct_count(categoriesBeanList.get(l).getProduct_count());

                                categoriesBeanArrayList.add(result_r);

                                checkingList.add("MainList");

                            }

                        }

                    }

//                    if(categoriesBeanArrayList.size()>0)
//                    {
//
//                        int numberOfColumns = 2;
//                        recyclerView.setLayoutManager(new GridLayoutManager(FirstPageCategory.this, numberOfColumns));
//                        listRecyclerViewAdapter = new CategoryListWiseRecyclerViewAdapter(FirstPageCategory.this,
//                                categoriesBeanArrayList);
//                        recyclerView.setAdapter(listRecyclerViewAdapter);
//
//                    }

                }
            }
                
        }
        catch (JsonSyntaxException e)
        {
            e.printStackTrace();
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////




        //Getting category wise Only Main Products Array result json
        try {

            String jsonFileString = Utils.getJsonFromAssets(FirstPageCategory.this, "data.json");
            Gson gson = new Gson();
            TempStock resultStock = gson.fromJson(jsonFileString, TempStock.class);
            List<TempStock.CategoriesBean> categories = resultStock.getCategories();

            for (int i = 0; i < categories.size(); i++)
            {
                //getting Inside Category Inner product Details
                if(categories.get(i).getProducts().size()>0)
                {
                    Log.e(TAG, "onCreateView: Size is :"+ categories.get(i).getProducts().size()
                    +"\t sssss:"+categories.get(i).getName());

                    //product is same to redirect Inner Product detail list view page.
                    if (categories.get(i).getName().equals(stringCatName)) {
                        Intent newIntent = new Intent(FirstPageCategory.this,FirstPageInnerCategory.class);
                        newIntent.putExtra("CatName",categories.get(i).getName());
                        startActivity(newIntent);
                        finish();

                    }

                }

            }

        }
        catch (JsonSyntaxException e)
        {
            e.printStackTrace();
        }

        if(checkingList.size()>0) {

            if(checkingList.contains("MainList")) {

                recyclerViewProductOnly.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                Log.e(TAG, "onCreate: dddddddddddddddddddddd" );
                if (categoriesBeanArrayList.size() > 0) {

                    int numberOfColumns = 2;
                    recyclerView.setLayoutManager(new GridLayoutManager(FirstPageCategory.this, numberOfColumns));
                    listRecyclerViewAdapter = new CategoryListWiseRecyclerViewAdapter(FirstPageCategory.this,
                            categoriesBeanArrayList);
                    recyclerView.setAdapter(listRecyclerViewAdapter);

                }
            }
            else
            {
                Log.e(TAG, "onCreate: eeeeeeeeeeeeeeeeeeee" );
            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        preferences           =   PreferenceManager.getDefaultSharedPreferences(FirstPageCategory.this);
        stringCatName         =   preferences.getString("tempCatName", "");
        stringCatDescp        =   preferences.getString("tempCatDesc", "");
       // Log.d(TAG, "onStart: GetCategory name :"+stringCatName+"\t second:"+stringCatDescp);
        mTitleTextView.setText(stringCatName);

    }

    @Override
    public void onResume() {
        super.onResume();

        preferences           =   PreferenceManager.getDefaultSharedPreferences(FirstPageCategory.this);
        stringCatName         =   preferences.getString("tempCatName", "");
        stringCatDescp        =   preferences.getString("tempCatDesc", "");
        stringAssignTitle      =    preferences.getString("tempAssignName","");
//        Log.e(TAG, "onResume: GetCategory Page :"+stringCatName +"\t Assign Title :"+stringAssignTitle
//                +"\t second:"+stringCatDescp);
        mTitleTextView.setText(stringAssignTitle);
        textViewDescription.setText(stringCatDescp);
    }

}

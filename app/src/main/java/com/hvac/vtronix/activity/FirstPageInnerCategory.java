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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hvac.vtronix.Adpater.CategoryInnerRecyclerViewAdapter;
import com.hvac.vtronix.Adpater.ProductsInnerRecyclerViewAdapter;
import com.hvac.vtronix.POJOClass.StockDetails;
import com.hvac.vtronix.POJOClass.TempStock;
import com.hvac.vtronix.R;
import com.hvac.vtronix.Utils.Utils;
import com.hvac.vtronix.ui.productSpecs.ProductsSpecsViewModel;

import java.util.ArrayList;
import java.util.List;

public class FirstPageInnerCategory extends AppCompatActivity {

    String TAG = FirstPageInnerCategory.class.getSimpleName();

    TextView mTitleTextView;
    ImageView imageViewBackPressed;

    String stringCatName,stringCatNameMain;

    private List<StockDetails.CategoriesBeanX.CategoriesBean.ProductsBean> productsBeanArrayList = new ArrayList<>();

    private RecyclerView recyclerView,recyclerViewProducts;
    private CategoryInnerRecyclerViewAdapter recyclerViewAdapter;

    private List<String> checkingList = new ArrayList<>();

    private List<ProductsSpecsViewModel> productsSpecsViewModelList = new ArrayList<>();
    private ProductsInnerRecyclerViewAdapter productsInnerRecyclerViewAdapter;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_first_products_inner_page);

        preferences             =   PreferenceManager.getDefaultSharedPreferences(FirstPageInnerCategory.this);

        recyclerView            =   findViewById(R.id.recyclerview_cat_inner_product_page);
        recyclerViewProducts    =   findViewById(R.id.recyclerview_cat_inner_product_only);

        //Custom Action Bar Refer URL:
        //  https://stacktips.com/tutorials/android/actionbar-with-custom-view-example-in-android
        ActionBar mActionBar = getSupportActionBar();
        if(mActionBar!=null) {
            mActionBar.setDisplayHomeAsUpEnabled(false);
            mActionBar.setDisplayShowHomeEnabled(false);
            mActionBar.setDisplayShowTitleEnabled(false);
        }

        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView        =   mInflater.inflate(R.layout.custom_actionbar_inner, null);
        mTitleTextView          =   (TextView) mCustomView.findViewById(R.id.title_text_inner);
        imageViewBackPressed    =   (ImageView)mCustomView.findViewById(R.id.imageView_back_icon_inner);

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

        Intent iin= getIntent();
        Bundle extras = iin.getExtras();

        if(extras!=null) {

            stringCatName              = (String) extras.get("CatName");

            mTitleTextView.setText(stringCatName);

            Log.e(TAG, "onCreate: Products Under name :"+stringCatName+"\n Inside :"+stringCatNameMain );

            preferences = PreferenceManager.getDefaultSharedPreferences(FirstPageInnerCategory.this);
            SharedPreferences.Editor editor = preferences.edit();

            editor.putString("tempCatMainName",stringCatNameMain);
            editor.putString("tempCatName",stringCatName);
            // Save the changes in SharedPreferences
            editor.apply(); // commit changes
        }


        try {

            String jsonFileString = Utils.getJsonFromAssets(FirstPageInnerCategory.this, "data.json");
            Gson gson = new Gson();
            StockDetails resultStock = gson.fromJson(jsonFileString, StockDetails.class);

            List<StockDetails.CategoriesBeanX> categories = resultStock.getCategories();

            recyclerView.setVisibility(View.VISIBLE);
            recyclerViewProducts.setVisibility(View.GONE);

            for (int i = 0; i < categories.size(); i++) {

                List<StockDetails.CategoriesBeanX.CategoriesBean> categoriesBeanList =
                            categories.get(i).getCategories();

                for(StockDetails.CategoriesBeanX.CategoriesBean s1:categoriesBeanList)
                {
                        Log.e(TAG, "onCreate: Checking :"+s1.getName()+"\t Name :"+stringCatName);

                        List<StockDetails.CategoriesBeanX.CategoriesBean.ProductsBean> productsBeanList =
                                s1.getProducts();

                        if(s1.getName().equals(stringCatName)) {

                            for (int m = 0; m < productsBeanList.size(); m++) {

                                Log.e(TAG, "onCreate: Results are :" + productsBeanList.get(m).getName()
                                        + "\n Desc :" + productsBeanList.get(m).getDescription());

                                StockDetails.CategoriesBeanX.CategoriesBean.ProductsBean result =
                                        new StockDetails.CategoriesBeanX.CategoriesBean.ProductsBean();

                                result.setName(productsBeanList.get(m).getName());
                                result.setImage(productsBeanList.get(m).getImage());
                                result.setDescription(productsBeanList.get(m).getDescription());
                                result.setDiagram_url(productsBeanList.get(m).getDiagram_url());
                                result.setSpecs_url(productsBeanList.get(m).getSpecs_url());
                                result.setUrl(productsBeanList.get(m).getUrl());

                                productsBeanArrayList.add(result);

                                checkingList.add("MainList");

                            }
                        }

                    }

//                if(productsBeanArrayList.size()>0)
//                {
//                    final LinearLayoutManager layoutManager = new LinearLayoutManager(FirstPageInnerCategory.this);
//                    layoutManager.setOrientation(RecyclerView.VERTICAL);
//                    recyclerView.setLayoutManager(layoutManager);
//
//                    recyclerViewAdapter = new CategoryInnerRecyclerViewAdapter(this,productsBeanArrayList);
//                    recyclerView.setAdapter(recyclerViewAdapter);
//                }

            }



        }
        catch (JsonSyntaxException e)
        {
            e.printStackTrace();
        }



        //Getting category wise Only Main Products Array result json
        try {

            String jsonFileString = Utils.getJsonFromAssets(FirstPageInnerCategory.this, "data.json");
            Gson gson = new Gson();
            TempStock resultStock = gson.fromJson(jsonFileString, TempStock.class);
            List<TempStock.CategoriesBean> categories = resultStock.getCategories();

            for (int i = 0; i < categories.size(); i++)
            {
                //getting Inside Category Inner product Details
                if(categories.get(i).getProducts().size()>0)
                {
                    Log.e(TAG, "onCreateView: Detaile products page result :"+ categories.get(i).getProducts().size()
                            +"\t sssss:"+categories.get(i).getName());

                    Log.e(TAG, "onCreate: product Array :" + categories.get(i).getName() +"\t Both Same :"+stringCatName);

                    List<TempStock.CategoriesBean.ProductsBean> InnerCat = categories.get(i).getProducts();

                    if (categories.get(i).getName().equals(stringCatName)) {

                        if (InnerCat.size() > 0) {

                            for (int j = 0; j < InnerCat.size(); j++) {

                                ProductsSpecsViewModel detailsPOJO = new ProductsSpecsViewModel();

                                Log.e(TAG, "onCreate: Inner Page loop :" + InnerCat.get(j).getName());

                                detailsPOJO.setProductName(InnerCat.get(j).getName());
                                detailsPOJO.setProductImage(InnerCat.get(j).getImage());
                                detailsPOJO.setProductDesc(InnerCat.get(j).getDescription());
                                detailsPOJO.setProductDiagram(InnerCat.get(j).getDiagram_url());
                                detailsPOJO.setProductSpecs(InnerCat.get(j).getSpecs_url());
                                detailsPOJO.setProductURL(InnerCat.get(j).getUrl());

                                productsSpecsViewModelList.add(detailsPOJO);

                                checkingList.add("ProductList");

                            }

                        }
                    }

                }


            }

        }
        catch (JsonSyntaxException e)
        {
            e.printStackTrace();
        }


        if(checkingList.size()>0)
        {
            if(checkingList.contains("ProductList"))
            {
                Log.e(TAG, "onCreate: Product for checking List" );
                if(productsSpecsViewModelList.size()>0)
                {
                    recyclerViewProducts.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);

                    final LinearLayoutManager layoutManager = new LinearLayoutManager(FirstPageInnerCategory.this);
                    layoutManager.setOrientation(RecyclerView.VERTICAL);
                    recyclerViewProducts.setLayoutManager(layoutManager);

                    productsInnerRecyclerViewAdapter = new ProductsInnerRecyclerViewAdapter(FirstPageInnerCategory.this,
                            productsSpecsViewModelList);
                    recyclerViewProducts.setAdapter(productsInnerRecyclerViewAdapter);

                }

            }
            else
            {
                Log.e(TAG, "onCreate: Else part for checking List" );
                recyclerViewProducts.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                if(productsBeanArrayList.size()>0)
                {
                    final LinearLayoutManager layoutManager = new LinearLayoutManager(FirstPageInnerCategory.this);
                    layoutManager.setOrientation(RecyclerView.VERTICAL);
                    recyclerView.setLayoutManager(layoutManager);

                    recyclerViewAdapter = new CategoryInnerRecyclerViewAdapter(this,productsBeanArrayList);
                    recyclerView.setAdapter(recyclerViewAdapter);
                }
            }
        }



    }

    @Override
    public void onResume() {
        super.onResume();

        preferences                 =   PreferenceManager.getDefaultSharedPreferences(FirstPageInnerCategory.this);
        stringCatName               =   preferences.getString("tempCatName", "");
        stringCatNameMain           =   preferences.getString("tempCatMainName","");
        Log.d(TAG, "onResume: GetCategory name :"+stringCatName);

        mTitleTextView.setText(stringCatName);
    }

}

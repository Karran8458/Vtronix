package com.hvac.vtronix.ui.productSpecs;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hvac.vtronix.Adpater.ProductsSpecDetailsRecyclerViewAdapter;
import com.hvac.vtronix.POJOClass.StockDetails;
import com.hvac.vtronix.POJOClass.TempStock;
import com.hvac.vtronix.R;
import com.hvac.vtronix.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecsFragment extends Fragment {

    private String TAG = ProductSpecsFragment.class.getSimpleName();

    private List<ProductsSpecsViewModel> productsSpecsViewModelList = new ArrayList<>();

    private EditText editText;
    private RecyclerView recyclerView;

    ProductsSpecDetailsRecyclerViewAdapter recyclerViewAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_product_specs, container, false);

        editText        =   root.findViewById(R.id.product_spec_editText_search);
        recyclerView    =   root.findViewById(R.id.products_specs_page_rv);

        //================================Search in EdiText Coding Part=============================
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(recyclerViewAdapter!=null)
                {
                    recyclerViewAdapter.getFilter().filter(s);
                }

            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        //=====================================End of code==========================================

        //Getting category wise Only Main Products Array result json
        try {

            String jsonFileString = Utils.getJsonFromAssets(getActivity(), "data.json");
            Gson gson = new Gson();

            TempStock resultStock = gson.fromJson(jsonFileString, TempStock.class);

            List<TempStock.CategoriesBean> categories = resultStock.getCategories();
            for (int i = 0; i < categories.size(); i++)
            {
                //getting Inside Category Inner product Details
                if(categories.get(i).getProducts().size()>0)
                {
                    Log.e(TAG, "onCreateView: Size is :"+ categories.get(i).getProducts().size());
                    List<TempStock.CategoriesBean.ProductsBean> InnerCat = categories.get(i).getProducts();

                    if(InnerCat.size()>0) {

                        for (int j = 0; j < InnerCat.size(); j++) {

                            ProductsSpecsViewModel detailsPOJO = new ProductsSpecsViewModel();

                            detailsPOJO.setProductName(InnerCat.get(j).getName());
                            detailsPOJO.setProductImage(InnerCat.get(j).getImage());
                            detailsPOJO.setProductDesc(InnerCat.get(j).getDescription());
                            detailsPOJO.setProductDiagram(InnerCat.get(j).getDiagram_url());
                            detailsPOJO.setProductSpecs(InnerCat.get(j).getSpecs_url());
                            detailsPOJO.setProductURL(InnerCat.get(j).getUrl());

                            productsSpecsViewModelList.add(detailsPOJO);

                        }
                    }
                }

            }

        }
        catch (JsonSyntaxException e)
        {
            e.printStackTrace();
        }

        //Getting Categories Inside Categories Related product details
        try {

            String jsonFileString = Utils.getJsonFromAssets(getActivity(), "data.json");
            Gson gson = new Gson();
            StockDetails resultStock = gson.fromJson(jsonFileString, StockDetails.class);

            List<StockDetails.CategoriesBeanX> categories = resultStock.getCategories();

            for (int i = 0; i < categories.size(); i++) {

                List<StockDetails.CategoriesBeanX.CategoriesBean> categoriesBeans =
                        resultStock.getCategories().get(i).getCategories();

                for(int j=0;j<categoriesBeans.size();j++)
                {
                    List<StockDetails.CategoriesBeanX.CategoriesBean.ProductsBean> productsBeanList =
                            categoriesBeans.get(j).getProducts();

                    if (productsBeanList.size() > 0) {

                        for (int m = 0; m < productsBeanList.size(); m++) {

//                            Log.e(TAG, "onCreateView: Inner Product List");
//                            Log.e(TAG, "onCreateView: ================================================");
//                            Log.e(TAG, "onCreate: inner products Results are :" + productsBeanList.get(m).getName()
//                                    + "\n Desc :" + productsBeanList.get(m).getDescription());

                            ProductsSpecsViewModel detailsPOJO = new ProductsSpecsViewModel();

                            detailsPOJO.setProductName(productsBeanList.get(m).getName());
                            detailsPOJO.setProductImage(productsBeanList.get(m).getImage());
                            detailsPOJO.setProductDesc(productsBeanList.get(m).getDescription());
                            detailsPOJO.setProductDiagram(productsBeanList.get(m).getDiagram_url());
                            detailsPOJO.setProductSpecs(productsBeanList.get(m).getSpecs_url());
                            detailsPOJO.setProductURL(productsBeanList.get(m).getUrl());

                            productsSpecsViewModelList.add(detailsPOJO);

                        }

                    }
                }
            }
        }
        catch (JsonSyntaxException e)
        {
            e.printStackTrace();
        }


        //Load product Inner Details to RecyclerView Adapter
        if(productsSpecsViewModelList.size()>0 )
        {
            final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);

            recyclerViewAdapter =  new ProductsSpecDetailsRecyclerViewAdapter(getActivity(),productsSpecsViewModelList);
            recyclerView.setAdapter(recyclerViewAdapter);

//            for(ProductsSpecsViewModel m : productsSpecsViewModelList)
//            {
//                Log.e(TAG, "onCreateView:Product Array Details:"+m.getProductName()+"\t "+m.getProductDesc());
//
//            }

        }



        return root;

    }

}

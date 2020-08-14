package com.hvac.vtronix.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hvac.vtronix.Adpater.StockDetailsRecyclerViewAdapter;
import com.hvac.vtronix.POJOClass.StockDetails;
import com.hvac.vtronix.R;
import com.hvac.vtronix.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private String TAG = HomeFragment.class.getSimpleName();

    private RecyclerView recyclerView;

    private StockDetails.CategoriesBeanX categoriesBeanX;

    private List<StockDetails> stockDetailsArrayListFinal = new ArrayList<>();

    private List<StockDetails.CategoriesBeanX> categoriesArrayList = new ArrayList<>();
    private List<StockDetails.CategoriesBeanX.CategoriesBean.ProductsBean> productsBeanArrayList = new ArrayList<>();

    private StockDetailsRecyclerViewAdapter recyclerViewAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getActivity().setTitle("Categories");
        View root       = inflater.inflate(R.layout.fragment_home, container, false);


        recyclerView            =   root.findViewById(R.id.recyclerview_cat);


        try {

            String jsonFileString = Utils.getJsonFromAssets(getActivity(), "data.json");
            //Log.e("data -->", jsonFileString);
            Gson gson = new Gson();
            StockDetails resultStock = gson.fromJson(jsonFileString, StockDetails.class);

            if(resultStock!= null )
            {
                categoriesArrayList.clear();
                List<StockDetails.CategoriesBeanX> categories = resultStock.getCategories();
                for (int i = 0; i < categories.size(); i++)
                {
//                    Log.d(TAG, "onCreate:Categories Names :"+categories.get(i).getName()
//                            +"\n Image:"+categories.get(i).getImage());
                    StockDetails.CategoriesBeanX stockDetails = new StockDetails.CategoriesBeanX();
                    stockDetails.setCategories(categories.get(i).getCategories());
                    stockDetails.setName(categories.get(i).getName());
                    stockDetails.setImage(categories.get(i).getImage());
                    stockDetails.setDescription(categories.get(i).getDescription());


                    if(categories.get(i).getCategories().size()>0) {
                        stockDetails.setStockInnerCategoryCount(categories.get(i).getCategories().size() + " Categories");
                    }
                    else
                        {
                        stockDetails.setStockInnerCategoryCount(categories.get(i).getProducts().size() + " Products");
                    }

                    categoriesArrayList.add(stockDetails);
                }

                if(categoriesArrayList.size()>0)
                {
                    int numberOfColumns = 2;
                    recyclerView.setHasFixedSize(false);
                    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
                    recyclerViewAdapter =  new StockDetailsRecyclerViewAdapter(getActivity(),categoriesArrayList);
                    recyclerView.setAdapter(recyclerViewAdapter);

                }
                else
                {
                    Log.d(TAG, "onCreateView: No Array");
                }
            }

        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }




        return root;
    }
}

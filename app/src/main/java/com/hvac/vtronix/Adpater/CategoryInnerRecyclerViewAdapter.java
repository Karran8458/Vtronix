package com.hvac.vtronix.Adpater;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hvac.vtronix.POJOClass.StockDetails;
import com.hvac.vtronix.R;
import com.hvac.vtronix.activity.SecondProductDetailsPage;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryInnerRecyclerViewAdapter extends RecyclerView.Adapter<CategoryInnerRecyclerViewAdapter.ViewHolder> {

    private String TAG = CategoryInnerRecyclerViewAdapter.class.getSimpleName();
    private Context context;
    private List<StockDetails.CategoriesBeanX.CategoriesBean.ProductsBean> productsBeanArrayList;

    public CategoryInnerRecyclerViewAdapter(Context context,
                                            List<StockDetails.CategoriesBeanX.CategoriesBean.ProductsBean> productsBeanArrayList)
    {
        this.context                    =   context;
        this.productsBeanArrayList      =   productsBeanArrayList;
    }

    @NonNull
    @Override
    public CategoryInnerRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_inner_items_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryInnerRecyclerViewAdapter.ViewHolder holder, int position) {

        holder.mItem = productsBeanArrayList.get(position);

        holder.textViewProductName.setText(productsBeanArrayList.get(position).getName());
        holder.textViewDescript.setText(productsBeanArrayList.get(position).getDescription());

        Log.e(TAG, "onBindViewHolder: Image :"+productsBeanArrayList.get(position).getImage());

        Picasso.with(context).load(productsBeanArrayList.get(position).getImage())
                .placeholder(R.mipmap.ic_launcher_round)
                .resize(200, 200)
                .onlyScaleDown() // the image will only be resized if it's bigger than 6000x2000 pixels.
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int adapterPosition = holder.getAdapterPosition();
                Log.d("TAG", "onClick: inside-click : "+adapterPosition);

                StockDetails.CategoriesBeanX.CategoriesBean.ProductsBean productsBean = productsBeanArrayList.get(adapterPosition);

                Intent intent = new Intent(context, SecondProductDetailsPage.class);

                intent.putExtra("ProductName",productsBean.getName());
                intent.putExtra("ProductDesc",productsBean.getDescription());
                intent.putExtra("ProductImageURL",productsBean.getImage());
                intent.putExtra("ProductSpec",productsBean.getSpecs_url());
                intent.putExtra("ProductDiagram",productsBean.getDiagram_url());

                context.startActivity(intent);
            }
        });

    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {

        return productsBeanArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final View mView;
        private final ImageView imageView;
        private final TextView textViewProductName;
        private final TextView textViewDescript;
        StockDetails.CategoriesBeanX.CategoriesBean.ProductsBean mItem;

        private ViewHolder(View view) {
            super(view);

            mView = view;
            imageView                      = view.findViewById(R.id.product_image_inner);
            textViewProductName            = view.findViewById(R.id.textViewProductName_inner);
            textViewDescript               = view.findViewById(R.id.textViewDesc_inner);

        }

    }

}




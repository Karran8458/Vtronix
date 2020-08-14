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

import com.hvac.vtronix.R;
import com.hvac.vtronix.activity.SecondProductDetailsPage;
import com.hvac.vtronix.ui.productSpecs.ProductsSpecsViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductsInnerRecyclerViewAdapter extends RecyclerView.Adapter<ProductsInnerRecyclerViewAdapter.ViewHolder> {

    private String TAG = ProductsInnerRecyclerViewAdapter.class.getSimpleName();
    private Context context;
    private List<ProductsSpecsViewModel> productsBeanArrayList;

    public ProductsInnerRecyclerViewAdapter(Context context, List<ProductsSpecsViewModel> productsBeanArrayList)
    {
        this.context                    =   context;
        this.productsBeanArrayList      =   productsBeanArrayList;
    }

    @NonNull
    @Override
    public ProductsInnerRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_inner_items_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductsInnerRecyclerViewAdapter.ViewHolder holder, int position) {

        holder.mItem = productsBeanArrayList.get(position);

        holder.textViewProductName.setText(productsBeanArrayList.get(position).getProductName());
        holder.textViewDescript.setText(productsBeanArrayList.get(position).getProductDesc());

        Log.e(TAG, "onBindViewHolder: Image :"+productsBeanArrayList.get(position).getProductImage());

        Picasso.with(context).load(productsBeanArrayList.get(position).getProductImage())
                .placeholder(R.mipmap.ic_launcher_round)
                .resize(250, 300)
                .onlyScaleDown() // the image will only be resized if it's bigger than 6000x2000 pixels.
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int adapterPosition = holder.getAdapterPosition();
                Log.d("TAG", "onClick: inside-click : "+adapterPosition);

                ProductsSpecsViewModel productsBean = productsBeanArrayList.get(adapterPosition);

                Intent intent = new Intent(context, SecondProductDetailsPage.class);

                intent.putExtra("ProductName",productsBean.getProductName());
                intent.putExtra("ProductDesc",productsBean.getProductDesc());
                intent.putExtra("ProductImageURL",productsBean.getProductImage());
                intent.putExtra("ProductSpec",productsBean.getProductSpecs());
                intent.putExtra("ProductDiagram",productsBean.getProductDiagram());

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
        ProductsSpecsViewModel mItem;

        private ViewHolder(View view) {
            super(view);

            mView = view;
            imageView                      = view.findViewById(R.id.product_image_inner_product);
            textViewProductName            = view.findViewById(R.id.textViewProductName_inner_product);
            textViewDescript               = view.findViewById(R.id.textViewDesc_inner_product);

        }

    }

}




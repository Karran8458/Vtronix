package com.hvac.vtronix.Adpater;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hvac.vtronix.R;
import com.hvac.vtronix.activity.ProductsSpecsTabDetailsActivity;
import com.hvac.vtronix.ui.productSpecs.ProductsSpecsViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductsSpecDetailsRecyclerViewAdapter extends RecyclerView.Adapter<ProductsSpecDetailsRecyclerViewAdapter.ViewHolder>
        implements Filterable {

    private String TAG = ProductsSpecDetailsRecyclerViewAdapter.class.getSimpleName();
    private Context context;
    private List<ProductsSpecsViewModel> productsBeanArrayList;

    private ValueFilter valueFilter;
    private List<ProductsSpecsViewModel> mStringFilterList;


    public ProductsSpecDetailsRecyclerViewAdapter(Context context, List<ProductsSpecsViewModel> productsBeanArrayList)
    {
        this.context                    =   context;
        this.productsBeanArrayList      =   productsBeanArrayList;
        this.mStringFilterList          =   productsBeanArrayList;
        getFilter();

    }

    @NonNull
    @Override
    public ProductsSpecDetailsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_details_items_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductsSpecDetailsRecyclerViewAdapter.ViewHolder holder, int position) {

        holder.mItem = productsBeanArrayList.get(position);

        holder.textViewProductName.setText(productsBeanArrayList.get(position).getProductName());
        holder.textViewDescript.setText(productsBeanArrayList.get(position).getProductDesc());

        Log.e(TAG, "onBindViewHolder: Image :"+productsBeanArrayList.get(position).getProductImage()
        +"\t ");

        Picasso.with(context).load(productsBeanArrayList.get(position).getProductImage())
                .placeholder(R.mipmap.ic_launcher_round)
                .resize(250, 300)
                .onlyScaleDown() // the image will only be resized if it's bigger than 6000x2000 pixels.
                .into(holder.imageView);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int adapterPosition = holder.getAdapterPosition();
               // Log.d("TAG", "onClick: inside-click : "+adapterPosition);

                ProductsSpecsViewModel productsBean = productsBeanArrayList.get(adapterPosition);

                Intent intent = new Intent(context, ProductsSpecsTabDetailsActivity.class);
                intent.putExtra("ProductName",productsBean.getProductName());
                intent.putExtra("ProductSpec",productsBean.getProductSpecs());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public long getItemId(int position) {
        return position;
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
            imageView                      = view.findViewById(R.id.product_image_model);
            textViewProductName            = view.findViewById(R.id.textViewProductName_model);
            textViewDescript               = view.findViewById(R.id.textViewDesc_model);

        }

    }

    @Override
    public Filter getFilter() {
        if(valueFilter==null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }


    private class ValueFilter extends Filter {
        //Invoked in a worker thread to filter the data according to the constraint.
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results=new FilterResults();
            if(constraint!=null && constraint.length()>0){
                ArrayList<ProductsSpecsViewModel> filterList = new ArrayList<>();
                for(int i=0;i<mStringFilterList.size();i++){
                    //=======Code for Multiple JSON keyword suing EditText Search and Filter Showing Results
                    String str = mStringFilterList.get(i).getProductName();
                    //                            mStringFilterList.get(i).getProductDesc()+""+mStringFilterList.get(i).getName()
                    if(str.toUpperCase().contains(constraint.toString().toUpperCase())) {
                        ProductsSpecsViewModel result_s = new ProductsSpecsViewModel();

                        result_s.setProductName(mStringFilterList.get(i).getProductName());
                        result_s.setProductImage(mStringFilterList.get(i).getProductImage());
                        result_s.setProductDesc(mStringFilterList.get(i).getProductDesc());
                        result_s.setProductDiagram(mStringFilterList.get(i).getProductDiagram());
                        result_s.setProductSpecs(mStringFilterList.get(i).getProductSpecs());
                        result_s.setProductURL(mStringFilterList.get(i).getProductURL());

                        filterList.add(result_s);
                    }
                }
                results.count=filterList.size();
                results.values=filterList;
            }
            else{
                results.count=mStringFilterList.size();
                results.values=mStringFilterList;
            }
            return results;
        }

        //Invoked in the UI thread to publish the filtering results in the user interface.
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            productsBeanArrayList = (ArrayList<ProductsSpecsViewModel>) results.values;
            notifyDataSetChanged();
        }
    }

}




package com.delta_inductions.delta_task_3.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.delta_inductions.delta_task_3.Model.Breeds;
import com.delta_inductions.delta_task_3.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BreedsSearchAdapter extends RecyclerView.Adapter<BreedsSearchAdapter.BreedViewHolder>implements Filterable {
    private ArrayList<Breeds> BreedList;
    private ArrayList<Breeds> BreedListFull;
    private Context mContext;
    private Onitemclicklistener listener;

    public BreedsSearchAdapter(Context context, ArrayList<Breeds> breedsList) {
        mContext = context;
        BreedList = breedsList;
        BreedListFull  = new ArrayList<>(BreedList);
    }

    @NonNull
    @Override
    public BreedsSearchAdapter.BreedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.cardlayout,parent,false);
        return new BreedsSearchAdapter.BreedViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull BreedsSearchAdapter.BreedViewHolder holder, int position) {
        Breeds currentbreed = BreedList.get(position);
        holder.breedname.setText(currentbreed.getName());
        Picasso.get().load(currentbreed.getImage().getUrl()).fit().centerInside().into(holder.imageView);
    }
    @Override
    public int getItemCount() {
        return BreedList.size();
    }

    @Override
    public Filter getFilter() {
        return BreedListFilter;
    }
    private Filter BreedListFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Breeds> filteredList = new ArrayList<>();
            if(constraint==null||constraint.length() == 0 )
            {
                filteredList.addAll(BreedListFull);
            }
            else
            {
                String filterpattern = constraint.toString().toLowerCase().trim();
                for(Breeds item : BreedListFull)
                {
                    if(item.getName().toLowerCase().contains(filterpattern))
                    {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            BreedList.clear();
            BreedList.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };
    public class BreedViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView breedname;

        public BreedViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.breedimage);
            breedname = itemView.findViewById(R.id.breed_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                            listener.itemclickSearch(position);
                    }
                }
            });
        }
    }

    public void setOnitemclicklistenerSearch( Onitemclicklistener onitemclicklistener)
    {
        listener = onitemclicklistener;
    }
}

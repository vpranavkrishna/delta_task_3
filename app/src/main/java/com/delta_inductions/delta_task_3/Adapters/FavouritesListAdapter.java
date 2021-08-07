package com.delta_inductions.delta_task_3.Adapters;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.delta_inductions.delta_task_3.Database.Favourites;
import com.delta_inductions.delta_task_3.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;


public class FavouritesListAdapter extends RecyclerView.Adapter<FavouritesListAdapter.FavouritesViewHolder> implements Filterable {
    private static final String TAG = "Adapter";
    private ArrayList<Favourites> FavouritesList;
    private ArrayList<Favourites> FavouritesListFull;
    private Context mContext;
    private Onitemclicklistnerfav listener;

    public FavouritesListAdapter(Context context, ArrayList<Favourites> favsList) {
        mContext = context;
        FavouritesList = favsList;
        FavouritesListFull  = new ArrayList<>(FavouritesList);
    }
    @NonNull
    @Override
    public FavouritesListAdapter.FavouritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.cardlayout,parent,false);
        return new FavouritesListAdapter.FavouritesViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull FavouritesListAdapter.FavouritesViewHolder holder, int position) {
        Favourites currentbreed = FavouritesList.get(position);
        Log.d(TAG, "onBindViewHolder: "+ currentbreed.getBreedname());
        holder.breedname.setText(currentbreed.getBreedname());
        Picasso.get().load(currentbreed.getImageURL()).fit().centerInside().into(holder.imageView, new Callback() {
            @Override
            public void onSuccess() {
                holder.progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError(Exception e) {
              holder.breedname.setText("Sorry error has occured");
            }
        });
    }
    @Override
    public int getItemCount() {
        return FavouritesList.size();
    }


    private Filter FavouritesListFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Favourites> filteredList = new ArrayList<>();
            if(constraint==null||constraint.length() == 0 )
            {
                filteredList.addAll(FavouritesListFull);
            }
            else
            {
                String filterpattern = constraint.toString().toLowerCase().trim();
                for(Favourites item : FavouritesListFull)
                {
                    if(item.getBreedname().toLowerCase().contains(filterpattern))
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
            FavouritesList.clear();
            FavouritesList.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };

    @Override
    public Filter getFilter() {
        return FavouritesListFilter;
    }

    public class FavouritesViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView breedname;
        private ProgressBar progressBar;
        public FavouritesViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.breedimage);
            breedname = itemView.findViewById(R.id.breed_name);
            progressBar = itemView.findViewById(R.id.progressbar);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                            listener.itemclickfav(position);
                    }
                }
            });
        }
    }

    public void setOnitemclicklistenerfav( Onitemclicklistnerfav onitemclicklistenerfav)
    {
        listener = onitemclicklistenerfav;
    }
}


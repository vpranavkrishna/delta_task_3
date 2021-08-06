package com.delta_inductions.delta_task_3.Adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.solver.state.State;
import androidx.recyclerview.widget.RecyclerView;

import com.delta_inductions.delta_task_3.Database.Favourites;
import com.delta_inductions.delta_task_3.R;
import com.delta_inductions.delta_task_3.Model.Breeds;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BreedsListAdapter extends RecyclerView.Adapter<BreedsListAdapter.BreedViewHolder> {
    private ArrayList<Breeds> BreedList;
    private ArrayList<Breeds> BreedListFull;
    private Context mContext;
    private Onitemclicklistener listener;

    public BreedsListAdapter(Context context, ArrayList<Breeds> breedsList) {
        mContext = context;
        BreedList = breedsList;
        BreedListFull  = new ArrayList<>(BreedList);
    }

    @NonNull
    @Override
    public BreedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.cardlayout,parent,false);
        return new BreedViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull BreedViewHolder holder, int position) {
         Breeds currentbreed = BreedList.get(position);
        holder.breedname.setText(currentbreed.getName());
        Picasso.get().load(currentbreed.getImage().getUrl()).fit().centerInside().into(holder.imageView);
}
    @Override
    public int getItemCount() {
        return BreedList.size();
    }

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
                        listener.itemclickList(position);
                    }
                }
            });
        }
    }

    public void setOnitemclicklistenerList( Onitemclicklistener onitemclicklistener)
    {
        listener = onitemclicklistener;
    }
}


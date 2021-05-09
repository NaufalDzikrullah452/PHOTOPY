package com.example.photopy.ui.collection;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photopy.databinding.ItemCollectionBinding;
import com.example.photopy.databinding.ItemHomeBinding;
import com.example.photopy.model.ModelCollection;
import com.example.photopy.model.ModelPost;
import com.example.photopy.ui.homefragment.HomeAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder> {

    private ItemCollectionBinding binding;
    private ArrayList<ModelCollection> data;

    public CollectionAdapter( ArrayList<ModelCollection> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemCollectionBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CollectionAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
ModelCollection dataMC = data.get(position);
        Picasso.get().load(dataMC.getImageUrl()).into(binding.IDItemCollectionImageView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull ItemCollectionBinding binding) {
            super(binding.getRoot());
        }
    }
}

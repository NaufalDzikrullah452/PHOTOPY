package com.example.photopy.ui.homefragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photopy.R;
import com.example.photopy.databinding.ItemHomeBinding;
import com.example.photopy.model.ModelPost;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private ItemHomeBinding binding;
    private final ArrayList<ModelPost> data;
    private final ListItemClickListener mOnClickListener;

    interface ListItemClickListener {
        void onListItemClick(int position, String uri);
        void onCollectionClick(int position,String uri,String imageID);
        void onLoveClick(int position,String uri,String ImgId);
    }

    public HomeAdapter(ListItemClickListener onClickListener, ArrayList<ModelPost> data) {
        this.mOnClickListener = onClickListener;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelPost datas = data.get(position);
        binding.textView4.setText(datas.getAuthorNAME());
        binding.IDItemBtnLove.setImageResource(R.drawable.ic_baseline_favorite_border_24);
        ImageView target = binding.IDHomeImagePostingan;
        ImageView targetProfil = binding.IDHomeImageUser;
        Picasso.get().load(datas.getAuthorIMG()).resize(30, 30).centerCrop().into(targetProfil);
        Picasso.get().load(datas.getImageURL()).placeholder(R.drawable.blur).into(target);
        holder.imageID = datas.getImageID();

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        String imageID;

        public ViewHolder(@NonNull ItemHomeBinding binding) {
            super(binding.getRoot());
            binding.IDItemBtnDownload.setOnClickListener(this);
            binding.IDItemBtnCollection.setOnClickListener(this);
            binding.IDItemBtnLove.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ID_Item_BtnDownload:
                    int position = getAdapterPosition();
                    mOnClickListener.onListItemClick(position, data.get(position).getImageURL());
                    break;
                case R.id.ID_Item_BtnCollection:
                    int positions = getAdapterPosition();
                    mOnClickListener.onCollectionClick(positions, data.get(positions).getImageURL(),data.get(positions).getImageID());
                case R.id.ID_Item_BtnLove:
                    int positionss = getAdapterPosition();
                    binding.IDItemBtnLove.setImageResource(R.drawable.ic_baseline_favorite_24);
                    mOnClickListener.onLoveClick(positionss, data.get(positionss).getAuthorUID(),data.get(positionss).getImageID());
            }

        }
    }

}

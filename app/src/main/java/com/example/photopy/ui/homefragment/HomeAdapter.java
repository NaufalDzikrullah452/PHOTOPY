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
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private ItemHomeBinding binding;
    private final ArrayList<ModelPost> data;
    private final ListItemClickListener mOnClickListener;

    interface ListItemClickListener{
        void onListItemClick(int position, String uri);
    }
    public HomeAdapter(ListItemClickListener onClickListener,ArrayList<ModelPost> data) {
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
        binding.IDHomeLove.setImageResource(R.drawable.ic_baseline_favorite_border_24);
        ImageView target = binding.IDHomeImagePostingan;
        ImageView targetProfil = binding.IDHomeImageUser;
        Picasso.get().load(datas.getAuthorIMG()).resize(30, 30).centerCrop().into(targetProfil);

        Picasso.get().load(datas.getImageURL()).placeholder(R.drawable.blur).into(target, new com.squareup.picasso.Callback(){
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {

            }
        });

//        binding.IDItemBtnDownload.setOnClickListener(v ->
//        viewModel.downloadImage(datas.getImageURL(), v));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ViewHolder(@NonNull ItemHomeBinding binding) {
            super(binding.getRoot());
            binding.IDItemBtnDownload.setOnClickListener(this);
            binding.IDHomeLove.setOnClickListener(new View.OnClickListener() {
                boolean love = false;
                int click = 0;

                @Override
                public void onClick(View v) {
                    click++;
                    if (click == 2) {
                        if (!love) {
                            love = true;
                            click = 0;
                            binding.IDHomeLove.setImageResource(R.drawable.ic_baseline_favorite_24);

                        } else {
                            click = 0;
                            love = false;
                            binding.IDHomeLove.setImageResource(R.drawable.ic_baseline_favorite_border_24);

                        }
                    }
                }
            });

            binding.IDHomeImagePostingan.setOnClickListener(new View.OnClickListener() {
                boolean love = false;
                int click = 0;

                @Override
                public void onClick(View v) {
                    click++;
                    if (click == 2) {
                        if (!love) {
                            love = true;
                            click = 0;
                            binding.IDHomeLove.setImageResource(R.drawable.ic_baseline_favorite_24);

                        } else {
                            click = 0;
                            love = false;
                            binding.IDHomeLove.setImageResource(R.drawable.ic_baseline_favorite_border_24);

                        }
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mOnClickListener.onListItemClick(position, data.get(position).getImageURL());
        }
    }

}

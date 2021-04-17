package com.example.photopy.ui.homefragment;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photopy.R;
import com.example.photopy.databinding.ItemHomeBinding;
import com.example.photopy.lib.ModelPost;
import com.example.photopy.lib.ModelProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private ItemHomeBinding binding;
    private final ArrayList<ModelPost> data;
    int click = 0;
    boolean love = false;
    String url = "";
    String uids;

    public HomeAdapter(ArrayList<ModelPost> data) {
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
        binding.textView4.setText(datas.getAuthorUID());
        binding.IDHomeLove.setImageResource(R.drawable.ic_baseline_favorite_border_24);
        ImageView target = binding.IDHomeImagePostingan;
        Picasso.get().load(datas.getImageURL()).into(target);
        holder.data(datas.getAuthorUID());


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public void data(String uid) {

            uids = uid;

        }

        public ViewHolder(@NonNull ItemHomeBinding binding) {
            super(binding.getRoot());
            binding.IDHomeImagePostingan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    click++;
                    if (click == 2) {
                        click = 0;
                        if (!love) {
                            love = true;
                            binding.IDHomeLove.setImageResource(R.drawable.ic_baseline_favorite_24);
                        } else {
                            binding.IDHomeLove.setImageResource(R.drawable.ic_baseline_favorite_border_24);

                        }
                    }
                }
            });

            Log.d("HomeAdapter", "Uid = " + uids);
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            DocumentReference docRef = db.collection("Profile").document(uid);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d("HomeAdapterFirestore", "" + document.getData());
                            ModelProfile profile = document.toObject(ModelProfile.class);
                            url = profile.getImageAuthor();
                            ImageView targetProfile = binding.IDHomeImageUser;
                            Picasso.get().load(url).into(targetProfile);
                            Log.d("HomeAdapterImage", url);
                        } else {
                            Log.d("HomeAdapterFirestore", "No such document");
                        }
                    }
                }

            });

        }

    }


}
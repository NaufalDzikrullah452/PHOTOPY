package com.example.photopy.ui.collection;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.photopy.R;
import com.example.photopy.databinding.FragmentCollectionBinding;
import com.example.photopy.model.ModelCollection;
import com.example.photopy.model.ModelPost;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CollectionFragment extends Fragment {

    private FragmentCollectionBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCollectionBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList data = new ArrayList<ModelCollection>();
//
//        data.add(new ModelCollection("AqbFYCIBuTM4AeHTUZOFrJEPmuB3", "MZSnwX1qnPXykaP6hoJ", "https://firebasestorage.googleapis.com/v0/b/database1-a8d71.appspot.com/o/PhotoPy%2F2fbb6adc-428a-4886-8b1d-b736769d2240?alt=media&token=ba601639-eeca-4f43-b6e2-57e5688e7218"));
//        data.add(new ModelCollection("AqbFYCIBuTM4AeHTUZOFrJEPmuB3", "MZSnwX1qnPXykaP6hoJ", "https://firebasestorage.googleapis.com/v0/b/database1-a8d71.appspot.com/o/PhotoPy%2F9c812267-88e6-40cc-81a9-9a00bc9e809b?alt=media&token=d74cd437-75a8-4267-a356-0b31e6d8dab4"));
//        data.add(new ModelCollection("AqbFYCIBuTM4AeHTUZOFrJEPmuB3", "MZSnwX1qnPXykaP6hoJ", "https://firebasestorage.googleapis.com/v0/b/database1-a8d71.appspot.com/o/PhotoPy%2F9c812267-88e6-40cc-81a9-9a00bc9e809b?alt=media&token=d74cd437-75a8-4267-a356-0b31e6d8dab4"));
//        data.add(new ModelCollection("AqbFYCIBuTM4AeHTUZOFrJEPmuB3", "MZSnwX1qnPXykaP6hoJ", "https://firebasestorage.googleapis.com/v0/b/database1-a8d71.appspot.com/o/PhotoPy%2F2fbb6adc-428a-4886-8b1d-b736769d2240?alt=media&token=ba601639-eeca-4f43-b6e2-57e5688e7218"));
//        data.add(new ModelCollection("AqbFYCIBuTM4AeHTUZOFrJEPmuB3", "MZSnwX1qnPXykaP6hoJ", "https://firebasestorage.googleapis.com/v0/b/database1-a8d71.appspot.com/o/PhotoPy%2F9c812267-88e6-40cc-81a9-9a00bc9e809b?alt=media&token=d74cd437-75a8-4267-a356-0b31e6d8dab4"));
//        data.add(new ModelCollection("AqbFYCIBuTM4AeHTUZOFrJEPmuB3", "MZSnwX1qnPXykaP6hoJ", "https://firebasestorage.googleapis.com/v0/b/database1-a8d71.appspot.com/o/PhotoPy%2F2fbb6adc-428a-4886-8b1d-b736769d2240?alt=media&token=ba601639-eeca-4f43-b6e2-57e5688e7218"));
//        data.add(new ModelCollection("AqbFYCIBuTM4AeHTUZOFrJEPmuB3", "MZSnwX1qnPXykaP6hoJ", "https://firebasestorage.googleapis.com/v0/b/database1-a8d71.appspot.com/o/PhotoPy%2F9c812267-88e6-40cc-81a9-9a00bc9e809b?alt=media&token=d74cd437-75a8-4267-a356-0b31e6d8dab4"));
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        binding.IDCollectionRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        FirebaseDatabase.getInstance().getReference().child("PHOTOPY").child("Collection").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    ModelCollection collection = itemSnapshot.getValue(ModelCollection.class);
                    data.add(collection);
                }
                CollectionAdapter adapter = new CollectionAdapter(data);
                binding.IDCollectionRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
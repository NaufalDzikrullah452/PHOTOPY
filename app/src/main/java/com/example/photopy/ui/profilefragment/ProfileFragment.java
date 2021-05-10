package com.example.photopy.ui.profilefragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.photopy.R;
import com.example.photopy.databinding.FragmentProfileBinding;
import com.example.photopy.lib.GoogleSignInActivity;
import com.example.photopy.lib.viewModel;
import com.example.photopy.model.ModelCollection;
import com.example.photopy.model.ModelPost;
import com.example.photopy.model.ModelProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProfileFragment extends GoogleSignInActivity {
    private FragmentProfileBinding binding;
    private final viewModel viewModel = new viewModel();
    int totalLike =0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(getLayoutInflater(), container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Photopy");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.IDProfileCardCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_profile_to_collectionFragment);
            }
        });

        binding.profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHead = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intentHead, 101);


            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        binding.IDProfilAuthorName.setText(user.getDisplayName());

        viewModel.getProfile().observe(requireActivity(), new Observer<ModelProfile>() {
            @Override
            public void onChanged(ModelProfile modelProfile) {
                Picasso.get().load(modelProfile.getImageAuthor()).placeholder(R.drawable.blur).into(binding.profilePic);
            }
        });

        viewModel.getDataCollectionByID().observe(requireActivity(), new Observer<ArrayList<ModelCollection>>() {
            @Override
            public void onChanged(ArrayList<ModelCollection> modelCollections) {
                binding.totalCollection.setText(String.valueOf(modelCollections.size()));
            }
        });
        viewModel.getDataByUid().observe(requireActivity(), new Observer<ArrayList<ModelPost>>() {
            @Override
            public void onChanged(ArrayList<ModelPost> modelPosts) {
                int total = modelPosts.size();
                binding.totalPhotos.setText(String.valueOf(total));
            }
        });
        binding.IDProfilBtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_profile_to_profileSettingFragment);
            }
        });

        FirebaseDatabase.getInstance().getReference().child("PHOTOPY").child("Like").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    totalLike = totalLike+1;
                }
                binding.totalLikes.setText(String.valueOf(totalLike));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 101) && (resultCode == Activity.RESULT_OK) && data != null) {
            viewModel.setImage(data.getData().toString());
        }
    }
}
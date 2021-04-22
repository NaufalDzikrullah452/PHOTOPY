package com.example.photopy.ui.addfragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.photopy.R;
import com.example.photopy.databinding.FragmentAddBinding;
import com.example.photopy.databinding.FragmentHomeBinding;
import com.example.photopy.lib.viewModel;
import com.example.photopy.model.ModelProfile;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;


public class AddFragment extends Fragment {

    private FragmentAddBinding binding;
    private Uri selectedUri;
    private viewModel viewModel = new viewModel();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.IDAddBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHead = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intentHead, 100);
            }
        });
        binding.IDAddBtnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    viewModel.getProfile().observe(requireActivity(), new Observer<ModelProfile>() {
                        @Override
                        public void onChanged(ModelProfile modelProfile) {
                            if (modelProfile.getAuthorUID()!=null) {
                                Log.d("HomeFragment", "Firestore IMG " + modelProfile.getImageAuthor());
                                viewModel.addPhotoStorage(selectedUri,modelProfile.getImageAuthor());
                            }
                        }
                    });
                }catch (Exception e){

                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 100) && (resultCode == Activity.RESULT_OK) && data != null) {
            binding.IDAddImage.setImageURI(data.getData());
            selectedUri = data.getData();
        }
    }


}
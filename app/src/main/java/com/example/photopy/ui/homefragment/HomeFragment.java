package com.example.photopy.ui.homefragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.photopy.databinding.FragmentHomeBinding;
import com.example.photopy.lib.viewModel;
import com.example.photopy.model.ModelPost;
import com.example.photopy.model.ModelProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;


public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private viewModel ViewModel = new viewModel();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        binding.simmerView.startShimmer();
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(requireContext(),"ResumeHome",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        Toast.makeText(requireContext(),"PauseHome",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {

            ViewModel.getDataPost().observe(requireActivity(), new Observer<ArrayList<ModelPost>>() {
                @Override
                public void onChanged(ArrayList<ModelPost> modelPosts) {
                    HomeAdapter adapter = new HomeAdapter(modelPosts);
                    binding.IDHomeRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
                    binding.IDHomeRecyclerview.setAdapter(adapter);
                    binding.simmerView.stopShimmer();
                    binding.IDHomeContainerSimmer.setVisibility(View.GONE);
                }
            });


        } catch (Exception e) {
            Log.d("HomeFragment", e.toString());
        }
        try {
            ViewModel.getProfile().observe(requireActivity(), new Observer<ModelProfile>() {
                @Override
                public void onChanged(ModelProfile modelProfile) {
                    if (modelProfile.getAuthorUID()!=null) {
                        Log.d("HomeFragment", "Firestore UID " + modelProfile.getAuthorUID());
                    }else {
                        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        CollectionReference dbCourses = db.collection("Profile/");
                        ModelProfile profil = new ModelProfile(uid,null, "Ismail");
                        dbCourses.document(uid).set(profil);
                    }
                }
            });
        }catch (Exception e){

        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        ModelPost mdata = new ModelPost(user.getUid(), user.getDisplayName(), "https://firebasestorage.googleapis.com/v0/b/database1-a8d71.appspot.com/o/Photopy%2Fpalma-de-mallorca-2900559_1920.jpg?alt=media&token=4e10500b-4ce4-43c5-a46a-0fcc1f1aafdd", "https://firebasestorage.googleapis.com/v0/b/database1-a8d71.appspot.com/o/Photopy%2Fpalma-de-mallorca-2900559_1920.jpg?alt=media&token=4e10500b-4ce4-43c5-a46a-0fcc1f1aafdd", "sas");

    }

    }

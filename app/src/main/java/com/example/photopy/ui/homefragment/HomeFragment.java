package com.example.photopy.ui.homefragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.photopy.R;
import com.example.photopy.databinding.FragmentHomeBinding;
import com.example.photopy.lib.viewModel;
import com.example.photopy.model.ModelProfile;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class HomeFragment extends Fragment implements HomeAdapter.ListItemClickListener {
    private FragmentHomeBinding binding;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final viewModel ViewModel = new viewModel();
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        binding.simmerView.startShimmer();
        binding.IDHomePopup.IDHomeContainerPopup.setVisibility(View.GONE);
        binding.IDHomePopup.IDHomePopupCardView.setAlpha(0);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Photopy");
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {

            ViewModel.getDataPost().observe(requireActivity(), modelPosts -> {
                HomeAdapter adapter = new HomeAdapter(this, modelPosts);
                Log.d("Data Uid", adapter.toString());
                binding.IDHomeRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.IDHomeRecyclerview.setAdapter(adapter);
                binding.simmerView.stopShimmer();
                binding.IDHomeContainerSimmer.setVisibility(View.GONE);
            });

        } catch (Exception e) {
            Log.d("HomeFragment", e.toString());
        }


    }


    @Override
    public void onListItemClick(int position, String uri) {

        binding.IDHomePopup.IDHomeContainerPopup.setVisibility(View.VISIBLE);
        binding.IDHomePopup.IDHomePopupLinear.setVisibility(View.VISIBLE);
        binding.IDHomePopup.IDHomePopupCardView.setAlpha(1);
        binding.IDHomePopup.IDHomePopupCardView.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.from_small));
        ImageView target = binding.IDHomePopup.IDHomePopupImageView;
        Picasso.get().load(uri).resize(300, 300).centerCrop().placeholder(R.drawable.blur).into(target);
        YoYo.with(Techniques.Bounce).duration(700).repeat(5).playOn(binding.IDHomePopup.IDHomePopupText);
        binding.IDHomePopup.IDHomePopupBtnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewModel.downloadImage(uri, v);
                binding.IDHomePopup.IDHomePopupCardView.startAnimation(AnimationUtils.loadAnimation(requireActivity(), R.anim.exit));
                binding.IDHomePopup.IDHomePopupLinear.setVisibility(View.GONE);
                ViewCompat.animate(binding.IDHomePopup.IDHomePopupCardView).setStartDelay(1000).alpha(0).start();
            }
        });
        binding.IDHomePopup.IDHomePopupBtnBack.setOnClickListener(v -> {
            binding.IDHomePopup.IDHomePopupCardView.startAnimation(AnimationUtils.loadAnimation(requireActivity(), R.anim.exit));
            binding.IDHomePopup.IDHomePopupLinear.setVisibility(View.GONE);
            ViewCompat.animate(binding.IDHomePopup.IDHomePopupCardView).setStartDelay(1000).alpha(0).start();
        });
    }

    @Override
    public void onCollectionClick(int position, String uri, String imageID) {
        binding.IDHomePopup.IDHomeContainerPopup.setVisibility(View.VISIBLE);
        binding.IDHomePopup.IDHomePopupLinear.setVisibility(View.VISIBLE);
        binding.IDHomePopup.IDHomePopupCardView.setAlpha(1);
        binding.IDHomePopup.IDHomePopupBtnDownload.setText("Save Collection");
        binding.IDHomePopup.IDHomePopupCardView.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.from_small));
        ImageView target = binding.IDHomePopup.IDHomePopupImageView;
        Picasso.get().load(uri).resize(300, 300).centerCrop().placeholder(R.drawable.blur).into(target);
        YoYo.with(Techniques.Bounce).duration(700).repeat(5).playOn(binding.IDHomePopup.IDHomePopupText);
        binding.IDHomePopup.IDHomePopupBtnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewModel.addCollection(uri, imageID);
                binding.IDHomePopup.IDHomePopupCardView.startAnimation(AnimationUtils.loadAnimation(requireActivity(), R.anim.exit));
                binding.IDHomePopup.IDHomePopupLinear.setVisibility(View.GONE);
                ViewCompat.animate(binding.IDHomePopup.IDHomePopupCardView).setStartDelay(1000).alpha(0).start();
            }
        });
        binding.IDHomePopup.IDHomePopupBtnBack.setOnClickListener(v -> {
            binding.IDHomePopup.IDHomePopupCardView.startAnimation(AnimationUtils.loadAnimation(requireActivity(), R.anim.exit));
            binding.IDHomePopup.IDHomePopupLinear.setVisibility(View.GONE);
            ViewCompat.animate(binding.IDHomePopup.IDHomePopupCardView).setStartDelay(1000).alpha(0).start();
        });
    }

    @Override
    public void onLoveClick(int position, String uri, String ImgId) {
        Toast.makeText(requireContext(), "Cobaa", Toast.LENGTH_SHORT).show();
    }
}

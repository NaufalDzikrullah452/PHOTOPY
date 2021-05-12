package com.example.photopy.ui.profilefragment.setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.photopy.R;
import com.example.photopy.databinding.FragmentProfileSettingBinding;
import com.example.photopy.lib.viewModel;
import com.example.photopy.model.ModelProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;


public class ProfileSettingFragment extends Fragment {
    private FragmentProfileSettingBinding binding;
    private SharedPreferences sharedPreferences;
    private final viewModel viewModel = new viewModel();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileSettingBinding.inflate(getLayoutInflater(),container,false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = getContext().getSharedPreferences("Data_Login", Context.MODE_PRIVATE);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        binding.IDProfilSettingName.setText(user.getDisplayName());
        binding.IDProfileSettingUsername.setText(user.getDisplayName());
        viewModel.getProfile().observe(requireActivity(), new Observer<ModelProfile>() {
            @Override
            public void onChanged(ModelProfile modelProfile) {
                Picasso.get().load(modelProfile.getImageAuthor()).placeholder(R.drawable.blur).into(binding.profilePic);
            }
        });
        binding.IDProfileSettingEmail.setText(user.getEmail());
        binding.IDBtnLogoutSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Navigation.findNavController(v).navigate(R.id.action_profileSettingFragment_to_loginFragment);
            }
        });
        binding.IDBtnAboutSettings.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_profileSettingFragment_to_aboutPrivacyFragment));
        binding.IDBtnPrivacy.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_profileSettingFragment_to_termFragment));
    }
}
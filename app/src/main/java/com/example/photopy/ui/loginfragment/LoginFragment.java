package com.example.photopy.ui.loginfragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.photopy.R;
import com.example.photopy.databinding.FragmentLoginBinding;
import com.example.photopy.lib.Repo;
import com.example.photopy.lib.viewModel;
import com.example.photopy.model.ModelProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.jetbrains.annotations.NotNull;


public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
private SharedPreferences sharedPreferences;
    private final viewModel ViewModel = new viewModel();
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        checkLogin(view);
        binding.IDLoginBtnSignEmail.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_signInFragment));
        binding.tvSignUp.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_signUpFragment));
    }

    private void checkLogin(View view) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_navigation_home);
        }
    }


    public void saveData(){
        sharedPreferences = getContext().getSharedPreferences("Data_Login", Context.MODE_PRIVATE);
        ViewModel.getProfile().observe(requireActivity(), new Observer<ModelProfile>() {
            @Override
            public void onChanged(ModelProfile modelProfile) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("authorName", modelProfile.getAuthorName());
                editor.putString("authorUID", modelProfile.getAuthorUID());
                editor.putString("imageAuthor", modelProfile.getImageAuthor());
                editor.apply();
            }
        });

    }



}
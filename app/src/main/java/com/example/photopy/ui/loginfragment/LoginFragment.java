package com.example.photopy.ui.loginfragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.renderscript.ScriptGroup;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.ModalDialog;
import com.example.photopy.R;
import com.example.photopy.databinding.FragmentLoginBinding;
import com.example.photopy.lib.Repo;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addData();
        checkLogin(view);
        binding.IDLoginBtnSignEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_signInFragment);
//                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
//                builder.setView(binding.getRoot()).setTitle("Sign").setMessage("Enter your basic details")
//                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        }).show();

            }
        });
        binding.tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_signUpFragment);

            }
        });
    }

    private void checkLogin(View view) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            Repo repo = new Repo();
            repo.init(currentUser.getUid());
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_navigation_home);
        }
    }

    public void addData() {
        DatabaseReference ref = database.getReference("PHOTOPY/saving-data");
        ref.setValue("Haloo");
    }
}
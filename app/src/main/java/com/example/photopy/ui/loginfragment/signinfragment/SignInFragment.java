package com.example.photopy.ui.loginfragment.signinfragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.photopy.R;
import com.example.photopy.databinding.FragmentSignInBinding;
import com.example.photopy.lib.GoogleSignInActivity;
import com.example.photopy.lib.viewModel;
import com.example.photopy.model.ModelProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignInFragment extends GoogleSignInActivity {

    private FragmentSignInBinding binding;
    private FirebaseAuth mAuth;
    private SharedPreferences sharedPreferences;
    private final viewModel ViewModel = new viewModel();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(getLayoutInflater(), container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        binding.IDSignInBtnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.IDSignInEdtEmail.getText().toString();
                String password = binding.IDSignInEdtPassword.getText().toString();
                if (email.equals("") || password.equals("")) {
                    Toast.makeText(requireContext(), "Isi Field", Toast.LENGTH_LONG).show();

                } else {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("SignIn", "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        saveData();
                                        Navigation.findNavController(v).navigate(R.id.action_signInFragment_to_navigation_home);


                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("SignIn", "signInWithEmail:failure", task.getException());
                                        Toast.makeText(requireContext(), "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        binding.IDSignInBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_signInFragment_to_loginFragment);
            }
        });
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
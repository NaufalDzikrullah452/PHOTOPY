package com.example.photopy.ui.loginfragment.signupfragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.photopy.R;
import com.example.photopy.databinding.FragmentSignUpBinding;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;


public class SignUpFragment extends Fragment {
    private FirebaseAuth mAuth;
    private FragmentSignUpBinding binding;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        binding.IDSignUpBtnBack.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_signUpFragment_to_loginFragment));
        binding.IDSignUpBtnSignUp.setOnClickListener(v -> {
            String username = binding.IDSignUpEdtUsername.getText().toString();
            String email = binding.IDSignUpEdtEmail.getText().toString();
            String password = binding.IDSignUpEdtPassword.getText().toString();
            if(username.equals("")||email.equals("")||password.equals("")){
                Toast.makeText(requireContext(),"Isi Field",Toast.LENGTH_LONG).show();
            }else {

                Log.d("SignUp",username+email+password);
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(requireActivity(), task -> {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("SignUp", "createUserWithEmail:success");
//                                FirebaseUser user = mAuth.getCurrentUser();
                                Navigation.findNavController(v).navigate(R.id.action_signUpFragment_to_loginFragment);

//                            updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("SignUp", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(requireContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
package com.example.photopy.ui.addfragment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.photopy.databinding.FragmentAddBinding;
import org.jetbrains.annotations.NotNull;

public class AddFragment extends Fragment {

    private FragmentAddBinding binding;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }





}
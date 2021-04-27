package com.example.photopy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.example.photopy.databinding.ActivityMainBinding;
import com.example.photopy.lib.viewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private final viewModel viewModel = new viewModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.getMenu().getItem(1).setEnabled(false);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_add, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController( binding.bottomNavigationView, navController);
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
           switch (destination.getId()){
               case R.id.loginFragment: binding.coordinatorLayout.setVisibility(View.GONE); break;
               case R.id.navigation_home:
               case R.id.navigation_profile:
               case R.id.navigation_add:
                   binding.coordinatorLayout.setVisibility(View.VISIBLE); break;

           }
        });
        binding.fab.setOnClickListener(v -> {
            Intent intentHead = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(intentHead, 100);
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 100) && (resultCode == Activity.RESULT_OK) && data != null) {
            viewModel.getProfile().observe(this, modelProfile -> {
                if (modelProfile.getAuthorUID()!=null) {
                    Log.d("HomeFragment", "Firestore IMG " + modelProfile.getImageAuthor());
                    viewModel.addPhotoStorage(data.getData(),modelProfile.getImageAuthor());
                }
            });
        }
    }
}
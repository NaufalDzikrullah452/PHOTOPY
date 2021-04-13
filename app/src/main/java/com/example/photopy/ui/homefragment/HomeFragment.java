package com.example.photopy.ui.homefragment;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.photopy.R;
import com.example.photopy.databinding.FragmentHomeBinding;
import com.example.photopy.databinding.FragmentLoginBinding;
import com.example.photopy.databinding.FragmentSignInBinding;
import com.example.photopy.lib.ModelPost;
import com.example.photopy.lib.ModelProfile;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    final FirebaseDatabase realtime = FirebaseDatabase.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    DatabaseReference ref = realtime.getReference("PHOTOPY/Picture");
    ArrayList<ModelPost> data = new ArrayList<>();
    long count = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkProfile();
        getData();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        String password = user.getProviderId();

        Log.d("HomeFragment",user+password);

        ModelPost mdata = new ModelPost("Ismail","https://firebasestorage.googleapis.com/v0/b/database1-a8d71.appspot.com/o/Photopy%2Fpalma-de-mallorca-2900559_1920.jpg?alt=media&token=4e10500b-4ce4-43c5-a46a-0fcc1f1aafdd","Hello Word","sas");

    }

    private void getData() {

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                count = snapshot.getChildrenCount();
                binding.IDHomeRecyclerview.removeAllViews();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    ModelPost post = itemSnapshot.getValue(ModelPost.class);
                    data.add(new ModelPost(post.getAuthorUID(),post.getImageURL(),post.getImageCaption(),  post.getLike()));
                    Log.d("HomeFragment", post.getAuthorUID());
                }
                HomeAdapter adapter = new HomeAdapter(data);
                binding.IDHomeRecyclerview.setAdapter(adapter);
                binding.IDHomeRecyclerview.setLayoutManager(new LinearLayoutManager(requireContext()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        double i = Math.floor(Math.random() * count);
        int value = (int) i;
        ref.limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void checkProfile() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        CollectionReference dbCourses = db.collection("Profile/");
        dbCourses.document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot != null) {
                    return;
                } else {
                    setUpData();
                }
            }
        });
    }

    private void setUpData() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        CollectionReference dbCourses = db.collection("Profile/");

        ModelProfile profil = new ModelProfile(uid, "asas");
        dbCourses.document(uid).set(profil);
//        dbCourses.add(profil).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//            @Override
//            public void onSuccess(DocumentReference documentReference) {
//                Toast.makeText(requireContext(),"Succes Saved Firestore",Toast.LENGTH_LONG).show();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(requireContext(),e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
//            }
//        });
    }
}
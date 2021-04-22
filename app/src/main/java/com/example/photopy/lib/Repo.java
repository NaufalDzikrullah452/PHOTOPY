package com.example.photopy.lib;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.photopy.model.ModelPost;
import com.example.photopy.model.ModelProfile;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.UUID;


public class Repo {

    private FirebaseDatabase realtime = FirebaseDatabase.getInstance();
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private DatabaseReference ref = realtime.getReference("PHOTOPY/Picture");
    private CollectionReference refFirestore = firestore.collection("Profile/");
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference("/PhotoPy/" + UUID.randomUUID().toString());
    String currentUserUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    public String uid, dataUri,authorIMG;


    public void init(String dataUid) {
        Repo repo = new Repo();
         repo.uid = dataUid;
    }

    public String addPhotoPostRealtime(ModelPost modelPost) {
        final MutableLiveData<ModelPost> mutableLiveData = new MutableLiveData<>();

        ref.push().setValue(modelPost);
        return "Succes add";
    }

    public String addPhotoStorage(Uri uri,String authorIMG) {
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Log.d("AddFragment", "Succesfull to upload image: " + taskSnapshot.getMetadata().getPath());
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    String dataUriw;

                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d("AddFragment", "File Location " + uri);
                        dataUri = uri.toString();
                        ModelPost data = new ModelPost(currentUserUid, "Ismaillll", authorIMG, uri.toString(), "w");
                        ref.push().setValue(data);

                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("AddFragment", e.toString());
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("AddFragment", e.toString());
            }
        });
        return dataUri;
    }

    public MutableLiveData<ArrayList<ModelPost>> requestDataPost() {
        final MutableLiveData<ArrayList<ModelPost>> mutableLiveData = new MutableLiveData<>();

        ref.addValueEventListener(new ValueEventListener() {
            ArrayList data = new ArrayList<ModelPost>();

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data.clear();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    ModelPost post = itemSnapshot.getValue(ModelPost.class);
                    data.add(new ModelPost(post.getAuthorUID(), post.getAuthorNAME(), post.getAuthorIMG(), post.getImageURL(), post.getLike()));
                }
                mutableLiveData.setValue(data);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return mutableLiveData;
    }

    public MutableLiveData<ModelProfile> getAccountFirestore() {
        final MutableLiveData<ModelProfile> mutableLiveData = new MutableLiveData<>();


        refFirestore.document(currentUserUid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            ModelProfile profil = new ModelProfile();

            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    profil = documentSnapshot.toObject(ModelProfile.class);
                    authorIMG = profil.getImageAuthor();
                }
                mutableLiveData.setValue(profil);
            }
        });


        return mutableLiveData;
    }

}

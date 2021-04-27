package com.example.photopy.lib;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;

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

import static android.os.Environment.DIRECTORY_DOWNLOADS;


public class Repo {

    private final FirebaseDatabase realtime = FirebaseDatabase.getInstance();
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private final DatabaseReference ref = realtime.getReference("PHOTOPY/Picture");
    private final CollectionReference refFirestore = firestore.collection("Profile/");
    private final StorageReference storageReference = FirebaseStorage.getInstance().getReference("/PhotoPy/" + UUID.randomUUID().toString());

    public String uid, dataUri, authorIMG;


    public void init(String dataUid) {
        Repo repo = new Repo();
        repo.uid = dataUid;
    }

    public String addPhotoPostRealtime(ModelPost modelPost) {
        final MutableLiveData<ModelPost> mutableLiveData = new MutableLiveData<>();

        ref.push().setValue(modelPost);
        return "Succes add";
    }

    //Menambah data ke storage dan mengirim data postingan
    public String addPhotoStorage(Uri uri, String authorIMG) {
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
                        String currentUserUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        ModelPost data = new ModelPost(currentUserUid, "Ismaillll", authorIMG, uri.toString(), 3);
                        ref.push().setValue(data);

                    }

                }).addOnFailureListener(e -> Log.d("AddFragment", e.toString()));

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("AddFragment", e.toString());
            }
        });
        return dataUri;
    }

    //Get data postingan real-time Database
    public MutableLiveData<ArrayList<ModelPost>> requestDataPost() {
        final MutableLiveData<ArrayList<ModelPost>> mutableLiveData = new MutableLiveData<>();

        ref.addValueEventListener(new ValueEventListener() {
            final ArrayList data = new ArrayList<ModelPost>();

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

    //Get data Account FireStore
    public MutableLiveData<ModelProfile> getAccountFirestore() {
        final MutableLiveData<ModelProfile> mutableLiveData = new MutableLiveData<>();
        String currentUserUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
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

    //Get Post By Uid
    public MutableLiveData<ArrayList<ModelPost>> getDataByUid() {
        Log.d("Data Uid", "Gasss");
        final MutableLiveData<ArrayList<ModelPost>> mutableLiveData = new MutableLiveData<>();
        ref.orderByChild("authorUID").equalTo("iX9xlpDc3ghFT3La1tSYjWU5Vgi1").addValueEventListener(new ValueEventListener() {
            final ArrayList data = new ArrayList<ModelPost>();

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
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

    public void updateDataByUid(ModelPost data) {
        ref.orderByChild("authorUID").equalTo("sas").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    itemSnapshot.getRef().setValue(data);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void downloadImage(String url, View view){
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("Downloading");
        request.setDescription("Downloading file ...");
        request.allowScanningByMediaScanner();
        request.setMimeType("image/jpeg");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(DIRECTORY_DOWNLOADS, "" + System.currentTimeMillis() + ".jpg");
        DownloadManager manager = (DownloadManager) view.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }
}

package com.example.photopy.ui.homefragment;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photopy.R;
import com.example.photopy.databinding.ItemHomeBinding;
import com.example.photopy.model.ModelPost;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.ArrayList;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private ItemHomeBinding binding;
    private final ArrayList<ModelPost> data;
    private ItemClickListener mItemClickListener;

    public HomeAdapter(ArrayList<ModelPost> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    public void addItemClickListener(ItemClickListener listener) {
        mItemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelPost datas = data.get(position);
        binding.textView4.setText(datas.getAuthorNAME());
        binding.IDHomeLove.setImageResource(R.drawable.ic_baseline_favorite_border_24);
        ImageView target = binding.IDHomeImagePostingan;
        ImageView targetProfil = binding.IDHomeImageUser;

        Picasso.get().load(datas.getAuthorIMG()).resize(30,30).centerCrop().into(targetProfil, new com.squareup.picasso.Callback(){

            @Override
            public void onSuccess() {
                Log.d("HomeAdapter","Suzzesss");
            }

            @Override
            public void onError(Exception e) {

            }
        });

        Picasso.get().load(datas.getImageURL()).placeholder(R.drawable.blur).into(target);
        binding.IDItemBtnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starDownlaoding(datas.getImageURL(), v);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public interface ItemClickListener {
        void onItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(@NonNull ItemHomeBinding binding) {
            super(binding.getRoot());
            binding.IDHomeImagePostingan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickLove();
                }
            });


        }

    }

    private void starDownlaoding(String url, View view) {

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

    public String convertUrlToBase64(String url) {
        URL newurl;
        Bitmap bitmap;
        String base64 = "";
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            newurl = new URL(url);
            bitmap = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            base64 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return base64;
    }

    private void clickLove() {
        int click = 0;
        boolean love = false;
        click++;

        if (click == 2) {
            click = 0;
            if (!love) {
                love = true;
                binding.IDHomeLove.setImageResource(R.drawable.ic_baseline_favorite_24);
            } else {
                love = false;
                binding.IDHomeLove.setImageResource(R.drawable.ic_baseline_favorite_border_24);

            }
        }
    }


}

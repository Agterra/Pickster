package com.example.agterra.pickster;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.GridView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.jar.Manifest;

public class PhotoGridActivity extends AppCompatActivity {

    public static String IMAGE_URI_INTENT = "imageURI";

    public static int READ_EXTERNAL_STORAGE;

    public static int WRITE_EXTERNAL_STORAGE;

    private ArrayList<String> imagesURI;

    private GridView photoGridView;

    private ImageAdapter imageAdapter;

    private Activity currentActivity;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_photo_grid);

        IntentFilter filter = new IntentFilter(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);

        MyReceiver mr = new MyReceiver();

        getApplicationContext().registerReceiver(mr, filter);

        this.currentActivity = this;

        int readPermissionCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);

        if(readPermissionCheck != PackageManager.PERMISSION_GRANTED)
        {

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_EXTERNAL_STORAGE
            );

        }

        int writePermissionCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(writePermissionCheck != PackageManager.PERMISSION_GRANTED)
        {

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_EXTERNAL_STORAGE
            );

        }

        this.imagesURI = getAllShownImagesPath(this);

        this.imageAdapter = new ImageAdapter(this, this.imagesURI);

        this.photoGridView = (GridView)findViewById(R.id.photoGridView);

        photoGridView.setAdapter(this.imageAdapter);

        photoGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getBaseContext(), PhotoDetail.class);

                intent.putExtra(IMAGE_URI_INTENT, imagesURI.get(i));

                startActivity(intent);

            }

        });

        this.swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshSwap);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {

                imagesURI = getAllShownImagesPath(currentActivity);

                imageAdapter = new ImageAdapter(currentActivity, imagesURI);

                imageAdapter.notifyDataSetChanged();

                photoGridView.setAdapter(imageAdapter);

                swipeRefreshLayout.setRefreshing(false);

            }

        });


    }

    @Override
    protected void onResume() {

        super.onResume();

        this.imagesURI = getAllShownImagesPath(this);

        this.imageAdapter = new ImageAdapter(this, this.imagesURI);

        this.imageAdapter.notifyDataSetChanged();

        this.photoGridView.setAdapter(this.imageAdapter);

        System.out.println("resume");

    }

    private ArrayList<String> getAllShownImagesPath(Activity currentContext)
    {

        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);

        scanIntent.setData(Uri.fromFile(new File("test")));

        sendBroadcast(scanIntent);

        Uri imageUri;

        Cursor cursor;

        int column_index_data, column_index_folder_name;

        ArrayList<String> allImages = new ArrayList<>();

        String imageAbsolutePath = null;

        imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        cursor = currentContext.getContentResolver().query(imageUri, projection, null, null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

        while (cursor.moveToNext())
        {

            imageAbsolutePath = cursor.getString(column_index_data);

            if(imageAbsolutePath != null) {

                allImages.add(imageAbsolutePath);

            }

        }

        Collections.reverse(allImages);

        return allImages;

    }



}

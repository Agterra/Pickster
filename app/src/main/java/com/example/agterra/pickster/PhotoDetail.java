package com.example.agterra.pickster;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;

import java.io.File;

public class PhotoDetail extends AppCompatActivity {

    private String imageURI;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        return true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_photo_detail);

        Intent intent = getIntent();

        String imageURI = intent.getStringExtra(PhotoGridActivity.IMAGE_URI_INTENT);

        this.imageURI = imageURI;

        ImageView imageView = (ImageView)findViewById(R.id.photoDetailImageView);

        Glide.with(this).load(imageURI).into(imageView);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {

            case R.id.share_action:

                Intent share = new Intent(Intent.ACTION_SEND);

                share.setType("image/*");

                share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(this.imageURI)));

                startActivity(Intent.createChooser(share, "Share via"));

                return true;

            case R.id.delete_action:

                File file = new File(this.imageURI);

                try
                {


                    System.out.println("delete file: "+ file.getAbsolutePath());

                    Boolean deleted = file.delete();

                    System.out.println(String.valueOf(deleted));

                }
                catch (Exception e)
                {

                    Log.println(Log.ERROR, "suppr: ", e.getMessage());

                    System.out.println("Deletion Error: "+ e.getMessage());

                }

                return true;

            default:

                break;

        }

        return super.onOptionsItemSelected(item);

    }



}

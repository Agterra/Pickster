package com.example.agterra.pickster;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Point;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.net.URI;
import java.util.ArrayList;

/**
 * Created by Agterra on 14/07/2017.
 */

public class ImageAdapter extends BaseAdapter {

    private Activity currentContext;

    private ArrayList<String> images;

    public ImageAdapter(Activity currentContext, ArrayList<String> images)
    {

        this.currentContext = currentContext;

        this.images = images;

    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ImageView picturesView;

        if(view == null)
        {

            picturesView = new ImageView(this.currentContext);

            picturesView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            WindowManager windowManager = this.currentContext.getWindowManager();

            Display screenDisplay = windowManager.getDefaultDisplay();

            Point screenSize = new Point();

            screenDisplay.getSize(screenSize);

            picturesView.setLayoutParams(new GridView.LayoutParams(screenSize.x/2, screenSize.x/2));

        }
        else
        {

            picturesView = (ImageView) view;

        }

        Glide.with(this.currentContext).load(this.images.get(i)).thumbnail(0.1F).into(picturesView);

        return picturesView;

    }

}

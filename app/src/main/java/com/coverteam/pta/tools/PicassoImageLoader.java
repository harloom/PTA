package com.coverteam.pta.tools;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.coverteam.pta.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import lv.chi.photopicker.loader.ImageLoader;

public class PicassoImageLoader implements ImageLoader {
    @Override
    public void loadImage(@NotNull Context context, @NotNull ImageView imageView, @NotNull Uri uri) {
        Picasso.with(context)
                .load(uri)
                .placeholder(R.drawable.bg_picker)
                .fit()
                .centerCrop()
                .into(imageView);
    }
}

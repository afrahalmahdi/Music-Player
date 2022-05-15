package com.example.music_player;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

public class Functions {

    // Function to change image view
    public static void getRoundedImage(ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(10));

        Glide.with(imageView.getContext())
                .load(imageView.getDrawable())
                .apply(requestOptions)
                .override(150, 150)
                .into(imageView);
    }
}

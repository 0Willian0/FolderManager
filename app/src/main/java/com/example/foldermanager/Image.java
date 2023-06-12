package com.example.foldermanager;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Image extends Activity {

    ImageView imageView;
    Uri image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageview);
        image = Uri.parse(getIntent().getStringExtra("Image"));
        imageView = findViewById(R.id.imageView);
        imageView.setImageURI(image);

    }
}

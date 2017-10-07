package com.picusic.picusic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SelectImages extends AppCompatActivity {
private TextView mText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_images);
        // Get View reference
        mText = (TextView)findViewById(R.id.text);
    }

    public void openGallery(View view) {
        mText.setText("Hello World!");
    }
}

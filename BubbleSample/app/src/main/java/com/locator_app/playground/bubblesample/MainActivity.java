package com.locator_app.playground.bubblesample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BubbleView bubble = (BubbleView) findViewById(R.id.bubble);

        bubble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BubbleView bubble = (BubbleView) v;

                bubble.moveTo(360, 511);
                bubble.setRadius(100);
            }
        });
    }
}

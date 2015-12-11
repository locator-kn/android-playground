package com.locator_app.playground.bubblesample;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    BubbleView schoenhier;
    BubbleView userProfile;
    RelativeLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
        schoenhier = (BubbleView) findViewById(R.id.schoenhier);
        userProfile = (BubbleView) findViewById(R.id.userProfile);

        Display display = getWindowManager().getDefaultDisplay();
        Point resolution = new Point();
        display.getSize(resolution);
        schoenhier.moveTo(resolution.x / 2 + 100, resolution.y / 2 - 200);
        userProfile.moveTo(resolution.x / 2, (int)(resolution.y * 0.7));

        schoenhier.setOnClickListener(onSchoenhierClicked);
        userProfile.setOnClickListener(onUserProfileClicked);
    }

    private View.OnClickListener onSchoenhierClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "Schön hier!", Toast.LENGTH_SHORT).show();
            schoenhier.moveTo(0, 0);
        }
    };

    private View.OnClickListener onUserProfileClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "Updating profile", Toast.LENGTH_SHORT).show();
            // addBubbleOnRuntime
        }
    };

    private void addBubbleOnRuntime() {
        BubbleView bubble = BubbleBuilder.on(getApplicationContext())
                .image("drawable://" + R.drawable.locatorlogo)
                .innerColor(Color.RED)
                .innerRadius(70)
                .borderColor(Color.BLACK)
                .innerRadius(5)
                .shadowWidth(20)
                .position(100, 100)
                .build();
        mainLayout.addView(bubble);
    }
}

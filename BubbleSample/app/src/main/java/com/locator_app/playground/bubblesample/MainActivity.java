package com.locator_app.playground.bubblesample;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    RelativeLayout mainLayout;
    BubbleView schoenhier;
    BubbleView userProfile;
    Map<BubbleView, GravityObject> bubbles;
    GravitySimulator simulator;

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
        schoenhier.setInnerRadius(130);
        schoenhier.moveTo(resolution.x / 2 + 20, resolution.y / 2 - 200);
        schoenhier.loadImage("drawable://" + R.drawable.schoenhier);
        userProfile.moveTo(resolution.x / 2, (int) (resolution.y * 0.76));

        schoenhier.setOnClickListener(onSchoenhierClicked);
        userProfile.setOnClickListener(onUserProfileClicked);

        bubbles = new HashMap<>();
        init();
    }

    View.OnClickListener onSchoenhierClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            go();
        }
    };

    View.OnClickListener onUserProfileClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            go();
        }
    };

    void init() {
        bubbles.put(schoenhier, toGravityObject(schoenhier, true));
        bubbles.get(schoenhier).mass = 1000;
        bubbles.put(userProfile, toGravityObject(userProfile, true));
        bubbles.get(userProfile).mass = 20;

        BubbleView b1 = BubbleBuilder.on(getApplicationContext())
                .borderColor(color(R.color.borderBlue))
                .innerColor(color(R.color.innerBlue))
                .borderWidth(10)
                .innerRadius(100)
                .shadowWidth(10)
                .position(100, 100)
                .build();
        mainLayout.addView(b1);
        bubbles.put(b1, toGravityObject(b1, false));
        BubbleView b2 = BubbleBuilder.on(getApplicationContext())
                .borderColor(color(R.color.borderGreen))
                .innerColor(color(R.color.innerGreen))
                .borderWidth(10)
                .innerRadius(50)
                .shadowWidth(10)
                .position(500, 50)
                .build();
        mainLayout.addView(b2);
        bubbles.put(b2, toGravityObject(b2, false));
        BubbleView b3 = BubbleBuilder.on(getApplicationContext())
                .borderColor(color(R.color.borderYellow))
                .innerColor(color(R.color.innerYellow))
                .borderWidth(10)
                .innerRadius(100)
                .shadowWidth(10)
                .position(0, 300)
                .build();
        mainLayout.addView(b3);
        bubbles.put(b3, toGravityObject(b3, false));
        BubbleView b4 = BubbleBuilder.on(getApplicationContext())
                .borderColor(color(R.color.borderRed))
                .innerColor(color(R.color.innerRed))
                .borderWidth(10)
                .innerRadius(75)
                .shadowWidth(10)
                .position(400, 300)
                .build();
        mainLayout.addView(b4);
        bubbles.put(b4, toGravityObject(b4, false));
        BubbleView b5 = BubbleBuilder.on(getApplicationContext())
                .borderColor(color(R.color.borderPurple))
                .innerColor(color(R.color.innerPurple))
                .borderWidth(10)
                .innerRadius(75)
                .shadowWidth(10)
                .position(100, 500)
                .build();
        mainLayout.addView(b5);
        bubbles.put(b5, toGravityObject(b5, false));
    }

    private int color(int id) {
        return ContextCompat.getColor(getApplicationContext(), id);
    }

    GravityObject toGravityObject(BubbleView bubble, boolean fixed) {
        GravityObject gravityObject = new GravityObject(fixed);
        gravityObject.x = bubble.getCenter().x;
        gravityObject.y = bubble.getCenter().y;
        gravityObject.radius = bubble.getRadius();
        gravityObject.mass = bubble.getRadius();
        return gravityObject;
    }

    void go() {
        if (simulator == null) {
            int w = mainLayout.getWidth();
            int h = mainLayout.getHeight();
            simulator = new GravitySimulator(1.0, w, h);
        }

        int i = 0;
        do {
            simulator.simulateGravity(bubbles.values(), 500);
            if (i++ == 5)
                break;
        } while (anyCollision());
        updateBubbles();
    }

    void updateBubbles() {
        for (BubbleView bubble: bubbles.keySet()) {
            GravityObject gravityObject = bubbles.get(bubble);
            bubble.moveTo((float)gravityObject.x, (float)gravityObject.y);
        }
    }

    boolean anyCollision() {
        for (GravityObject o1: bubbles.values()) {
            for (GravityObject o2: bubbles.values()) {
                if (o1 == o2)
                    continue;
                double distance = o1.distanceTo(o2);
                if (distance < o1.radius + o2.radius)
                    return true;
            }
        }
        return false;
    }
}

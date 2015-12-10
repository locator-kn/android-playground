package com.locator_app.playground.bubblesample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final BubbleView bubble = (BubbleView) findViewById(R.id.bubble);

        //bubble.setRadius(100);
        bubble.moveTo(100, 100);

        bubble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //BubbleView bubble = (BubbleView) v;

                int newX = bubble.getCenter().x + 100;
                int newY = bubble.getCenter().y + 100;
                bubble.moveTo(newX, newY);

                //bubble.setRadius(20);
                //bubble.moveTo(360, 511);
                //bubble.moveVirtual(360, 511);

                Toast.makeText(getApplicationContext(), bubble.getCenter().toString(),
                        Toast.LENGTH_SHORT).show();

            }
        });
    }
}

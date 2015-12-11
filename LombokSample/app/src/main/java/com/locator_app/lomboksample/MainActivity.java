package com.locator_app.lomboksample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyClass myInstance = new MyClass();
        myInstance.setSomeMember("http://www.locator-app.com");
        Toast.makeText(getApplicationContext(), myInstance.getSomeMember(), Toast.LENGTH_LONG)
                .show();
    }
}

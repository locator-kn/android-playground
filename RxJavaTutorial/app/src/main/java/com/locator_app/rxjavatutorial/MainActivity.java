package com.locator_app.rxjavatutorial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Observable;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {


    Button button;
    TextView textView;
    rx.Observable<Book> bookObservable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);
        bookObservable = rx.Observable.create(new BookObservable());
        button.setOnClickListener(v -> handleButtonClick());
    }

    void handleButtonClick() {
        textView.setText("");
        bookObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(book -> logBook(book))
                .filter(book -> book.author.startsWith("M"))
                .map(book -> book.title.toUpperCase())
                .map(title -> title + "\n")
                .subscribe(
                        (value) -> textView.append(value),
                        (error) -> Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show(),
                        () -> Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show()
                );
    }

    private void logBook(Book book) {
        Log.d(MainActivity.class.getName(), book.toString());
    }

}

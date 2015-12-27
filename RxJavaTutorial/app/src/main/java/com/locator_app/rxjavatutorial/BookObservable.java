package com.locator_app.rxjavatutorial;


import android.os.SystemClock;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import rx.Observable;
import rx.Subscriber;

public class BookObservable implements Observable.OnSubscribe<Book> {

    @Override
    public void call(Subscriber<? super Book> subscriber) {

        Book book1 = new Book("Müller", "Ach! Hans, Run!", new GregorianCalendar(2010, 5, 19));
        Book book2 = new Book("Maier", "Ciaole Paule", new GregorianCalendar(2007, 8, 22));
        Book book3 = new Book("Schröder", "Tru la la", new GregorianCalendar(1982, 7, 1));

        SystemClock.sleep(300);
        subscriber.onNext(book1);

        SystemClock.sleep(1000);
        subscriber.onNext(book2);

        SystemClock.sleep(600);
        subscriber.onNext(book3);

        subscriber.onCompleted();

        // subscriber.onError(new BookError());
    }

    public class BookError extends Throwable {

    }
}

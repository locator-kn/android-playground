package com.locator_app.rxjavatutorial;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Book {

    public String author;
    public String title;
    public GregorianCalendar date;

    public Book(String author, String title, GregorianCalendar date) {
        this.author = author;
        this.title = title;
        this.date = date;
    }

    @Override
    public String toString() {
        return author + " - " + title + " (" + date.get(Calendar.YEAR) + ")";
    }
}

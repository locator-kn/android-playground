package com.locator_app.playground.bubblesample;

import android.content.Context;
import android.view.View;

/*
    BubbleBuilder is the recommended way to build a BubbleView at runtime.
    usage:
        BubbleView myBubble = BubbleBuilder
                                .on(...)
                                .image("http://...")
                                .position(200, 300)
                                .radius(10)
                                // ...
                                .build();
    note:
        It's smart to call the 'image(...)' function right after the 'on(...)' function is called
        because the image loading process will be started immediately (in a background thread).
 */
public class BubbleBuilder {

    private BubbleView bubble;

    private BubbleBuilder() {
    }

    public static BubbleBuilder on(Context context) {
        BubbleBuilder builder = new BubbleBuilder();
        builder.bubble = new BubbleView(context);
        return builder;
    }

    public BubbleBuilder image(String uri) {
        bubble.loadImage(uri);
        return this;
    }

    public BubbleBuilder innerRadius(int radius) {
        bubble.setInnerRadius(radius);
        return this;
    }

    public BubbleBuilder innerColor(int color) {
        bubble.setInnerColor(color);
        return this;
    }

    public BubbleBuilder borderWidth(int width) {
        bubble.setBorderWidth(width);
        return this;
    }

    public BubbleBuilder borderColor(int color) {
        bubble.setBorderColor(color);
        return this;
    }

    public BubbleBuilder shadowWidth(int width) {
        bubble.setShadowWidth(width);
        return this;
    }

    public BubbleBuilder shadowColor(int color) {
        bubble.setShadowColor(color);
        return this;
    }

    public BubbleBuilder position(int x, int y) {
        bubble.moveTo(x, y);
        return this;
    }

    public BubbleBuilder animation(View.OnTouchListener listener) {
        bubble.setOnTouchListener(listener);
        return this;
    }

    public BubbleView build() {
        return bubble;
    }
}

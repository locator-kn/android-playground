package com.locator_app.playground.bubblesample;

import android.test.AndroidTestCase;


public class BubbleViewTest extends AndroidTestCase {

    BubbleView bubble;

    public void setUp() throws Exception {
        super.setUp();
        bubble = new BubbleView(getContext());
        bubble.setInnerRadius(20);
    }

    public void tearDown() throws Exception {
        bubble = null;
    }

    public void testMoveTo() throws Exception {
        bubble.moveTo(0, 0);
        assertEquals(0, bubble.getCenter().x);
        assertEquals(0, bubble.getCenter().y);

        bubble.moveTo(100, 100);
        assertEquals(100, bubble.getCenter().x);
        assertEquals(100, bubble.getCenter().y);

        bubble.moveTo(300, 500);
        assertEquals(300, bubble.getCenter().x);
        assertEquals(500, bubble.getCenter().y);

        bubble.moveTo(500, 300);
        assertEquals(500, bubble.getCenter().x);
        assertEquals(300, bubble.getCenter().y);
    }

    public void testSetRadius() throws Exception {

        bubble.setShadowWidth(0);

        bubble.setInnerRadius(100);
        bubble.setBorderWidth(10);
        assertEquals(110, bubble.getRadius());

        bubble.setInnerRadius(10);
        bubble.setBorderWidth(100);
        assertEquals(110, bubble.getRadius());
    }
}
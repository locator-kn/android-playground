package com.locator_app.playground.bubblesample;

import android.test.AndroidTestCase;


public class BubbleViewTest extends AndroidTestCase {

    BubbleView bubble;
    private final int DEFAULT_RADIUS = 100;

    public void setUp() throws Exception {
        super.setUp();
        bubble = new BubbleView(getContext(), DEFAULT_RADIUS);
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

    public void testMoveVirtual() throws Exception {
        bubble.moveVirtual(0, 0);
        assertEquals(0, bubble.getVirtualCenter().x);
        assertEquals(0, bubble.getVirtualCenter().y);

        bubble.moveVirtual(100, 100);
        assertEquals(100, bubble.getVirtualCenter().x);
        assertEquals(100, bubble.getVirtualCenter().y);

        bubble.moveVirtual(300, 500);
        assertEquals(300, bubble.getVirtualCenter().x);
        assertEquals(500, bubble.getVirtualCenter().y);

        bubble.moveVirtual(500, 300);
        assertEquals(500, bubble.getVirtualCenter().x);
        assertEquals(300, bubble.getVirtualCenter().y);
    }

    public void testMoveToVirtualCenter() throws Exception {
        bubble.moveVirtual(0, 0);
        bubble.moveToVirtualCenter();
        assertEquals(0, bubble.getCenter().x);
        assertEquals(0, bubble.getCenter().y);

        bubble.moveVirtual(100, 100);
        bubble.moveToVirtualCenter();
        assertEquals(100, bubble.getCenter().x);
        assertEquals(100, bubble.getCenter().y);

        bubble.moveVirtual(300, 500);
        bubble.moveToVirtualCenter();
        assertEquals(300, bubble.getCenter().x);
        assertEquals(500, bubble.getCenter().y);

        bubble.moveVirtual(500, 300);
        bubble.moveToVirtualCenter();
        assertEquals(500, bubble.getCenter().x);
        assertEquals(300, bubble.getCenter().y);
    }

    public void testSetRadius() throws Exception {

        assertEquals(DEFAULT_RADIUS, bubble.getRadius());

        bubble.setRadius(10);
        assertEquals(10, bubble.getRadius());

        bubble.setRadius(100);
        assertEquals(100, bubble.getRadius());

        bubble.setRadius(300);
        assertEquals(300, bubble.getRadius());

        bubble.setRadius(500);
        assertEquals(500, bubble.getRadius());
    }
}
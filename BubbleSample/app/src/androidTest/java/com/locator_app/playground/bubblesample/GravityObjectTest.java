package com.locator_app.playground.bubblesample;

import android.graphics.Point;
import android.test.AndroidTestCase;

import junit.framework.TestCase;


public class GravityObjectTest extends AndroidTestCase {

    public void setUp() throws Exception {
        super.setUp();

    }

    public void tearDown() throws Exception {

    }

    public void testDistanceTo() throws Exception {
        GravityObject obj1 = new GravityObject(new Point(0, 0), 10, 20, true);
        GravityObject obj2 = new GravityObject(new Point(60, 80), 10, 20, true);

        // expected distance => sqrt(60*60+80*80) == 100
        assertEquals(100, obj1.distanceTo(obj2));
        assertEquals(100, obj2.distanceTo(obj1));
    }

    public void testDistanceToWithSamePisotion() throws Exception {
        GravityObject obj1 = new GravityObject(new Point(100, 100), 10, 20, true);
        GravityObject obj2 = new GravityObject(new Point(100, 100), 50, 38, false);

        // objects are on same position, so distance must be 0
        assertEquals(0, obj1.distanceTo(obj2));
        assertEquals(0, obj2.distanceTo(obj1));
    }

    public void testDistanceToWithItself() throws Exception {
        GravityObject obj1 = new GravityObject(new Point(20, 20), 0, 0, true);
        assertEquals(0, obj1.distanceTo(obj1));
    }
}
package com.locator_app.playground.bubblesample;


import android.graphics.Point;

public class GravityObject {

    private Point center;
    private int radius;
    private float mass;
    private boolean fixedPosition;

    public GravityObject(Point center, int radius, float mass, boolean fixedPosition) {
        this.center = center;
        this.radius = radius;
        this.mass = mass;
        this.fixedPosition = fixedPosition;
    }

    public int getRadius() {
        return radius;
    }

    public Point getCenter() {
        return center;
    }

    public float getMass() {
        return mass;
    }

    public boolean hasFixedPosition() {
        return fixedPosition;
    }

    void moveTo(int x, int y) {
        center.set(x, y);
    }

    int distanceTo(GravityObject other) {
        int xDelta = center.x - other.center.x;
        int yDelta = center.y - other.center.y;
        int distance = (int) Math.sqrt(xDelta * xDelta + yDelta * yDelta);
        return distance;
    }
}

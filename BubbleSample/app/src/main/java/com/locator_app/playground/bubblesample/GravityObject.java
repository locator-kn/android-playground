package com.locator_app.playground.bubblesample;


import android.graphics.Point;

public class GravityObject {

    double x, y;
    double vx, vy;
    double radius, mass;
    private boolean fixedPosition;

    public GravityObject(boolean fixedPosition) {
        this.fixedPosition = fixedPosition;
        // todo determine smart initial values for velocity vector
        this.vx = 0;
        this.vy = 0;
    }

    public boolean fixed() {
        return fixedPosition;
    }

    double distanceTo(GravityObject other) {
        double xDelta = x - other.x;
        double yDelta = y - other.y;
        double distance = Math.sqrt(xDelta * xDelta + yDelta * yDelta);
        return distance;
    }
}

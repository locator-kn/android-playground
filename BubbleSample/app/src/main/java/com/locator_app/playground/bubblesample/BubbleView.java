package com.locator_app.playground.bubblesample;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class BubbleView extends View {

    private Paint circlePaint;
    private final static int NOT_INITIALIZED = -1;
    private int radius = NOT_INITIALIZED;

    public BubbleView(Context context, int radius) {
        this(context, null);
        this.radius = radius;
    }

    public BubbleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BubbleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = context.getTheme()
                              .obtainStyledAttributes(attrs, R.styleable.BubbleView, 0, 0);


        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setStyle(Paint.Style.FILL);

        int circleColor = a.getColor(R.styleable.BubbleView_bubbleColor, 0xff00ff);
        circlePaint.setColor(circleColor);
    }

    public void moveTo(float x, float y) {
        setX(x - radius);
        setY(y - radius);

        requestLayout();
    }

    public void setRadius(int radius) {
        float x = getX() + this.radius;
        float y = getY() + this.radius;

        this.radius = radius;
        getLayoutParams().width = radius * 2;
        getLayoutParams().height = radius * 2;

        moveTo(x, y);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (radius == NOT_INITIALIZED) {
            radius = (Math.min(getWidth(), getHeight()) / 2);
        }
    }

    public int getRadius() {
        return radius;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, circlePaint);
    }

//    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
//        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
//                .getHeight(), Config.ARGB_8888);
//        Canvas canvas = new Canvas(output);
//
//        final int color = 0xff424242;
//        final Paint paint = new Paint();
//        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
//        final RectF rectF = new RectF(rect);
//        final float roundPx = pixels;
//
//        paint.setAntiAlias(true);
//        canvas.drawARGB(0, 0, 0, 0);
//        paint.setColor(color);
//        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
//
//        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
//        canvas.drawBitmap(bitmap, rect, rect, paint);
//
//        return output;
//    }
}

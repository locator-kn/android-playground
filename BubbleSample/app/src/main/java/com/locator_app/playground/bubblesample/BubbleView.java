package com.locator_app.playground.bubblesample;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class BubbleView extends View {


    private final static int NOT_INITIALIZED = -1;
    private int radius = NOT_INITIALIZED;
    private Paint circlePaint;
    private int bubbleColorWidth = 5;
    private Paint shadowPaint;
    private int shadowWidth = 2;
    private Bitmap bitmap;
    private Point virtualCenter;

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

        int circleColor = a.getColor(R.styleable.BubbleView_bubbleColor, 0xff00ff);
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setColor(circleColor);

        shadowPaint = new Paint(0);
        shadowPaint.setColor(0x77eeccee);
        shadowPaint.setMaskFilter(new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL));
        this.shadowWidth = a.getInteger(R.styleable.BubbleView_shadowWidth, 0);

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.progresscircle);

        virtualCenter = new Point();
    }

    public void moveTo(float x, float y) {
        setX(x - radius);
        setY(y - radius);
        requestLayout();
    }

    public Point getCenter() {
        return new Point((int)getX() + radius, (int)getY() + radius);
    }

    public void moveVirtual(int x, int y) {
        virtualCenter.set(x, y);
    }

    public void moveToVirtualCenter() {
        moveTo(virtualCenter.x, virtualCenter.y);
    }

    public Point getVirtualCenter() {
        return virtualCenter;
    }

    public void setRadius(int radius) {
        float x = getX() + this.radius;
        float y = getY() + this.radius;

        this.radius = radius;
        if (getLayoutParams() != null) {
            getLayoutParams().width = radius * 2;
            getLayoutParams().height = radius * 2;
        }

        moveTo(x, y);
    }

    public int getRadius() {
        return radius;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (radius == NOT_INITIALIZED) {
            radius = (Math.min(getWidth(), getHeight()) / 2);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawShadow(canvas);
        drawBubble(canvas);
        drawBitmap(canvas);
    }

    private void drawShadow(Canvas canvas) {
        if (shadowWidth > 0) {
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, shadowPaint);
        }
    }

    private void drawBubble(Canvas canvas) {
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius - shadowWidth, circlePaint);
    }

    private void drawBitmap(Canvas canvas) {
        canvas.drawBitmap(bitmap, 0, 0, circlePaint);
    }

    public void loadImage(String imageUri) {
        if (!ImageLoader.getInstance().isInited()) {
            ImageLoaderConfiguration configuration =
                    new ImageLoaderConfiguration.Builder(getContext()).build();
            ImageLoader.getInstance().init(configuration);
        }
        ImageLoader.getInstance().loadImage(imageUri, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
            }
            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                final String errorMessage = "could not load image: " + imageUri;
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                scaleRoundAndSetBitmap(loadedImage);
            }
            @Override
            public void onLoadingCancelled(String imageUri, View view) {
            }
        });
    }

    private void scaleRoundAndSetBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        invalidate();
    }
}
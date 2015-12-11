package com.locator_app.playground.bubblesample;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
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

    private Paint painter;

    private int innerColor;
    public void setInnerColor(int color) {
        innerColor = color;
        invalidate();
    }

    private int innerRadius;
    public void setInnerRadius(int radius) {
        innerRadius = radius;
        updateLayoutParams();
    }

    private Bitmap innerIcon;
    private void setInnerBitmap(Bitmap bitmap) {
        // todo scale round ...
        innerIcon = bitmap;
        invalidate();
    }

    private int borderColor;
    public void setBorderColor(int color) {
        borderColor = color;
        invalidate();
    }

    private int borderWidth;
    public void setBorderWidth(int width) {
        borderWidth = width;
        updateLayoutParams();
    }

    private int shadowColor;
    public void setShadowColor(int color) {
        shadowColor = color;
        invalidate();
    }

    private int shadowWidth;
    public void setShadowWidth(int width) {
        shadowWidth = width;
        requestLayout();
    }

    public BubbleView(Context context) {
        this(context, null);
    }

    public BubbleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BubbleView(final Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        Resources.Theme resourceTheme = context.getTheme();
        TypedArray a = resourceTheme.obtainStyledAttributes(attrs, R.styleable.BubbleView, 0, 0);

        painter = new Paint(Paint.ANTI_ALIAS_FLAG);
        painter.setStyle(Paint.Style.FILL);

        int innerColor = a.getColor(R.styleable.BubbleView_innerColor, Color.TRANSPARENT);
        setInnerColor(innerColor);
        int innerRadius = a.getInteger(R.styleable.BubbleView_innerRadius, 50);
        setInnerRadius(innerRadius);

        int borderColor = a.getColor(R.styleable.BubbleView_borderColor, Color.MAGENTA);
        setBorderColor(borderColor);
        int borderWidth = a.getInteger(R.styleable.BubbleView_borderWidth, 10);
        setBorderWidth(borderWidth);

        int shadowColor = a.getInteger(R.styleable.BubbleView_shadowColor, 0x80000000);
        setShadowColor(shadowColor);
        int shadowWidth = a.getInteger(R.styleable.BubbleView_shadowWidth, 0);
        setShadowWidth(shadowWidth);
    }

    public void moveTo(float x, float y) {
        int radius = getRadius();
        setX(x - radius);
        setY(y - radius);
        requestLayout();
    }

    public Point getCenter() {
        return new Point((int)getX() + getRadius(), (int)getY() + getRadius());
    }

    private void updateLayoutParams() {
        int currentRadius = Math.min(getWidth(), getHeight()) / 2;
        float x = getX() + currentRadius;
        float y = getY() + currentRadius;
        if (getLayoutParams() != null) {
            int newRadius = getRadius();
            getLayoutParams().width = newRadius * 2;
            getLayoutParams().height = newRadius * 2;
        }
        moveTo(x, y);
    }

    public int getRadius() {
        return innerRadius + borderWidth + shadowWidth;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        updateLayoutParams();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBorderWithShadow(canvas);
        drawInner(canvas);
    }

    private void drawBorderWithShadow(Canvas canvas) {
        painter.setColor(borderColor);
        painter.setStyle(Paint.Style.STROKE);
        painter.setStrokeWidth(borderWidth);
        painter.setShadowLayer(shadowWidth, 0, 0, shadowColor);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, innerRadius, painter);
    }

    private void drawInner(Canvas canvas) {
        painter.setColor(innerColor);
        painter.setStyle(Paint.Style.FILL);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, innerRadius, painter);
        if (innerIcon != null) {
            canvas.drawBitmap(innerIcon, 0, 0, painter);
        }
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
                Bitmap progress = BitmapFactory.decodeResource(getContext().getResources(),
                        R.mipmap.progresscircle);
                setInnerBitmap(progress);
            }
            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                final String errorMessage = "could not load image: " + imageUri;
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                setInnerBitmap(null);
            }
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                setInnerBitmap(loadedImage);
            }
            @Override
            public void onLoadingCancelled(String imageUri, View view) {
            }
        });
    }
}

package com.solutions.secondorder.SOSSteelBeamLite;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * TODO: document your custom view class.
 * Custom Attributes: image_id
 */
public class ScaledImageView extends ImageView {
    int image_id;
    Context context;
    Drawable image;
    public ScaledImageView(Context context) {
        super(context);
        this.context = context;
        init(null, 0);
    }

    public ScaledImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs, 0);
    }

    public ScaledImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.ScaledImageView, defStyle, 0);
        image_id = a.getResourceId(R.styleable.ScaledImageView_image_id, 0);
        image = ResourcesCompat.getDrawable(getResources(), image_id, null);
        this.setImageResource(image_id);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = width * image.getIntrinsicHeight() / image.getIntrinsicWidth();
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

}

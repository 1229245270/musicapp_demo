package com.example.musicapp.Module;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;

import com.example.musicapp.R;

public class CircularImageView extends AppCompatImageView {
    float width,height;
    private int DEFAULTRADIUS = 0;
    private int radius,leftTopRadius,rightTopRadius,rightBottomRadius,leftBottomRadius;
    private boolean isCircle;

    public CircularImageView(Context context) {
        this(context,null);
        init(context,null);
    }

    public CircularImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
        init(context,attrs);
    }

    public CircularImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    public void init(Context context,AttributeSet attrs){
        if(Build.VERSION.SDK_INT < 18){
            setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        }
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircularImageView);
        radius = array.getDimensionPixelOffset(R.styleable.CircularImageView_radius,DEFAULTRADIUS);
        leftTopRadius = array.getDimensionPixelOffset(R.styleable.CircularImageView_leftTopRadius,DEFAULTRADIUS);
        rightTopRadius = array.getDimensionPixelOffset(R.styleable.CircularImageView_rightTopRadius,DEFAULTRADIUS);
        rightBottomRadius = array.getDimensionPixelOffset(R.styleable.CircularImageView_rightBottomRadius,DEFAULTRADIUS);
        leftBottomRadius = array.getDimensionPixelOffset(R.styleable.CircularImageView_leftBottomRadius,DEFAULTRADIUS);
        isCircle = array.getBoolean(R.styleable.CircularImageView_isCircle,false);

        if(DEFAULTRADIUS == leftTopRadius){
            leftTopRadius = radius;
        }
        if(DEFAULTRADIUS == rightTopRadius){
            rightTopRadius = radius;
        }
        if(DEFAULTRADIUS == rightBottomRadius){
            rightBottomRadius = radius;
        }
        if(DEFAULTRADIUS == leftBottomRadius){
            leftBottomRadius = radius;
        }
        array.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int maxLeft = Math.max(leftTopRadius,leftBottomRadius);
        int maxRight = Math.max(rightTopRadius,rightBottomRadius);
        int minWidth = maxLeft + maxRight;
        int maxTop = Math.max(leftTopRadius,rightTopRadius);
        int maxBottom = Math.max(leftBottomRadius,rightBottomRadius);
        int minHeight = maxTop + maxBottom;
        if(width >= minWidth && height >= minHeight){
            Path path = new Path();
            if(isCircle){
                path.addCircle(width/2,height/2,width/2,Path.Direction.CW);
            }else {
                path.moveTo(leftTopRadius, 0);
                path.lineTo(width - rightTopRadius, 0);
                path.quadTo(width, 0, width, rightTopRadius);

                path.lineTo(width, height - rightBottomRadius);
                path.quadTo(width, height, width - rightBottomRadius, height);

                path.lineTo(leftBottomRadius, height);
                path.quadTo(0, height, 0, height - leftBottomRadius);

                path.lineTo(0, leftTopRadius);
                path.quadTo(0, 0, leftTopRadius, 0);
            }
            canvas.clipPath(path);
        }
        super.onDraw(canvas);
    }

}

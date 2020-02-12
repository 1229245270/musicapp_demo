package com.example.musicapp.Module;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatImageView;

public class NumberImageView extends AppCompatImageView {

    private static int COMMENT = 0;
    public static int REDCIRCLE = 1;
    //要显示的数量数量
    private int num = 0;
    //红色圆圈的半径
    private float radius;
    //圆圈内数字的半径
    private float textSize;
    //右边和上边内边距
    private int paddingRight;
    private int paddingTop;
    private int type = COMMENT;

    public NumberImageView(Context context) {
        super(context);
    }

    public NumberImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NumberImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //设置显示的数量
    public void setNum(int num) {
        this.num = num;
        //重新绘制画布
        invalidate();
    }
    //设置显示的类型
    public void setType(int type) {
        this.type = type;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (num > 0) {
            if(type == COMMENT){

                //初始化半径
                radius = getWidth() / 6;
                //初始化字体大小
                textSize = num < 10 ? radius + 5 : radius;
                //初始化边距
                paddingRight = getPaddingRight();
                paddingTop = getPaddingTop();
                //初始化画笔
                Paint paint = new Paint();
                //设置抗锯齿
                paint.setAntiAlias(true);
                //设置颜色为#999999
                paint.setColor(Color.parseColor("#666666"));
                //设置字体大小
                paint.setTextSize(textSize);
                //画数字
                canvas.drawText("" + (num < 999 ? num : "999+"),
                        num < 10 ? getWidth() - radius - textSize / 4 - paddingRight/2 : getWidth() - radius - textSize / 2 - paddingRight/2,
                        radius + textSize / 3 + paddingTop/2, paint);
            }else if(type == REDCIRCLE){
                //初始化半径
                radius = getWidth() / 6;
                //初始化字体大小
                textSize = num < 10 ? radius + 5 : radius;
                //初始化边距
                paddingRight = getPaddingRight();
                paddingTop = getPaddingTop();
                //初始化画笔
                Paint paint = new Paint();
                //设置抗锯齿
                paint.setAntiAlias(true);
                //设置颜色为红色
                paint.setColor(0xffff4444);
                //设置填充样式为充满
                paint.setStyle(Paint.Style.FILL);
                //画圆
                canvas.drawCircle(getWidth() - radius - paddingRight/2, radius + paddingTop/2, radius, paint);
                //设置颜色为白色
                paint.setColor(0xffffffff);
                //设置字体大小
                paint.setTextSize(textSize);
                //画数字
                canvas.drawText("" + (num < 99 ? num : "99+"),
                        num < 10 ? getWidth() - radius - textSize / 4 - paddingRight/2
                                : getWidth() - radius - textSize / 2 - paddingRight/2,
                        radius + textSize / 3 + paddingTop/2, paint);
            }
        }
    }
}
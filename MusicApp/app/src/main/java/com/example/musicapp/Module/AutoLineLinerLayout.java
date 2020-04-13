package com.example.musicapp.Module;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;


public class AutoLineLinerLayout extends LinearLayout {
    private int mLineCount,mChildCount,mWidth,mHeight;

    public AutoLineLinerLayout(Context context) {
        super(context);
    }

    public AutoLineLinerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoLineLinerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        mLineCount = 0;
        mChildCount = getChildCount();

        //没有子控件的时候，给AT_MOST模式默认大小
        if (mChildCount == 0){
            if (heightMode == MeasureSpec.AT_MOST){
                height = 0;
            }
            if (widthMode == MeasureSpec.AT_MOST){
                width = 0;
            }
            setMeasuredDimension(width,height);

            return;
        }
        //至少有一行
        mLineCount ++;
        //每一行子view的宽度加左右padding的值
        int totalWidthPerLine = getPaddingLeft() + getPaddingRight();
        LayoutParams layoutParams = null;
        View child = null;
        for (int i = 0 ; i < mChildCount ; i ++){
            child = getChildAt(i);
            layoutParams = ((LayoutParams) child.getLayoutParams());
            measureChild(child,widthMeasureSpec,heightMeasureSpec);

            totalWidthPerLine += child.getMeasuredWidth()+layoutParams.leftMargin+layoutParams.rightMargin;
            if (totalWidthPerLine > width){//需要换行
                //换行后，当前子view需要排到新的一行，totalWidthPerLine也需要更新为新行的宽度，需要加上当前子view的宽度和margin
                totalWidthPerLine = child.getMeasuredWidth()+layoutParams.leftMargin+layoutParams.rightMargin+getPaddingLeft()+getPaddingRight();
                mLineCount ++;
            }else{

            }
        }
        //此viewGroup的高度
        int layoutHeight = getPaddingTop() + getPaddingBottom();
        //在这里每一个子view的高度一致、故只取第一个子view的高度及其marginTop和marginBottom属性
        child = getChildAt(0);
        layoutParams = ((LayoutParams) child.getLayoutParams());
        //计算此viewGroup的高度 通过每一行子view的高度 x 行数 + padding 得到总高度
        layoutHeight += (child.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin) * mLineCount;

        if(mLineCount >= 3){
            layoutHeight = getPaddingTop() + getPaddingBottom() + (child.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin) * 3;
        }
        /*//AT_MOST模式下、就取上面计算出来的高度
        if (heightMode == MeasureSpec.AT_MOST){
            height = layoutHeight;
        }*/
        height = layoutHeight;
        this.mWidth = width;
        this.mHeight = height;
        setMeasuredDimension(width,height);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed,l,t,r,b);
        if (mLineCount > 1){
            View child;
            LayoutParams layoutParams = null;

            int[] childCountPerLine = new int[mLineCount];//每行的个数
            int currentLine = 0;
            int hasInArrayCount = 0;//已入数组的个数

            int childrenWidth = getPaddingLeft() + getPaddingRight();
            for (int i = 0 ; i < mChildCount ; i ++){
                child = getChildAt(i);
                layoutParams = ((LayoutParams) child.getLayoutParams());
                childrenWidth += child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;

                if (childrenWidth > mWidth){
                    childCountPerLine[currentLine] = i - hasInArrayCount;
                    hasInArrayCount += i - hasInArrayCount;
                    currentLine ++;
                    childrenWidth = getPaddingLeft() + getPaddingRight() + child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
                }

            }

            //计算最后一行有多少个子view
            if (currentLine == childCountPerLine.length - 1){
                childCountPerLine[currentLine] = mChildCount - hasInArrayCount;
            }

            int left;
            int top;
            int bottom;

            int totalCountInLines = 0;//遍历过的每一行的子view的个数的和

            //由于默认每一个子view的高度和margin属性都一致。所以选取第一个子view测量即可
            View c = getChildAt(0);
            LayoutParams lp = ((LayoutParams) c.getLayoutParams());

            //遍历行
            for (int j = 0 ; j < childCountPerLine.length ; j ++){

                top = j * (c.getMeasuredHeight()+lp.topMargin+lp.bottomMargin) + lp.topMargin;
                bottom = c.getMeasuredHeight() + top;

                left = 0;

                for (int i = totalCountInLines; i < childCountPerLine[j] + totalCountInLines ; i ++){//遍历每行的子view，布局每行子view
                    child = getChildAt(i);
                    layoutParams = ((LayoutParams) child.getLayoutParams());

                    left += layoutParams.leftMargin;

                    child.layout(left+getPaddingLeft(),top + getPaddingTop(),left+child.getMeasuredWidth(),bottom);

                    left += child.getMeasuredWidth() + layoutParams.rightMargin;
                }
                totalCountInLines += childCountPerLine[j];//将当前行子view的个数相加
            }
            //最后一行剩余的子view个数
            int countLastLine = mChildCount - totalCountInLines;
            if ( countLastLine > 0){
                left = 0;
                top = (childCountPerLine.length) * (c.getMeasuredHeight()+lp.topMargin+lp.bottomMargin) + lp.topMargin;
                bottom = c.getMeasuredHeight() + top;

                //遍历最后一行
                for (int i = totalCountInLines ; i < mChildCount ; i ++){
                    child = getChildAt(i);
                    layoutParams = ((LayoutParams) child.getLayoutParams());

                    left += layoutParams.leftMargin;

                    child.layout(left+getPaddingLeft(),top + getPaddingTop(),left+child.getMeasuredWidth(),bottom);

                    left += child.getMeasuredWidth() + layoutParams.rightMargin;
                }
            }
        }

    }

}

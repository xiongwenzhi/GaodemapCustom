package com.android.orangetravel.ui.widgets.view;

/**
 * Created by Administrator on 2017/12/4.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.android.orangetravel.R;


/**
 * Created by LITP on 2016/9/9.
 */
public class TipView extends View {

    private int textColor = Color.WHITE;   //默认
    private float textMargin = 0.9f;    //文字边距，0-1 ，越小字越小
    private int circleColor = Color.RED;  //默认背景颜色
    private int text = 0;               //默认文本0，0就是不显示
    private int textMax = 99;          //最大数值，超出就显示 +

    Paint paint = new Paint();   //尽量不要在onDraw创建对象，因为onDraw经常调用

    public TipView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TipView);

        // 获取自定义属性的文字
        int text = ta.getInt(R.styleable.TipView_text, -1);
        setText(text);

        // 获取自定义属性的 最大消息数
        int textMax = ta.getInt(R.styleable.TipView_textMax, 0);
        setTextMax(textMax);

        // 获取自定义属性的字体颜色，默认为白色
        int textColor = ta.getColor(R.styleable.TipView_textColor, 0);
        setTextColor(textColor);

        // 获取自定义属性的字体距离圆的距离，默认为5px
        Float textMargin = ta.getFloat(R.styleable.TipView_textMargin, 0);
        setTextMargin(textMargin);

        // 获取自定义属性的原型背景颜色，默认为红色
        int circleColor = ta.getColor(R.styleable.TipView_tips_circleColor, 0);
        setCircleColor(circleColor);

        ta.recycle();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int mWidthHalf = getMeasuredWidth() / 2;

        //创建画笔
        paint.setAntiAlias(true);  // 设置画笔的锯齿效果。true是去除锯齿，更平滑
//        paint.setStrokeWidth(3);
//        paint.setStyle(Paint.Style.STROKE);//只描边，不填充
        paint.setColor(circleColor);// 设置圆的背景红色
        //cx：圆心的x坐标。cy：圆心的y坐标。radius：圆的半径。paint：绘制时所使用的画笔
        canvas.drawCircle(mWidthHalf, mWidthHalf, mWidthHalf, paint);

        //写文字,为0 就不显示
        if (text != 0) {
            paint.setColor(textColor);
            paint.setTextSize(getTextSize());

            paint.setTextAlign(Paint.Align.CENTER);   //对齐原点位置

            //text:要绘制的文字,x：绘制原点x坐标  0y：绘制原点y坐标  底部基线的位置,paint:用来做画的画笔
            canvas.drawText(text > textMax ? textMax + "+" : String.valueOf(text),
                    mWidthHalf,
                    getTextY(paint),    //文本基线
                    paint);
        }

    }

    /**
     * 每位数大小不一样，防止超出圆圈
     *
     * @return 字体size
     */
    private Float getTextSize() {
        float num;
        if (text >= 100) {  //三位数
            num = 0.5f;
        } else if (text >= 10) { //两位数
            num = 0.7f;
        } else {
            num = 0.9f;
        }
        return getMeasuredHeight() * num * textMargin;

    }


    private int getTextY(Paint paint) {
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        return ((getMeasuredHeight() - fontMetrics.bottom - fontMetrics.top) / 2);
    }


    public int getTextMax() {
        return textMax;
    }

    public void setTextMax(int textMax) {
        if (textMax > 0) {
            this.textMax = textMax;
        }

    }

    public int getText() {
        return text;
    }

    public void setText(int text) {
        if (text < 0) {
            setVisibility(GONE);
        } else {
            this.text = text;
        }
        invalidate();
    }

    public int getTextColor() {
        return textColor;
    }


    public void setTextColor(int textColor) {
        if (textColor != 0) {
            this.textColor = textColor;
            invalidate();
        }
    }

    public float getTextMargin() {
        return textMargin;
    }

    public void setTextMargin(float textMargin) {
        if (textMargin > 1) {   //最大为1
            this.textMargin = 1;
        } else if (textMargin < 0) {
            this.textMargin = 0;
        } else if (textMargin != 0) {
            this.textMargin = textMargin;
        }

    }

    public int getCircleColor() {
        return circleColor;
    }

    public void setCircleColor(int circleColor) {
        if (circleColor != 0) {
            this.circleColor = circleColor;
            invalidate();
        }
    }


}
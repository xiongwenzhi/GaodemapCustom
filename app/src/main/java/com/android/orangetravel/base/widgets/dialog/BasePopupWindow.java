package com.android.orangetravel.base.widgets.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.yang.base.R;

/**
 * PopupWindow基类
 *
 * @author yangfei
 */
public class BasePopupWindow extends PopupWindow {

    public BasePopupWindow(Builder builder) {
        super(builder.contentView, builder.width, builder.height, builder.focusable);
        super.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        super.setOutsideTouchable(builder.touchable);
        super.setAnimationStyle(builder.animationStyle);
    }

    public static class Builder {

        public View contentView;
        public int width;
        public int height;
        public boolean focusable;
        public boolean touchable;
        public int animationStyle;

        public Builder() {
            width = ViewGroup.LayoutParams.WRAP_CONTENT;
            height = ViewGroup.LayoutParams.WRAP_CONTENT;
            focusable = true;
            touchable = true;
            animationStyle = R.style.DialogCentreAnim;
        }

        public Builder setContentView(View contentView) {
            this.contentView = contentView;
            return this;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setFocusable(boolean focusable) {
            this.focusable = focusable;
            return this;
        }

        public Builder setTouchable(boolean touchable) {
            this.touchable = touchable;
            return this;
        }

        public Builder setAnimationStyle(int animationStyle) {
            this.animationStyle = animationStyle;
            return this;
        }

        public BasePopupWindow create() {
            return new BasePopupWindow(this);
        }
    }

}
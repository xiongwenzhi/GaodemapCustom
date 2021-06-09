package com.android.orangetravel.base.statusBar;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;

import com.android.orangetravel.base.widgets.StatusBarView;
import com.yang.base.R;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static com.android.orangetravel.base.utils.ScreenUtils.getStatusBarHeight;


/**
 * 沉浸式状态栏
 *
 * @author yangfei
 */
public class YangStatusBar {
    private static final int COLOR_TRANSLUCENT = Color.parseColor("#00000000");

    public static final int DEFAULT_COLOR_ALPHA = 112;

    public static YangStatusBar with(@NonNull Activity activity) {
        if (null == activity)
            throw new IllegalArgumentException("Activity不能为null");
        return new YangStatusBar(activity);
    }

    public static final int TRANSPARENT = Color.TRANSPARENT;
    //    public static final int TRANSPARENT_BLACK = ColorUtils.blendARGB(Color.TRANSPARENT, Color.BLACK, 0.3f);
    public static final int TRANSPARENT_BLACK = Color.TRANSPARENT;

    private Activity mActivity;
    private Window mWindow;
    private ViewGroup mDecorView;
    private StatusBarView mStatusBarView;
    // 是否深色
    private boolean isDarkColor = false;
    // 状态栏颜色
    @ColorInt
    private int statusBarColor = TRANSPARENT_BLACK;
    // 状态栏控件颜色
    @ColorInt
    private int statusBarViewColor = Color.WHITE;

    public YangStatusBar(Activity activity) {
        WeakReference<Activity> weakReference = new WeakReference<>(activity);
        this.mActivity = weakReference.get();
        this.mWindow = this.mActivity.getWindow();
        this.mDecorView = (ViewGroup) this.mWindow.getDecorView();
    }

    /**
     * 是否深色
     */
    public YangStatusBar setDarkColor(boolean darkColor) {
        this.isDarkColor = darkColor;
        return this;
    }

    /**
     * 状态栏颜色
     */
    public YangStatusBar setStatusBarColor(@ColorInt int statusBarColor) {
        this.statusBarColor = statusBarColor;
        return this;
    }

    /**
     * 状态栏控件颜色
     */
    public YangStatusBar setStatusBarViewColor(@ColorInt int statusBarViewColor) {
        this.statusBarViewColor = statusBarViewColor;
        return this;
    }

    /**
     * 重置
     */
    public YangStatusBar reset() {
        // 是否深色
        this.isDarkColor = false;
        // 状态栏颜色
        this.statusBarColor = TRANSPARENT_BLACK;
        // 状态栏控件颜色
        this.statusBarViewColor = Color.WHITE;
        return this;
    }

    /**
     * 初始化
     */
    public YangStatusBar init() {
        /*-----------------------------------------------------------------*/
        // 设置状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 5.0以上系统
            this.mWindow.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            this.mWindow.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            this.mWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            this.mWindow.setStatusBarColor(this.statusBarColor);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 4.4以上系统
            this.mWindow.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 添加状态栏控件
            addStatusBarView();
        }
        if (OsUtil.isFlymeOS4Later() || OsUtil.isMIUI6Later() || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)) {
            // 设置Android状态栏的字体颜色
            setStatusBarFontIconDark();
        }
        View statusBarView = mActivity.findViewById(R.id.id_status_bar_view);
        if ((null != statusBarView) && (statusBarViewColor != 0)) {
            statusBarView.setBackgroundColor(statusBarViewColor);
        }
        /*-----------------------------------------------------------------*/
        return this;
    }

    /**
     * change to full screen mode
     *
     * @param hideStatusBarBackground hide status bar alpha Background when SDK > 21, true if hide it
     */
    public static void translucentStatusBar(Activity activity, boolean hideStatusBarBackground) {
        Window window = activity.getWindow();
        ViewGroup mContentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);

        //set child View not fill the system window
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            ViewCompat.setFitsSystemWindows(mChildView, false);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int statusBarHeight = getStatusBarHeight(activity);

            //First translucent status bar.
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //After LOLLIPOP just set LayoutParams.
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                if (hideStatusBarBackground) {
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.setStatusBarColor(COLOR_TRANSLUCENT);
                } else {
                    window.setStatusBarColor(calculateStatusBarColor(COLOR_TRANSLUCENT, DEFAULT_COLOR_ALPHA));
                }
                //must call requestApplyInsets, otherwise it will have space in screen bottom
                if (mChildView != null) {
                    ViewCompat.requestApplyInsets(mChildView);
                }
            } else {
                ViewGroup mDecorView = (ViewGroup) window.getDecorView();
                if (mDecorView.getTag() != null && mDecorView.getTag() instanceof Boolean && (Boolean) mDecorView.getTag()) {
                    mChildView = mDecorView.getChildAt(0);
                    //remove fake status bar view.
                    mContentView.removeView(mChildView);
                    mChildView = mContentView.getChildAt(0);
                    if (mChildView != null) {
                        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mChildView.getLayoutParams();
                        //cancel the margin top
                        if (lp != null && lp.topMargin >= statusBarHeight) {
                            lp.topMargin -= statusBarHeight;
                            mChildView.setLayoutParams(lp);
                        }
                    }
                    mDecorView.setTag(false);
                }
            }
        }
    }

    //Get alpha color
    private static int calculateStatusBarColor(int color, int alpha) {
        float a = 1 - alpha / 255f;
        int red = color >> 16 & 0xff;
        int green = color >> 8 & 0xff;
        int blue = color & 0xff;
        red = (int) (red * a + 0.5);
        green = (int) (green * a + 0.5);
        blue = (int) (blue * a + 0.5);
        return 0xff << 24 | red << 16 | green << 8 | blue;
    }

    /**
     * 销毁
     */
    public void destroy() {
        this.mActivity = null;
        this.mWindow = null;
        this.mDecorView = null;
        this.mStatusBarView = null;
    }

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------

    /**
     * 添加状态栏控件
     */
    private void addStatusBarView() {
        // 创建一个状态栏控件
        if (null == this.mStatusBarView) {
            this.mStatusBarView = new StatusBarView(this.mActivity);
//            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) this.mStatusBarView.getLayoutParams();// new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, SystemUtil.getStatusBarHeight());
//            params.gravity = Gravity.TOP;
//            this.mStatusBarView.setLayoutParams(params);

            this.mDecorView.addView(this.mStatusBarView);
        }
        // 设置颜色
        this.mStatusBarView.setBackgroundColor(this.statusBarColor);
        this.mStatusBarView.setVisibility(View.VISIBLE);
//        // 添加到布局中
//        ViewGroup viewGroup = (ViewGroup) this.mStatusBarView.getParent();
//        if (null != viewGroup)
//            viewGroup.removeView(this.mStatusBarView);
//        this.mDecorView.addView(this.mStatusBarView);
    }

    /**
     * 设置Android状态栏的字体颜色,状态栏为亮色的时候字体和图标是黑色,
     * 状态栏为暗色的时候字体和图标为白色
     */
    private void setStatusBarFontIconDark() {
        // 魅族FlymeOs4及以上
        if (OsUtil.isFlymeOS4Later()) {
            FlymeUtil.setStatusBarDarkIcon(this.mActivity, isDarkColor);
        }
        // 小米MIUI6及以上
        if (OsUtil.isMIUI6Later()) {
            try {
                Class<? extends Window> clazz = this.mWindow.getClass();
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                int darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (isDarkColor) {// 状态栏亮色且黑色字体
                    extraFlagField.invoke(this.mWindow, darkModeFlag, darkModeFlag);
                } else {// 清除黑色字体
                    extraFlagField.invoke(this.mWindow, 0, darkModeFlag);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Android6.0+系统
        // 这个设置和在xml的style文件中用这个<item name="android:windowLightStatusBar">true</item>属性是一样的
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isDarkColor) {
                this.mWindow.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                this.mWindow.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
        }
    }

}
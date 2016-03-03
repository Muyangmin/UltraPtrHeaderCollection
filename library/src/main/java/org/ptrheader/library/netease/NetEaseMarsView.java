package org.ptrheader.library.netease;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import org.ptrheader.library.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * This kind of refresh effect is created by NetEase News app and this is an unofficial
 * implementation.
 *
 * 实现思路：
 * 网易新闻刷新动画有好几种，这里实现的是 V5.4.9的小球加回归线类型的下拉动画。
 * 实现要点：
 * 1.下拉刷新但还未触及刷新位置时，不显示回归线；
 * 2.下拉过程中小球从最小尺寸逐步线性放大到最大尺寸；
 * 2.松开手指开始刷新后，显示回归线并且从赤道位置开始来回移动，其宽度伴随着离赤道距离的增加而缩小。
 *
 * @author Muyangmin
 * @since 1.0.0
 */
public class NetEaseMarsView extends FrameLayout implements PtrUIHandler {

    private ImageView imgMars;
    private ImageView imgMarsCircle;

    /**
     * The circle should be drawn and animated only if this variable is set to true.
     */
    private boolean isRefreshing;
    /**
     * records the percent for drawing, from {@link #onUIPositionChange(PtrFrameLayout, boolean,
     * byte, PtrIndicator)} method.
     */
    private float ptrPullDownPercent;
    private float circleScaleX = 0;
    private float circleTranslateY = 0;

    private AnimatorSet circleAnimators;

    public NetEaseMarsView(Context context) {
        super(context);
        init(context, null);
    }

    public NetEaseMarsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public NetEaseMarsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressWarnings("unused")
    public NetEaseMarsView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        //merge label must be used with attachToRoot=true
        LayoutInflater.from(context).inflate(R.layout.layout_mars_view, this);
        imgMars = (ImageView) findViewById(R.id.netease_mars_img_mars);
        imgMarsCircle = (ImageView) findViewById(R.id.netease_mars_img_mars_circle);

        circleAnimators = new AnimatorSet();
        ValueAnimator scaleXAnimator = ValueAnimator.ofFloat(1, 0.5F, 1, 0.5F, 1);
        setAnimators(scaleXAnimator);
        scaleXAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                circleScaleX = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });

        ValueAnimator translateYAnimator = ValueAnimator.ofFloat(0F, -0.5F, 0F, 0.5F, 0F);
        setAnimators(translateYAnimator);
        translateYAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                circleTranslateY = (float)animation.getAnimatedValue();
                postInvalidate();
            }
        });

        circleAnimators.playTogether(scaleXAnimator, translateYAnimator);
    }

    private void setAnimators(ValueAnimator animator) {
        animator.setDuration(1000);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (imgMars != null && imgMarsCircle != null) {
            canvas.save();
            canvas.translate(imgMars.getX(), imgMars.getY());
            float ballScale = 0.7F * ptrPullDownPercent + 0.3F;
            canvas.scale(ballScale, ballScale, imgMars.getWidth() / 2.0F, 0.0F);
            imgMars.draw(canvas);
            canvas.restore();

            if (isRefreshing) {
                canvas.save();
                float translateY = ((imgMars.getHeight() - imgMarsCircle.getHeight()) / 2.0F) *
                        circleTranslateY;
                float scaleX = 0.7F * circleScaleX + 0.3F;
                canvas.translate(imgMarsCircle.getX(), imgMarsCircle.getY() + translateY);
                canvas.scale(scaleX, scaleX, imgMarsCircle.getWidth() / 2.0F, imgMarsCircle.getHeight()/2.0F);
                imgMarsCircle.draw(canvas);
                canvas.restore();
            }
        }
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status,
                                   PtrIndicator ptrIndicator) {
        float percent = ptrIndicator.getCurrentPercent();
        if (percent <= 1) {
            this.ptrPullDownPercent = percent;
            postInvalidate();
        }
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {
        //empty implementation
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        //empty implementation
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        isRefreshing = true;
        circleAnimators.start();
        postInvalidate();
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        isRefreshing = false;
        circleAnimators.end();
        postInvalidate();
    }
}

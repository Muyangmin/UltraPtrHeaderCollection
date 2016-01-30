package org.ptrheader.library.ballslogan;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * This view class is the core view of {@link BallSloganHeader}, provides rotating and animating
 * features.
 * <p>
 *
 *     (x1, y1)+ ---------- +(x2, y1)
 *             |            |
 *             |            |
 *             |            |
 *             |            |
 *     (x1, y2)+ ---------- +(x2, y2)
 *  </p>
 */
public class BallRectangleView extends View {

    private Paint paint;

    private PointF[] basePoint;
    private float[] translateX = new float[4];
    private float[] translateY = new float[4];

    private List<Animator> animators;
    private boolean isInAnimation;

    private int[] colorSchemes;
    private int circleRadius;

    public BallRectangleView(Context context) {
        super(context);
        init();
    }

    public BallRectangleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BallRectangleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressWarnings("unused")
    public BallRectangleView(Context context, AttributeSet attrs, int defStyleAttr, int
            defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        //alloc default points
        basePoint = new PointF[]{new PointF(), new PointF(), new PointF(), new PointF()};
        animators = new ArrayList<>();

        //TEST:set default color !
        setColorSchemes(Color.parseColor("#FDC752"), Color.parseColor("#70D439"), Color
                .parseColor("#70D439"), Color.parseColor("#1699FE"));
    }

    public void setColorSchemes(int colorScheme1, int colorScheme2, int colorScheme3, int
            colorScheme4) {
        this.colorSchemes = new int[]{colorScheme1, colorScheme2, colorScheme3, colorScheme4};
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < 4; i++) {
            canvas.save();

            paint.setColor(colorSchemes[i]);
            float centerX, centerY;
            if (isInAnimation) {
                centerX = translateX[i];
                centerY = translateY[i];
            } else {
                centerX = basePoint[i].x;
                centerY = basePoint[i].y;
            }
            canvas.drawCircle(centerX, centerY, circleRadius, paint);
            canvas.restore();
        }
    }

    @SuppressLint("DrawAllocation")
    @SuppressWarnings("UnnecessaryLocalVariable")
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        //Try to avoid redundant computation
        if (!changed) {
            return;
        }

        animators.clear();

        float margin = getMargin(getWidth());
        float x1 = margin;
        float x2 = getWidth() - margin;
        float y1 = margin;
        float y2 = getHeight() - margin;

        for (int i = 0; i < 4; i++) {
            ValueAnimator animatorX = null;
            ValueAnimator animatorY = null;
            final int index = i;
            //NOTE: Here the animator must end as the start value, or it would lead to a short
            // 'flash' on every time animator executed completed.
            // So it is five points and four lines.
            switch (i) {
                case 0:
                    animatorX = ValueAnimator.ofFloat(x1, x2, x2, x1, x1);
                    animatorY = ValueAnimator.ofFloat(y1, y1, y2, y2, y1);
                    break;
                case 1:
                    animatorX = ValueAnimator.ofFloat(x2, x2, x1, x1, x2);
                    animatorY = ValueAnimator.ofFloat(y1, y2, y2, y1, y1);
                    break;
                case 2:
                    animatorX = ValueAnimator.ofFloat(x1, x1, x2, x2, x1);
                    animatorY = ValueAnimator.ofFloat(y2, y1, y1, y2, y2);
                    break;
                case 3:
                    animatorX = ValueAnimator.ofFloat(x2, x1, x1, x2, x2);
                    animatorY = ValueAnimator.ofFloat(y2, y2, y1, y1, y2);
                    break;
                default:
                    break;
            }
            onCreateAnimator(animatorX);
            onCreateAnimator(animatorY);
            if (animatorX == null || animatorY == null) {
                throw new RuntimeException("Child class shouldn't set animator as null!");
            }

            animatorX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    translateX[index] = (float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });

            animatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    translateY[index] = (float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            animators.add(animatorX);
            animators.add(animatorY);
        }

        circleRadius = getCircleRadius(getWidth());

        basePoint[0].set(x1, y1);
        basePoint[1].set(x2, y1);
        basePoint[2].set(x1, y2);
        basePoint[3].set(x2, y2);

    }

    public void stopAnimator() {
        isInAnimation = false;
        if (animators == null || animators.isEmpty()) {
            return;
        }
        for (Animator animator : animators) {
            animator.end();
        }
    }

    public void startAnimator() {
        if (animators == null || animators.isEmpty()) {
            return;
        }
        for (Animator animator : animators) {
            animator.start();
        }
        isInAnimation = true;
    }

    /**
     * Compute circle radius by view's width.
     */
    protected int getCircleRadius(int width) {
        return width / 8;
    }

    /**
     * Compute circle margin by view's width.
     */
    protected float getMargin(int width) {
        return width * 0.3F;
    }

    /**
     * Set animator for the value animation, e.g interpolator, duration, etc.
     */
    protected void onCreateAnimator(ValueAnimator animator) {
        animator.setDuration(2000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
    }

}

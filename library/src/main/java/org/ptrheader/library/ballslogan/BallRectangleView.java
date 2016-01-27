package org.ptrheader.library.ballslogan;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * This view class is the core view of {@link BallSloganHeader}, provides rotating and animating
 * features.
 *
 *     (x1, y1)+ ---------- +(x2, y1)
 *             |            |
 *             |            |
 *             |            |
 *             |            |
 *     (x1, y2)+ ---------- +(x2, y2)
 *
 * Just as the image above, since the view is a square, (x2-x1) is equal to (y2-y1). According to
 * this important condition, we can use a single ValueAnimator for all balls.
 *
 */
public class BallRectangleView extends View{

    protected String LOG_TAG = getClass().getSimpleName();

    private Paint paint;

    private PointF[] basePoint;
    private PointF[] offsetPoints;
    private int[] colorSchemes;
    private int circleRadius;
    private ValueAnimator valueAnimator;

    private ValueAnimator.AnimatorUpdateListener updateListener = new ValueAnimator
            .AnimatorUpdateListener() {

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {

            float value = (float) animation.getAnimatedValue();
            Log.v(LOG_TAG, "animation value=" + value);
            offsetPoints[0].set(value, -value);
            offsetPoints[1].set(value, value);
            offsetPoints[2].set(-value, -value);
            offsetPoints[3].set(-value, value);
        }
    };

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

    private void init(){
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        //alloc default points
        basePoint = new PointF[]{new PointF(), new PointF(), new PointF(), new PointF()};
        offsetPoints = new PointF[]{new PointF(), new PointF(), new PointF(), new PointF()};
        //TEST:set default color !
        setColorSchemes(Color.parseColor("#FDC752"), Color.parseColor("#70D439"), Color
                .parseColor("#70D439"), Color.parseColor("#1699FE"));
    }

    public void setColorSchemes(int colorScheme1, int colorScheme2, int colorScheme3, int colorScheme4){
        this.colorSchemes = new int[]{colorScheme1, colorScheme2, colorScheme3, colorScheme4};
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i=0; i<4; i++){
            paint.setColor(colorSchemes[i]);
            canvas.drawCircle(basePoint[i].x + offsetPoints[i].x, basePoint[i].y +
                    offsetPoints[i].y, circleRadius, paint);
        }
    }

    @SuppressWarnings("UnnecessaryLocalVariable")
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        //Try to avoid redundant computation
        if ((!changed) || (valueAnimator != null)) {
            return;
        }

        float margin = getMargin(getWidth());
        float x1 = margin;
        float x2 = getWidth()-margin;
        float y1 = margin;
        float y2 = getHeight()-margin;

        //For the reasons of using single animator, please read the header note.
        valueAnimator = ValueAnimator.ofFloat(0, x2 - x1);
        setAnimator(valueAnimator);
        valueAnimator.addUpdateListener(updateListener);

        circleRadius = getCircleRadius(getWidth());

        basePoint[0].set(x1, y1);
        basePoint[1].set(x2, y1);
        basePoint[2].set(x1, y2);
        basePoint[3].set(x2, y2);

        valueAnimator.start();
    }

    public void stopAnimator(){
        if (valueAnimator!=null){
            valueAnimator.end();
        }
    }

    public void startAnimator(){
        if (valueAnimator!=null){
            valueAnimator.start();
        }
    }

    /**
     * Compute circle radius by view's width.
     */
    protected int getCircleRadius(int width){
        return width/8;
    }

    /**
     * Compute circle margin by view's width.
     */
    protected float getMargin(int width){
        return width*0.3F;
    }

    /**
     * Set animator for the value animation.
     */
    protected void setAnimator(ValueAnimator animator) {
        animator.setDuration(2000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
    }
}

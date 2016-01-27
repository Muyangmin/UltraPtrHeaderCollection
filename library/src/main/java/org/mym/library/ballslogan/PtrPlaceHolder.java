package org.mym.library.ballslogan;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import org.mym.library.R;

/**
 * 这个view主要是解决PTR在未执行刷新时隐藏刷新动画的问题。
 * Created by Muyangmin on 1/14/16.
 */
public class PtrPlaceHolder extends View {

    private Paint paint;
    private int[] colorSchemes;
    private float[] translateX = new float[4],
            translateY = new float[4];

    public PtrPlaceHolder(Context context) {
        super(context);
        init(context);
    }

    public PtrPlaceHolder(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PtrPlaceHolder(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressWarnings("unused")
    public PtrPlaceHolder(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        Resources resources = context.getResources();
        colorSchemes = new int[]{
                resources.getColor(R.color.ptr_header_yellow),
                resources.getColor(R.color.ptr_header_green),
                resources.getColor(R.color.ptr_header_red),
                resources.getColor(R.color.ptr_header_blue),
        };
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawColor(Color.WHITE);
        for (int i = 0; i < 4; ++i) {
            canvas.save();
            canvas.translate(translateX[i], translateY[i]);
            paint.setColor(colorSchemes[i]);
            canvas.drawCircle(0, 0, getWidth() / 6, paint);

            canvas.restore();
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        float startX    = getWidth()/5;
        float startY    = getWidth()/5;

        //noinspection UnnecessaryLocalVariable
        float leftX     = startX;
        float rightX    = getWidth() - startX;
        //noinspection UnnecessaryLocalVariable
        float topY      = startY;
        float bottomY   = getHeight() - startY;

        translateX = new float[]{leftX, rightX, leftX, rightX};
        translateY = new float[]{topY, topY, bottomY, bottomY};
    }
}

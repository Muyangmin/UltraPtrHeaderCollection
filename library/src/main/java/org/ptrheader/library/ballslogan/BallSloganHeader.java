package org.ptrheader.library.ballslogan;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import org.ptrheader.library.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * <p>
 *     A header view contains a rotatable 4-colored-ball view and a normal ImageView for displaying
 * slogans.
 * </p>
 * Created by Muyangmin on 1/13/16.
 * @see org.ptrheader.library.R.styleable#BallSloganHeader
 */
public class BallSloganHeader extends FrameLayout implements PtrUIHandler {

    private boolean rotateBallsOnPullDown;

    private BallRectangleView ballRectangleView;
    private ImageView imgSlogan;

    public BallSloganHeader(Context context) {
        super(context);
        init(context, null);
    }

    public BallSloganHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BallSloganHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressWarnings("unused")
    public BallSloganHeader(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.header_ball_slogan, this);
        ballRectangleView = (BallRectangleView)findViewById(R.id.ball_slogan_view_rectangle);
        imgSlogan = (ImageView)findViewById(R.id.ball_slogan_img_slogan);

        if (attrs == null) {
            return ;
        }
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BallSloganHeader);

        int colorScheme1 = a.getColor(R.styleable.BallSloganHeader_colorScheme1, -1);
        int colorScheme2 = a.getColor(R.styleable.BallSloganHeader_colorScheme2, -1);
        int colorScheme3 = a.getColor(R.styleable.BallSloganHeader_colorScheme3, -1);
        int colorScheme4 = a.getColor(R.styleable.BallSloganHeader_colorScheme4, -1);
        ballRectangleView.setColorSchemes(colorScheme1, colorScheme2, colorScheme3, colorScheme4);

        int sloganDrawable = a.getResourceId(R.styleable.BallSloganHeader_sloganDrawable, 0);
        imgSlogan.setBackgroundResource(sloganDrawable);

        //default behaviour allows rotate
        rotateBallsOnPullDown = a.getBoolean(R.styleable.BallSloganHeader_rotateBallsOnPullDown,
                true);

        a.recycle();
    }

    /**
     * Retrieve the view for displaying slogan.
     */
    public ImageView getSloganImageView() {
        return imgSlogan;
    }

    /**
     * Retrieve the ball view.
     */
    public BallRectangleView getBallRectangleView() {
        return ballRectangleView;
    }

    /**
     * Set whether allow rotation when the header is pulled down.The default value is {@code true}.
     */
    public void setRotateBallsOnPullDown(boolean rotateBallsOnPullDown) {
        this.rotateBallsOnPullDown = rotateBallsOnPullDown;
    }

    /**
     * Delegate method, set the ball colors.
     */
    public void setColorSchemes(int colorScheme1, int colorScheme2, int colorScheme3, int colorScheme4) {
        ballRectangleView.setColorSchemes(colorScheme1, colorScheme2, colorScheme3, colorScheme4);
    }

    /**
     * Delegate method, set the background of slogan view.
     */
    public void setSloganBackgroundResource(int resid) {
        imgSlogan.setBackgroundResource(resid);
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {
        hideProgressView();
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        hideProgressView();
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        showProgressView();
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        hideProgressView();
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        if (rotateBallsOnPullDown) {
            int rotateDegree = (int) (ptrIndicator.getCurrentPercent() * 360);
            ballRectangleView.setRotation(rotateDegree);
        }
    }

    private void showProgressView() {
        ballRectangleView.startAnimator();
    }

    private void hideProgressView() {
        ballRectangleView.stopAnimator();
    }

}

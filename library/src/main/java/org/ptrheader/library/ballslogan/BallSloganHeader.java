package org.ptrheader.library.ballslogan;

import android.annotation.TargetApi;
import android.content.Context;
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
 * Custom header views.
 * Created by Muyangmin on 1/13/16.
 */
public class BallSloganHeader extends FrameLayout implements PtrUIHandler {

    private BallRectangleView viewRectangle;
    private ImageView imgSlogan;

    public BallSloganHeader(Context context) {
        super(context);
        init(context);
    }

    public BallSloganHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BallSloganHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressWarnings("unused")
    public BallSloganHeader(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.header_ball_slogan, this);
        viewRectangle = (BallRectangleView)findViewById(R.id.ball_slogan_view_rectangle);
        imgSlogan = (ImageView)findViewById(R.id.ball_slogan_img_slogan);
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
        int rotateDegree = (int) (ptrIndicator.getCurrentPercent() * 360);
        viewRectangle.setRotation(rotateDegree);
    }

    private void showProgressView() {
        viewRectangle.startAnimator();
    }

    private void hideProgressView() {
        viewRectangle.stopAnimator();
    }

}

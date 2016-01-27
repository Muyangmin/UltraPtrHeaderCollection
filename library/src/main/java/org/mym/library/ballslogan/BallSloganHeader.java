package org.mym.library.ballslogan;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.wang.avi.AVLoadingIndicatorView;

import org.mym.library.R;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * Custom header views.
 * Created by Muyangmin on 1/13/16.
 */
public class BallSloganHeader extends FrameLayout implements PtrUIHandler {

    private View viewPlaceHolder;
    private AVLoadingIndicatorView indicatorView;

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
        View headerView = LayoutInflater.from(context).inflate(R.layout.header_ball_slogan, this);
        //        TextView tvMotto = (TextView) headerView.findViewById(R.id.header_ptr_motto);
        //        TextView tvAuthor = (TextView) headerView.findViewById(R.id.header_ptr_author);
        viewPlaceHolder = headerView.findViewById(R.id.header_ptr_place_holder);
        indicatorView = (AVLoadingIndicatorView) headerView.findViewById(R.id
                .header_ptr_avLoadingIndicator);

        //        String[] mottoArray = context.getResources().getStringArray(R.array.ptr_random_motto);
        //        String[] authorArray = context.getResources().getStringArray(R.array.ptr_random_author);
        //        if (mottoArray.length != authorArray.length) {
        //            L.e(VIEW_LOG_TAG, "Different array length!");
        //        }
        //        int safeLength = Math.min(mottoArray.length, authorArray.length);
        //        int index = ((int) (Math.random() * 1000)) % safeLength;
        //        tvMotto.setText(mottoArray[index]);
        //        tvAuthor.setText(authorArray[index]);
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
        viewPlaceHolder.setRotation(rotateDegree);
    }

    private void showProgressView() {
        viewPlaceHolder.setVisibility(View.INVISIBLE);
        indicatorView.setVisibility(View.VISIBLE);
    }

    private void hideProgressView() {
        indicatorView.setVisibility(View.INVISIBLE);
        viewPlaceHolder.setVisibility(View.VISIBLE);
    }

}

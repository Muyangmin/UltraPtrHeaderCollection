package org.ptrheader.library.netease;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.ptrheader.library.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * This is a header combines {@link NetEaseMarsView} and a TextView which can display "refresh xx
 * minutes ago" or "Pull to refresh".
 * According to difference text to display among apps, this class do not provide any default
 * display, please use {@link #getRefreshTimeView()} or {@link #setLastRefreshLabel(CharSequence)}.
 *
 * @author Muyangmin
 * @since 1.0.0
 */
public class NetEaseNewsHeader extends FrameLayout implements PtrUIHandler {

    private NetEaseMarsView marsView;
    private TextView tvRefreshTime;

    public NetEaseNewsHeader(Context context) {
        super(context);
        init(context, null);
    }

    public NetEaseNewsHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public NetEaseNewsHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressWarnings("unused")
    public NetEaseNewsHeader(Context context, AttributeSet attrs, int defStyleAttr, int
            defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    @SuppressWarnings("UnusedParameters")
    private void init(Context context, AttributeSet attrs){
        LayoutInflater.from(context).inflate(R.layout.header_netease_news, this);
        marsView = (NetEaseMarsView)findViewById(R.id.netease_mars_view);
        tvRefreshTime = (TextView)findViewById(R.id.netease_tv_refresh_time);
    }

    @SuppressWarnings("unused")
    public TextView getRefreshTimeView(){
        return tvRefreshTime;
    }

    @SuppressWarnings("unused")
    public void setLastRefreshLabel(CharSequence label){
        tvRefreshTime.setText(label);
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status,
                                   PtrIndicator ptrIndicator) {
        marsView.onUIPositionChange(frame, isUnderTouch, status, ptrIndicator);
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        marsView.onUIRefreshBegin(frame);
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        marsView.onUIRefreshComplete(frame);
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        marsView.onUIRefreshPrepare(frame);
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {
        marsView.onUIReset(frame);
    }
}

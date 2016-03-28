package org.ptrheader.library.ingkee;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import org.ptrheader.library.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * @author Muyangmin
 */
public class IngkeeHeader extends RelativeLayout implements PtrUIHandler {

    private ProgressBar progressBar;
    private ImageView imgLogo;

    private Drawable imgLogoColored;
    private Drawable imgLogoGrey;

    public IngkeeHeader(Context context) {
        super(context);
        init(context, null);
    }

    public IngkeeHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public IngkeeHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressWarnings("unused")
    public IngkeeHeader(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        LayoutInflater.from(context).inflate(R.layout.layout_ingkee_header, this);
        progressBar  = (ProgressBar) findViewById(R.id.ptr_header_ingkee_prg_indicator);
        imgLogo = (ImageView)findViewById(R.id.ptr_header_ingkee_img_logo);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.IngkeeHeader);
            Drawable prgIndeterminateDrawable = a.getDrawable(R.styleable
                    .IngkeeHeader_customProgressIndicator);
            //prefer use customized indicator
            if (prgIndeterminateDrawable!=null){
                progressBar.setIndeterminateDrawable(prgIndeterminateDrawable);
            }

            imgLogoColored = a.getDrawable(R.styleable.IngkeeHeader_logoDrawableColored);
            imgLogoGrey = a.getDrawable(R.styleable.IngkeeHeader_logoDrawableGrey);
            //noinspection deprecation
            imgLogo.setBackgroundDrawable(imgLogoColored);
            a.recycle();
        }
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {

    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {
        setLogoToGrey();
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {

    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        seLogoToColored();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {

    }

    private void setLogoToGrey(){
        //noinspection deprecation
        imgLogo.setBackgroundDrawable(imgLogoGrey);
    }

    private void seLogoToColored(){
        //noinspection deprecation
        imgLogo.setBackgroundDrawable(imgLogoColored);
    }

}

package org.ptrheader.ultraptrheadercollection;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.ptrheader.library.netease.NetEaseNewsHeader;
import org.ptrheader.ultraptrheadercollection.model.HeaderDisplay;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;

/**
 * @author Muyangmin
 * @since 1.0.0
 */
public class InnerActivity extends Activity implements SupportedHeaderType {

    private static final String EXTRA_HEADER_INFO = "info";

    private PtrFrameLayout ptrFrameLayout;

    private Runnable mCompleteRefresh = new Runnable() {
        @Override
        public void run() {
            ptrFrameLayout.refreshComplete();
        }
    };

    public static Intent createIntent(Context context, HeaderDisplay headerDisplay) {
        Intent intent = new Intent(context, InnerActivity.class);
        intent.putExtra(EXTRA_HEADER_INFO, headerDisplay);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inner);
        //set back button
        findViewById(R.id.imgbtn_title_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ptrFrameLayout = (PtrFrameLayout)findViewById(R.id.inner_ptr);

        HeaderDisplay headerInfo = getIntent().getParcelableExtra(EXTRA_HEADER_INFO);
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(headerInfo.displayName);

        @SuppressLint("InflateParams")
        View header = LayoutInflater.from(this).inflate(headerInfo.sampleUsage, null);
        if (header==null || !(header instanceof PtrUIHandler)){
            Log.e("InnerActivity", "Oops, no ui handler?");
        }
        else{
            extraSettingOrDefaultDisplay(header, headerInfo.type);

            ptrFrameLayout.setHeaderView(header);
            ptrFrameLayout.addPtrUIHandler((PtrUIHandler)header);
        }

        //set ptrHandler & auto refresh
        ptrFrameLayout = (PtrFrameLayout)findViewById(R.id.inner_ptr);
        ptrFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                ptrFrameLayout.postDelayed(mCompleteRefresh, 3000);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        ptrFrameLayout.autoRefresh();
        ptrFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrFrameLayout.autoRefresh();
            }
        }, 500);
    }

    private void extraSettingOrDefaultDisplay(View inflatedHeader, int type) {
        switch (type) {
            case NET_EASE_NEWS_HEADER:
                NetEaseNewsHeader netEaseNewsHeader = (NetEaseNewsHeader) inflatedHeader;
                netEaseNewsHeader.setLastRefreshLabel("1分钟前刷新");
                break;
            default:
                //No Extra operations.
                break;
        }
    }
}

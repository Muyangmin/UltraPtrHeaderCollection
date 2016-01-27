package org.ptrheader.ultraptrheadercollection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.ptrheader.library.ballslogan.BallSloganHeader;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;

public class MainActivity extends AppCompatActivity {

    private PtrFrameLayout ptrFrameLayout;

    private Runnable mCompleteRefresh = new Runnable() {
        @Override
        public void run() {
            ptrFrameLayout.refreshComplete();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ptrFrameLayout = (PtrFrameLayout)findViewById(R.id.main_ptr);

//        BallSloganHeader header = new BallSloganHeader(this);
//        ptrFrameLayout.setHeaderView(header);
//        ptrFrameLayout.addPtrUIHandler(header);

//        ptrFrameLayout.addPtrUIHandler((PtrUIHandler)ptrFrameLayout.getHeaderView());

        //emulate data request
        ptrFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                ptrFrameLayout.postDelayed(mCompleteRefresh, 3000);
            }
        });

        //TODO add multi header switch and factory/interface
    }
}

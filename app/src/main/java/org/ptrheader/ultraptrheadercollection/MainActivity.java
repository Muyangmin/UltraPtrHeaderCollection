package org.ptrheader.ultraptrheadercollection;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.ptrheader.library.netease.NetEaseNewsHeader;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;

public class MainActivity extends AppCompatActivity implements SupportedHeaderType {

    private static final String LOG_TAG = "MainActivity";

    private SparseArray<View> headerViewMap;

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

        createHeaderMap();

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.main_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<HeaderDisplay> displayList = createHeaderDisplayList();
        if (displayList.size()!=headerViewMap.size()){
            Log.e(LOG_TAG, "adapter size != supported types, please fix!");
        }
        recyclerView.setAdapter(new HeaderAdapter(displayList));

        //emulate data request
        ptrFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                ptrFrameLayout.postDelayed(mCompleteRefresh, 3000);
            }
        });

        //TODO add multi header switch and factory/interface
    }

    private void createHeaderMap() {
        headerViewMap = new SparseArray<>();

        //This collection view is just for code to be simpler;
        @SuppressLint("InflateParams")
        View collections = LayoutInflater.from(this).inflate(R.layout.header_collections, null);

        putViewToMap(collections, BALL_SLOGAN, R.id.main_header_ball_slogan);
        putViewToMap(collections, NET_EASE_MARS_VIEW, R.id.main_header_net_ease_mars);
        putViewToMap(collections, NET_EASE_NEWS_HEADER, R.id.main_header_net_ease_news);

        extraInitOrDefaultDisplayInJava();
    }

    private void putViewToMap(View collections, int headerType, @IdRes int id){
        View view = collections.findViewById(id);
        //remove collection parent
        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
            Log.v(LOG_TAG, "removed view");
        }
        headerViewMap.put(headerType, view);
    }

    private void extraInitOrDefaultDisplayInJava(){
        NetEaseNewsHeader netEaseNewsHeader = (NetEaseNewsHeader)headerViewMap.get
                (NET_EASE_NEWS_HEADER);
        netEaseNewsHeader.setLastRefreshLabel("1分钟前刷新");
    }
    
    private List<HeaderDisplay> createHeaderDisplayList(){
        List<HeaderDisplay> list = new ArrayList<>();
        list.add(new HeaderDisplay(BALL_SLOGAN, "BallSloganHeader"));
        list.add(new HeaderDisplay(NET_EASE_MARS_VIEW, "NetEaseMarsView Only"));
        list.add(new HeaderDisplay(NET_EASE_NEWS_HEADER, "NetEaseNewsHeader"));
        return list;
    }

    private static class HeaderDisplay{

        public final String name;
        public final int type;

        public HeaderDisplay(int type, String name) {
            this.type = type;
            this.name = name;
        }
    }

    private class HeaderHolder extends RecyclerView.ViewHolder{
        private TextView tvName;
        public HeaderHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.item_main_tv_name);
        }

        void displayData(final HeaderDisplay data){
            tvName.setText(data.name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View header = headerViewMap.get(data.type);
                    if (header==null || (!(header instanceof PtrUIHandler))){
                        Log.wtf(LOG_TAG, "Is there any wrong config?");
                        return ;
                    }
                    if (header == ptrFrameLayout.getHeaderView()){
                        Log.v(LOG_TAG, "Already this header");
                    }
                    else{
                        ptrFrameLayout.setHeaderView(header);
                        ptrFrameLayout.addPtrUIHandler((PtrUIHandler) header);
                    }
                    //auto refresh to show header immediately.
                    ptrFrameLayout.autoRefresh();
                }
            });
        }
    }

    private class HeaderAdapter extends RecyclerView.Adapter<HeaderHolder> {

        private List<HeaderDisplay> list;

        public HeaderAdapter(List<HeaderDisplay> list) {
            this.list = list;
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        @Override
        public HeaderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler,
                    parent, false);
            return new HeaderHolder(view);
        }

        @Override
        public void onBindViewHolder(HeaderHolder holder, int position) {
            holder.displayData(list.get(position));
        }
    }
}

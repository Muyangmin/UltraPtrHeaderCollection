package org.ptrheader.ultraptrheadercollection;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.ptrheader.ultraptrheadercollection.model.HeaderDisplay;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 这个Sample主要展示Header在XML中的用法和一些必要的Java调用。
 * 要查看某个具体类型的使用方式，请查看 {@link #createHeaderDisplayList()}中该类型对应的XML文件即可。
 *
 * <p>
 *     如果你有更好地展示Sample的方式【包括UI或逻辑上的】，欢迎在 GitHub 上提issue或者PR。
 * </p>
 * https://github.com/Muyangmin/UltraPtrHeaderCollection
 */
public class MainActivity extends AppCompatActivity implements SupportedHeaderType {

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
        ptrFrameLayout = (PtrFrameLayout) findViewById(R.id.main_ptr);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<HeaderDisplay> displayList = createHeaderDisplayList();
        recyclerView.setAdapter(new HeaderAdapter(displayList));

        //emulate data request
        ptrFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                ptrFrameLayout.postDelayed(mCompleteRefresh, 3000);
            }
        });

    }

    private List<HeaderDisplay> createHeaderDisplayList() {
        List<HeaderDisplay> list = new ArrayList<>();
        list.add(new HeaderDisplay("BallSloganHeader", BALL_SLOGAN,
                R.layout.sample_header_ball_slogan));
        list.add(new HeaderDisplay("NetEaseMarsView Only", NET_EASE_MARS_VIEW,
                R.layout.sample_netease_mars_only));
        list.add(new HeaderDisplay("NetEaseNewsHeader", NET_EASE_NEWS_HEADER,
                R.layout.sample_netease_news));
        return list;
    }

    private class HeaderHolder extends RecyclerView.ViewHolder {
        private TextView tvName;

        public HeaderHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.item_main_tv_name);
        }

        void displayData(final HeaderDisplay data) {
            tvName.setText(data.displayName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(InnerActivity.createIntent(MainActivity.this, data));
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

package space.alair.vavale;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import space.alair.vavale.adapters.MsgDetailAdapter;
import space.alair.vavale.databinding.ActivityMsgDetailBinding;
import space.alair.vavale.models.MsgModel;
import space.alair.vavale.view_tools.LoadMore.EndlessRecyclerOnScrollListener;
import space.alair.vavale.view_tools.LoadMore.LoadMoreWrapper;

public class MsgDetailActivity extends BaseActivity {


    public final static String TITLE = "msg-detail-title";
    private Toolbar toolbar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private LoadMoreWrapper loadMoreWrapper;
    private List<MsgModel> dataList = new ArrayList<>();

    @Override
    protected void buildView(Bundle savedInstanceState) {
        ActivityMsgDetailBinding activityMsgDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_msg_detail);
        String title = getIntent().getStringExtra(TITLE);
        if (title != null)
            activityMsgDetailBinding.setTitle(title);
        init();

    }


    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // 使用Toolbar替换ActionBar
//        setSupportActionBar(toolbar);

        // 设置刷新控件颜色
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#4DB6AC"));

        // 模拟获取数据
        getData();
        MsgDetailAdapter msgDetailAdapter = new MsgDetailAdapter(dataList, this);
        loadMoreWrapper = new LoadMoreWrapper(msgDetailAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(loadMoreWrapper);

        // 设置下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新数据
                dataList.clear();
                getData();
                loadMoreWrapper.notifyDataSetChanged();

                // 延时1s关闭下拉刷新
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, 1000);
            }
        });

        // 设置加载更多监听
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING);

                if (dataList.size() < 52) {
                    // 模拟获取网络数据，延时1s
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    getData();
                                    loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
                                }
                            });
                        }
                    }, 1000);
                } else {
                    // 显示加载到底的提示
                    loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
                }
            }
        });

    }

    private void getData() {
        char letter = 'A';
        for (int i = 0; i < 26; i++) {
            MsgModel msgModel = new MsgModel();
            msgModel.setContent(i * i + "");
            msgModel.setDate(i * i * 8 + "");
            msgModel.setPhone("156788328" + i);
            msgModel.setRead(i % 2 == 0);
            msgModel.setPrice(i*0.5);
            msgModel.setReceiveNumber("749837492"+i);
            dataList.add(msgModel);
            letter++;
        }
    }


}

package space.alair.vavale;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import space.alair.vavale.adapters.MsgListAdapter;
import space.alair.vavale.models.MsgListContextModel;
import space.alair.vavale.models.MsgModel;
import space.alair.vavale.net_tools.responses.MsgResponse;
import space.alair.vavale.net_tools.responses.ResponseListener;
import space.alair.vavale.view_tools.LoadMore.EndlessRecyclerOnScrollListener;
import space.alair.vavale.view_tools.LoadMore.LoadMoreWrapper;

public class MsgListActivity extends BaseActivity {

    private Toolbar toolbar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private LoadMoreWrapper loadMoreWrapper;
    private List<MsgModel> dataList = new ArrayList<>();
    private int pageIndex = 1, pageSize;

    @Override
    protected void buildView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_msg_list);
        init();
//        getData();

    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // 使用Toolbar替换ActionBar
//        setSupportActionBar(toolbar);

        // 设置刷新控件颜色
//        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.theme_bg));
        swipeRefreshLayout.setColorSchemeResources(R.color.theme_font);
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.theme_font_gray);

        // 模拟获取数据
        getData();
        MsgListAdapter msgListAdapter = new MsgListAdapter(dataList, this);
        loadMoreWrapper = new LoadMoreWrapper(msgListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(loadMoreWrapper);

        // 设置下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新数据
                dataList.clear();
                pageIndex = 1;
                getData();
                loadMoreWrapper.notifyDataSetChanged();

                // 延时1s关闭下拉刷新
//                swipeRefreshLayout.postDelayed(() -> {
//                    if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
//                        swipeRefreshLayout.setRefreshing(false);
//                    }
//                }, 300);
            }
        });

        // 设置加载更多监听
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING);

                if (pageIndex < pageSize) {
                    pageIndex++;
                    // 模拟获取网络数据，延时1s
                    getData();
                    loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
                } else {
                    // 显示加载到底的提示
                    loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
                }
            }
        });

    }

    private void hideRefreshToast() {
        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    private void getData() {
        MsgResponse.getMsgRes().getMsgList(this, pageIndex, new ResponseListener<MsgListContextModel>() {
            @Override
            public void success(MsgListContextModel model) {
                hideRefreshToast();
                pageSize = model.getLast_page();
                dataList.addAll(model.convertToMsg());
                loadMoreWrapper.notifyDataSetChanged();
            }

            @Override
            public void netError() {
                hideRefreshToast();
            }
        });

    }


}

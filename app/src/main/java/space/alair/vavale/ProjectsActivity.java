package space.alair.vavale;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import space.alair.vavale.adapters.ProjectsAdapter;
import space.alair.vavale.models.ProjectContextModel;
import space.alair.vavale.models.ProjectModel;
import space.alair.vavale.net_tools.responses.ProjectResponse;
import space.alair.vavale.net_tools.responses.ResponseListener;
import space.alair.vavale.view_tools.LoadMore.EndlessRecyclerOnScrollListener;
import space.alair.vavale.view_tools.LoadMore.LoadMoreWrapper;

public class ProjectsActivity extends BaseActivity {
    private Toolbar toolbar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private LoadMoreWrapper loadMoreWrapper;
    private List<ProjectModel> dataList = new ArrayList<>();
    private int pageIndex = 1, pageSize;
    private String queryWord;
    private EditText et_projects_query;
    private boolean inQuery;
    private boolean isRefresh;

    @Override
    protected void buildView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_projects);
        et_projects_query = findViewById(R.id.et_projects_query);

        init();
        et_projects_query.setOnEditorActionListener((textView, i, keyEvent) -> {

            if (i == EditorInfo.IME_ACTION_SEARCH || (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                if (et_projects_query.getText().toString().trim().length() == 0) {
                    return false;
                }
                queryWord = et_projects_query.getText().toString();
                pageIndex = 1;
                dataList.clear();
                getQueryData();
                return true;
            }
            return false;

        });
        getNoQueryData();
    }

    private void getNoQueryData() {
        ProjectResponse.getRes().getProjectList(this, pageIndex, new ResponseListener<ProjectContextModel>() {
            @Override
            public void success(ProjectContextModel model) {
                hideRefreshToast();
                dataList.addAll(model.getProjectModels());
                pageSize = model.getLast_page();
                loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
                inQuery = false;
            }

            @Override
            public void netError() {
                hideRefreshToast();
            }
        });

    }

    private void hideRefreshToast() {
        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    private void getQueryData() {
        ProjectResponse.getRes().getProjectList(this, pageIndex, queryWord, new ResponseListener<ProjectContextModel>() {
            @Override
            public void success(ProjectContextModel model) {
                hideRefreshToast();
                dataList.addAll(model.getProjectModels());
                pageSize = model.getLast_page();
                loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
                inQuery = true;
            }

            @Override
            public void netError() {
                hideRefreshToast();
            }
        });

    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // 使用Toolbar替换ActionBar
//        setSupportActionBar(toolbar);

        // 设置刷新控件颜色
//        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#4DB6AC"));
        swipeRefreshLayout.setColorSchemeResources(R.color.theme_font);
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.theme_font_gray);

        // 模拟获取数据
//        getData();
        ProjectsAdapter projectsAdapter = new ProjectsAdapter(dataList, this);
        loadMoreWrapper = new LoadMoreWrapper(projectsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(loadMoreWrapper);

        // 设置下拉刷新
        swipeRefreshLayout.setOnRefreshListener(() -> {
            // 刷新数据
            dataList.clear();
            isRefresh = true;
            pageIndex = 1;
            getData();
            loadMoreWrapper.notifyDataSetChanged();

            // 延时1s关闭下拉刷新
//                swipeRefreshLayout.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
//                            swipeRefreshLayout.setRefreshing(false);
//                        }
//                    }
//                }, 1000);
        });

        // 设置加载更多监听
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING);

                if (pageIndex < pageSize) {
                    // 模拟获取网络数据，延时1s
//                    new Timer().schedule(new TimerTask() {
//                        @Override
//                        public void run() {
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
                    pageIndex++;
                    getData();
//                                }
//                            });
//                        }
//                    }, 1000);
                } else {
                    // 显示加载到底的提示
                    loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
                }
            }
        });

    }

    private void getData() {
        queryWord =et_projects_query.getText().toString();
        if ((queryWord == null || queryWord.trim().length() == 0)) {
            getNoQueryData();
        } else {
            getQueryData();
        }

    }


}
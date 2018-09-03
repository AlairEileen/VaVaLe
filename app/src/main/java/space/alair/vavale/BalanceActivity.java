package space.alair.vavale;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import space.alair.vavale.adapters.PaymentsAdapter;
import space.alair.vavale.app_datas.AppControl;
import space.alair.vavale.databinding.ActivityBalanceBinding;
import space.alair.vavale.models.BalanceModel;
import space.alair.vavale.models.PageContentModel;
import space.alair.vavale.models.PayModel;
import space.alair.vavale.models.PaymentModel;
import space.alair.vavale.net_tools.responses.BalanceResponse;
import space.alair.vavale.net_tools.responses.ResponseListener;
import space.alair.vavale.view_tools.LoadMore.EndlessRecyclerOnScrollListener;
import space.alair.vavale.view_tools.LoadMore.LoadMoreWrapper;
import space.alair.vavale.wxapi.WeChatPayModel;
import space.alair.vavale.wxapi.WeChatPayTool;

public class BalanceActivity extends BaseActivity {
    private Toolbar toolbar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private LoadMoreWrapper loadMoreWrapper;
    private List<PaymentModel> dataList = new ArrayList<>();
    private BalanceModel balanceModel = new BalanceModel();
    private ActivityBalanceBinding activityBalanceBinding;
    private TextView tv_balance_money;
    private EditText et_pay_money;
    private int pageIndex = 1, pageSize;

    @Override
    protected void buildView(Bundle savedInstanceState) {
        ActivityBalanceBinding activityBalanceBinding = DataBindingUtil.setContentView(this, R.layout.activity_balance);
        activityBalanceBinding.setBalance(balanceModel);
        tv_balance_money = findViewById(R.id.tv_balance_money);
        et_pay_money = findViewById(R.id.et_pay_money);
        init();
        initBalance();
    }

    private void initBalance() {
        BalanceResponse.getBalanceRes().getBalance(this, new ResponseListener<BalanceModel>() {
            @Override
            public void success(BalanceModel model) {
                String bmm = model.getBalanceMoney();
                balanceModel.setBalanceString(bmm);
                tv_balance_money.setText(balanceModel.getBalanceString());
            }

            @Override
            public void netError() {

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
        getData();
        PaymentsAdapter paymentsAdapter = new PaymentsAdapter(dataList, this);
        loadMoreWrapper = new LoadMoreWrapper(paymentsAdapter);
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
                // 延时1s关闭下拉刷新
//                swipeRefreshLayout.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
//                            swipeRefreshLayout.setRefreshing(false);
//                        }
//                    }
//                }, 1000);
            }
        });

        // 设置加载更多监听
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING);

                if (pageIndex < pageSize) {
                    pageIndex++;
                    getData();

                    // 模拟获取网络数据，延时1s
//                    new Timer().schedule(new TimerTask() {
//                        @Override
//                        public void run() {
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    getData();
//                                    loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
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
        BalanceResponse.getBalanceRes().getPayList(this, new ResponseListener<PageContentModel<PaymentModel>>() {
            @Override
            public void success(PageContentModel<PaymentModel> model) {
                dataList.addAll(model.getModelList());
                pageSize = model.getLast_page();
                loadMoreWrapper.notifyDataSetChanged();
                loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
                if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }

            }

            @Override
            public void netError() {

            }
        });
    }


    public void onPayClick(View view) {
        Log.d("############token:", AppControl.control.getAccountSP().getAccountToken());
        String money = et_pay_money.getText().toString();
        if (money.isEmpty()) {
            return;
        }
        double moneyD = Double.parseDouble(money);
        PayModel payModel = new PayModel();
        payModel.setMoney(moneyD);
        BalanceResponse.getBalanceRes().getPayParams(this, payModel, new ResponseListener<WeChatPayModel>() {
            @Override
            public void success(WeChatPayModel model) {
                doPay(model);
            }

            @Override
            public void netError() {

            }
        });
    }

    private void doPay(WeChatPayModel model) {


        WeChatPayTool.getInstance(this).doPay(model, new WeChatPayTool.WxPayListener() {
            @Override
            public void onError(int code, String msg) {

            }

            @Override
            public void onSuccess() {
                initBalance();
            }
        });
    }

    private void showErrorToastInput() {
        Toast.makeText(this, "金额有误", Toast.LENGTH_SHORT).show();
    }
}

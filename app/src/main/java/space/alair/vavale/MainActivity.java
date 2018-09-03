package space.alair.vavale;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import space.alair.vavale.databinding.ActivityMainBinding;
import space.alair.vavale.models.AccountModel;
import space.alair.vavale.net_tools.responses.AccountResponse;
import space.alair.vavale.net_tools.responses.ResponseListener;
import space.alair.vavale.view_tools.Pop;

public class MainActivity extends BaseActivity {

    public final static String RE_LOGIN_PARAM = "reLogin";
    AccountModel account = new AccountModel();

    @Override
    protected void buildView(Bundle savedInstanceState) {
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setAccount(account);

        boolean reLogin = getIntent().getBooleanExtra(RE_LOGIN_PARAM, false);
        if (AccountResponse.getAccountRes().hasAccount() && !reLogin) {
            goHome();
        }
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        View mDecorView = getWindow().getDecorView();
//        mDecorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
//                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar |
//                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }


    public void onLoginClick(View view) {
        AccountResponse.getAccountRes().doLogin(this, account, new ResponseListener<AccountModel>() {
            @Override
            public void success(AccountModel model) {
                goHome();
            }

            @Override
            public void netError() {

            }
        });


    }

    private void goHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void onSignUpClick(View view) {
//        AccountAction testAction = RetrofitBuilder.build().create(AccountAction.class);

        Pop.createOkPop(this, R.layout.pop_ok, view, () -> {


        }, "请联系售后管理人员", "400-886-1898", "我知道了");

    }
}

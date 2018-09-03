package space.alair.vavale;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import space.alair.vavale.models.BalanceModel;
import space.alair.vavale.models.MsgModel;
import space.alair.vavale.models.PhoneNumberModel;
import space.alair.vavale.net_tools.responses.BalanceResponse;
import space.alair.vavale.net_tools.responses.PhoneResponse;
import space.alair.vavale.net_tools.responses.ResponseListener;
import space.alair.vavale.view_tools.MsgPop;

public class HomeActivity extends BaseActivity {

    public final static int GET_PROJECT = 1000, GET_NEW_NUMBER = 1001;
    public final static String PROJECT_ID = "projectId";
    private String selectedProjectID;
    private PhoneNumberModel prePhone, currentPhone;
    private TextView tv_home_current_phone_number, tv_home_new_msg_phone, tv_home_new_msg_content, tv_home_new_msg_date, tv_home_project_phone_content2, tv_home_project_phone_content, tv_home_pre_phone_number, tv_home_no_number, tv_home_number_toast;
    private Button btn_home_no_number,btn_home_re_get;
    private LinearLayout ll_home_has_number;
    private MsgPop msgPop;

    @Override
    protected void buildView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
        initListeners();
        refreshMsgPop();
        initNumberView();
        initBalance();
        initPrePhoneNumber();
        HomeService.homeActivity = this;
        initMsgView();
        if (HomeService.homeService == null) {
            startService(new Intent(this, HomeService.class));
        }
        initNumberViewData();
        initMsgData();
    }

    private void initListeners() {
        btn_home_re_get=findViewById(R.id.btn_home_re_get);
        btn_home_re_get.setOnClickListener(view->{
            onReGetPhoneClick(view);
        });
    }

    public void refreshMsgPop() {

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    try {
                        if (!HomeService.homeService.hasNumber && HomeService.homeService.msgModel == null)
                            return;
                        if (msgPop != null) msgPop.dismiss();
                        msgPop = new MsgPop(HomeActivity.this, tv_home_pre_phone_number);
                        if (HomeService.homeService.hasNumber)
                            msgPop.onReceivingMsg(HomeService.homeService.currentPhone.getNumber());
                        if (!HomeService.homeService.hasNumber && HomeService.homeService.msgModel != null)
                            msgPop.onReceivedMsg(HomeService.homeService.msgModel.getPhone(), HomeService.homeService.msgModel.getContent());
                    } catch (Exception e) {
                    }
                });
            }
        }, 500);

    }

    private void initMsgData() {
        if (HomeService.homeService != null)
            refreshMsg(HomeService.homeService.msgModel);
    }

    private void initNumberViewData() {
        showNumber();
    }

    private void initMsgView() {
        tv_home_new_msg_phone = findViewById(R.id.tv_home_new_msg_phone);
        tv_home_new_msg_date = findViewById(R.id.tv_home_new_msg_date);
        tv_home_new_msg_content = findViewById(R.id.tv_home_new_msg_content);
    }

    private void initNumberView() {
        tv_home_current_phone_number = findViewById(R.id.tv_home_current_phone_number);
        tv_home_pre_phone_number = findViewById(R.id.tv_home_pre_phone_number);
        tv_home_no_number = findViewById(R.id.tv_home_no_number);
        tv_home_project_phone_content2 = findViewById(R.id.tv_home_project_phone_content2);
        tv_home_project_phone_content = findViewById(R.id.tv_home_project_phone_content);
        tv_home_number_toast = findViewById(R.id.tv_home_number_toast);
        btn_home_no_number = findViewById(R.id.btn_home_no_number);
        ll_home_has_number = findViewById(R.id.ll_home_has_number);
    }

    public void setNumberViewData(String content) {
        runOnUiThread(() -> {
            tv_home_number_toast.setText(content);
        });

    }

    public void showNumber() {
        if (HomeService.homeService == null) return;
        selectedProjectID = HomeService.homeService.selectedProjectID;
        HomeActivity.this.runOnUiThread(() -> {

            if (HomeService.homeService.hasNumber) {
                refreshMsgPop();

                tv_home_current_phone_number.setVisibility(View.VISIBLE);
                tv_home_project_phone_content.setVisibility(View.VISIBLE);
                tv_home_no_number.setVisibility(View.GONE);

                tv_home_number_toast.setText("5:00后自动释放");
                if (HomeService.homeService != null) {
                    HomeService.homeService.getMsg();
                    tv_home_current_phone_number.setText(HomeService.homeService.currentPhone.getNumber());
                    if (msgPop != null)
                        msgPop.onReceivingMsg(HomeService.homeService.currentPhone.getNumber());
                }

            } else {
//                tv_home_no_number.setVisibility(View.VISIBLE);
//                tv_home_current_phone_number.setVisibility(View.GONE);
//                tv_home_project_phone_content.setVisibility(View.GONE);
                setNumberViewData("手机号成功接收短信后会自动释放");
                if (HomeService.homeService != null && msgPop != null && HomeService.homeService.currentPhone != null) {
                    msgPop.onTimeout();
                }
            }
            ll_home_has_number.setVisibility(selectedProjectID == null ? View.GONE : View.VISIBLE);
            btn_home_no_number.setVisibility(selectedProjectID == null ? View.VISIBLE : View.GONE);
        });

    }

    private void initPrePhoneNumber() {
        PhoneResponse.getPhoneRes().getPrePhoneNumber(this, new ResponseListener<PhoneNumberModel>() {
            @Override
            public void success(PhoneNumberModel model) {
                prePhone = model;
                tv_home_pre_phone_number.setText(prePhone.getNumber());
                tv_home_project_phone_content.setText(model.getProject().getContent());
                tv_home_project_phone_content2.setText(model.getProject().getContent());
            }

            @Override
            public void netError() {

            }
        });


    }

    private void initBalance() {
        TextView tv_home_balance = findViewById(R.id.tv_home_balance);
        BalanceResponse.getBalanceRes().getBalance(this, new ResponseListener<BalanceModel>() {
            @Override
            public void success(BalanceModel model) {
                String bmm = model.getBalanceMoney();
                tv_home_balance.setText(bmm);
            }

            @Override
            public void netError() {

            }
        });


    }

    public void onBalanceClick(View view) {
        Intent intent = new Intent(this, BalanceActivity.class);
        startActivity(intent);
    }

    public void onMsgClick(View view) {
        Intent intent = new Intent(this, MsgListActivity.class);
        startActivity(intent);


    }

    public void onChangeProject(View view) {
        Intent intent = new Intent(this, ProjectsActivity.class);
        startActivityForResult(intent, GET_PROJECT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            switch (requestCode) {
                case GET_NEW_NUMBER:
                    selectedProjectID = data.getStringExtra(PROJECT_ID);
                    if (HomeService.homeService != null)
                        HomeService.homeService.selectedProjectID = selectedProjectID;
                    getNewNumber();
                    break;
                case GET_PROJECT:
                    selectedProjectID = data.getStringExtra(PROJECT_ID);
                    if (HomeService.homeService != null)
                        HomeService.homeService.selectedProjectID = selectedProjectID;
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
        }
    }

    private void getNewNumber() {
        PhoneResponse.getPhoneRes().getPhoneNumber(this, selectedProjectID, new ResponseListener<PhoneNumberModel>() {
            @Override
            public void success(PhoneNumberModel model) {
                currentPhone = model;
                tv_home_current_phone_number.setText(model.getNumber());
                if (HomeService.homeService != null) {
                    HomeService.homeService.stopNumberTimer();
                    HomeService.homeService.currentPhone = model;
                    HomeService.homeService.onNumberChanged(true);
                }
                initPrePhoneNumber();
            }

            @Override
            public void netError() {

            }
        });
    }

    public void refreshMsg(MsgModel msgModel) {
        if (msgPop != null && msgModel != null) {
            msgPop.onReceivedMsg(msgModel.getPhone(), msgModel.getContent());
        }
        runOnUiThread(() -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            tv_home_new_msg_phone.setText(msgModel == null ? "" : msgModel.getPhone());
            tv_home_new_msg_date.setText(msgModel == null ? "" : sdf.format(new Date()));
            tv_home_new_msg_content.setText(msgModel == null ? "" : msgModel.getContent());
        });
    }

    public void onGetNewNumberClick(View view) {

        if (selectedProjectID == null || selectedProjectID.trim().length() == 0) {
            Toast.makeText(this, "请先选择项目", Toast.LENGTH_SHORT).show();
            return;
        }
        getNewNumber();
    }

    public void onReGetPhoneClick(View view) {
        if (prePhone == null) return;
        PhoneResponse.getPhoneRes().getPhoneNumber(this, selectedProjectID, prePhone.getNumber(), new PhoneResponse.PhoneResReGetListener<PhoneNumberModel>() {
            @Override
            public void noNumberUsed() {

            }

            @Override
            public void success(PhoneNumberModel model) {
                currentPhone = model;
                tv_home_current_phone_number.setText(model.getNumber());
                selectedProjectID = model.getProjectID();
                if (HomeService.homeService != null) {
                    HomeService.homeService.stopNumberTimer();
                    HomeService.homeService.currentPhone = model;
                    HomeService.homeService.onNumberChanged(true);
                }
            }

            @Override
            public void netError() {
                HomeActivity.this.runOnUiThread(() -> {
                    Toast.makeText(HomeActivity.this, "该号码正在使用中", Toast.LENGTH_SHORT).show();
                });
            }
        });

    }

    public void onGetNewNumberAddProjectClick(View view) {
        Intent intent = new Intent(this, ProjectsActivity.class);
        startActivityForResult(intent, GET_NEW_NUMBER);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return false;
    }

}

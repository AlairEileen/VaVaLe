package space.alair.vavale.view_tools;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import space.alair.vavale.R;

public class MsgPop extends PopupWindow {


    private TextView
            tv_pop_msg_close_number,
            tv_pop_msg_close_content,
            tv_msg_close_wait_phone,
            tv_pop_msg_close_pb_text;

    private Button btn_pop_msg_close_close;
    private LinearLayout pop_msg_close_wait, pop_msg_close_content;
    private ProgressBar pb_pop_msg_close;

    public MsgPop(Context context, View anchor) {
        super(LayoutInflater
                        .from(context)
                        .inflate(R.layout.pop_msg_close,
                                null),
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        initSettings(context, anchor);
        initViews();


    }

    private void setCloseButtonVisible(boolean visible) {
        btn_pop_msg_close_close.setVisibility(visible ? View.VISIBLE : View.GONE);
    }


    private void setMsgWaitVisible(boolean visible) {
        pop_msg_close_wait.setVisibility(visible ? View.VISIBLE : View.GONE);
        pop_msg_close_content.setVisibility(!visible ? View.VISIBLE : View.GONE);
        setCloseButtonVisible(!visible);
    }

    public void onTimeout() {
        setProgressBarVisible(false);
    }

    public void onReceivedMsg(String number, String content) {
        setMsgWaitVisible(false);
        setContentText(content);
        setNumberText(number);
    }

    public void onReceivingMsg(String number) {
        setMsgWaitVisible(true);
        setNumberText(number);
    }

    private void initViews() {
        pop_msg_close_wait = getContentView().findViewById(R.id.pop_msg_close_wait);
        pop_msg_close_content = getContentView().findViewById(R.id.pop_msg_close_content);


        btn_pop_msg_close_close = getContentView().findViewById(R.id.btn_pop_msg_close_close);
        btn_pop_msg_close_close.setOnClickListener(view -> {
            dismiss();
        });

        pb_pop_msg_close = getContentView().findViewById(R.id.pb_pop_msg_close);
        tv_pop_msg_close_number = getContentView().findViewById(R.id.tv_pop_msg_close_number);
        tv_pop_msg_close_pb_text = getContentView().findViewById(R.id.tv_pop_msg_close_pb_text);
        tv_pop_msg_close_content = getContentView().findViewById(R.id.tv_pop_msg_close_content);
        tv_msg_close_wait_phone = getContentView().findViewById(R.id.tv_msg_close_wait_phone);

    }

    private void setNumberText(String numberText) {
        tv_pop_msg_close_number.setText(numberText);
        tv_msg_close_wait_phone.setText(numberText);
    }

    private void setContentText(String contentText) {
        tv_pop_msg_close_content.setText(contentText);
    }

    private void setProgressBarVisible(boolean visible) {
        pb_pop_msg_close.setVisibility(visible ? View.VISIBLE : View.GONE);
        tv_pop_msg_close_pb_text.setText(visible ? "短信接收中..." : "短信接受失败");
        setCloseButtonVisible(!visible);
    }


    private void initSettings(Context context, View anchor) {
        setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.theme_bg_title_black)));
        // 设置PopupWindow是否能响应外部点击事件
        setOutsideTouchable(false);
        // 设置PopupWindow是否能响应点击事件
        setTouchable(true);
//        setFocusable(true);
        getContentView().setFocusable(true);
        getContentView().setFocusableInTouchMode(true);

        // 显示PopupWindow，其中：
        // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
//        window.showAsDropDown(anchor, xoff, yoff);
//        window.showAsDropDown();
        showAtLocation(anchor, 0, 0, 0);
        getContentView().setOnKeyListener((view, i, keyEvent) -> {
            if (keyEvent.getAction() == KeyEvent.KEYCODE_BACK) return true;
            return false;
        });
    }
}

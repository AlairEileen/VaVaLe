package space.alair.vavale.view_tools;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import space.alair.vavale.R;

/**
 * Created by Alair on 3/20/2018.
 */

public class Pop {

    public static View tempView;

    public static void createOkPop(Context context, int layoutRes, View anchor, OkPopBuilder okPopBuilder, String title, String content, String okText) {
        // 用于PopupWindow的View

        View contentView = LayoutInflater.from(context).inflate(layoutRes, null, false);
        // 创建PopupWindow对象，其中：
        // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
        // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
        PopupWindow window = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        // 设置PopupWindow的背景
        window.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.theme_bg_black)));
        // 设置PopupWindow是否能响应外部点击事件
        window.setOutsideTouchable(true);
        // 设置PopupWindow是否能响应点击事件
        window.setTouchable(true);
        // 显示PopupWindow，其中：
        // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
//        window.showAsDropDown(anchor, xoff, yoff);
//        window.showAsDropDown();
        window.showAtLocation(anchor, 0, 0, 0);
        // 或者也可以调用此方法显示PopupWindow，其中：
        // 第一个参数是PopupWindow的父View，第二个参数是PopupWindow相对父View的位置，
        // 第三和第四个参数分别是PopupWindow相对父View的x、y偏移
        // window.showAtLocation(parent, gravity, x, y);
        Button btnOk = ((Button) contentView.findViewById(R.id.btn_ok));
        btnOk.setText(okText);
        btnOk.setOnClickListener(view -> {
            window.dismiss();
            okPopBuilder.OnOk();
        });
        ((TextView) contentView.findViewById(R.id.tv_title)).setText(title);
        ((TextView) contentView.findViewById(R.id.tv_content)).setText(content);
    }

    public static void createOkCancelPop(Context context, int layoutRes, View anchor, OkPopBuilder okPopBuilder, String content) {
        // 用于PopupWindow的View

        View contentView = LayoutInflater.from(context).inflate(layoutRes, null, false);
        // 创建PopupWindow对象，其中：
        // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
        // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
        PopupWindow window = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        // 设置PopupWindow的背景
        window.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.theme_bg_black)));
        // 设置PopupWindow是否能响应外部点击事件
        window.setOutsideTouchable(true);
        // 设置PopupWindow是否能响应点击事件
        window.setTouchable(true);
        // 显示PopupWindow，其中：
        // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
//        window.showAsDropDown(anchor, xoff, yoff);
//        window.showAsDropDown();
        window.showAtLocation(anchor, 0, 0, 0);
        // 或者也可以调用此方法显示PopupWindow，其中：
        // 第一个参数是PopupWindow的父View，第二个参数是PopupWindow相对父View的位置，
        // 第三和第四个参数分别是PopupWindow相对父View的x、y偏移
        // window.showAtLocation(parent, gravity, x, y);
        contentView.findViewById(R.id.btn_ok_cancel_ok).setOnClickListener(view -> {
            window.dismiss();
            okPopBuilder.OnOk();
        });
        contentView.findViewById(R.id.btn_ok_cancel_cancel).setOnClickListener(view -> {
            window.dismiss();
        });
        ((TextView) contentView.findViewById(R.id.tv_content)).setText(content);
    }


    public static PopupWindow createMsgClosePop(Context context, int layoutRes, View anchor, boolean isWait, String currentPhone, String number, String content) {
        // 用于PopupWindow的View

        View contentView = LayoutInflater.from(context).inflate(layoutRes, null, false);
        contentView.setFocusable(true);
        contentView.setFocusableInTouchMode(true);

        // 创建PopupWindow对象，其中：
        // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
        // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
        PopupWindow window = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        // 设置PopupWindow的背景
        window.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.theme_bg_title_black)));
        // 设置PopupWindow是否能响应外部点击事件
        window.setOutsideTouchable(false);
        // 设置PopupWindow是否能响应点击事件
        window.setTouchable(true);

        // 显示PopupWindow，其中：
        // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
//        window.showAsDropDown(anchor, xoff, yoff);
//        window.showAsDropDown();
        window.showAtLocation(anchor, 0, 0, 0);
        // 或者也可以调用此方法显示PopupWindow，其中：
        // 第一个参数是PopupWindow的父View，第二个参数是PopupWindow相对父View的位置，
        // 第三和第四个参数分别是PopupWindow相对父View的x、y偏移
        // window.showAtLocation(parent, gravity, x, y);

        contentView.setOnKeyListener((view, i, keyEvent) -> {
            if (keyEvent.getAction() == KeyEvent.KEYCODE_BACK) return true;
            return false;
        });
        contentView.findViewById(R.id.pop_msg_close_wait).setVisibility(isWait ? View.VISIBLE : View.GONE);
        contentView.findViewById(R.id.pop_msg_close_content).setVisibility(!isWait ? View.VISIBLE : View.GONE);
        Button close = contentView.findViewById(R.id.btn_pop_msg_close_close);
        close.setVisibility(!isWait ? View.VISIBLE : View.GONE);
        close.setOnClickListener(view -> {
            window.dismiss();
        });
        tempView = close;

        ((TextView) contentView.findViewById(R.id.tv_pop_msg_close_number)).setText(number);
        ((TextView) contentView.findViewById(R.id.tv_pop_msg_close_content)).setText(content);
        ((TextView) contentView.findViewById(R.id.tv_msg_close_wait_phone)).setText(currentPhone);
        return window;
    }


    public interface OkPopBuilder {

        void OnOk();
    }


}

package space.alair.vavale.models;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import space.alair.vavale.MainActivity;
import space.alair.vavale.net_tools.responses.AccountResponse;

/**
 * Created by Alair on 3/23/2018.
 */

public class BaseModel<T> {
    private Integer code;
    private T data;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void getData(DataExecutor<T> dataExecutor, Context context) {
        switch (code) {
            case StatusCode.SUCCESS:
                dataExecutor.success(data);
                break;
            case StatusCode.ERROR:
                dataExecutor.error(StatusCode.ERROR);
                break;
            case StatusCode.REFRESH_TOKEN_TIME_OUT:
                reLogin(context);
                break;
            case StatusCode.TOKEN_TIME_OUT:
                AccountResponse.getAccountRes().getTokens(dataExecutor, context);
                break;
            case StatusCode.NO_PHONE_NUMBER:
                dataExecutor.error(StatusCode.NO_PHONE_NUMBER);
                break;
            case StatusCode.NUMBER_USED:
                dataExecutor.error(StatusCode.NUMBER_USED);
                break;
            default:
                if (context instanceof Activity) {
                    ((Activity) context).runOnUiThread(() -> {
                        Toast.makeText(context,code+":"+ msg, Toast.LENGTH_SHORT).show();
                    });
                }
                break;
        }
    }

    private void reLogin(Context context) {
        if (context instanceof Service) {
            return;
        }
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MainActivity.RE_LOGIN_PARAM, true);
        context.startActivity(intent);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

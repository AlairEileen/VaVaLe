package space.alair.pay_common.asyncTask;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import space.alair.pay_common.service.IPayLogic;

public class WeChatPayTask extends AsyncTask<Object, Integer, String> {
    private Activity mContext;

    public WeChatPayTask(Activity context) {
        this.mContext = context;
    }

    @Override
    protected String doInBackground(Object... params) {
        try {
            return (String) params[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    protected void onPostExecute(String result) {
        try {
            if (result != null) {
                System.out.println("TestPayPrepay result>" + result);
                JSONObject data = new JSONObject(result);
                if (!data.has("code")) {
                    String sign = data.getString("paySign");
                    String timestamp = data.getString("timeStamp");
                    String noncestr = data.getString("nonceStr");
                    String partnerid = data.getString("signType");
                    String prepayid = data.getString("package");
                    String appid = data.getString("appId");
                    Toast.makeText(mContext, "正在调起支付", Toast.LENGTH_SHORT).show();

                    IPayLogic.getInstance(mContext).startWXPay(appid, partnerid, prepayid, noncestr, timestamp, sign);
                } else {
                    String message = data.getString("message");
                    Log.d("PAY_GET", "返回错误" + message);
                    Toast.makeText(mContext, "返回错误:" + message, Toast.LENGTH_SHORT).show();
                }
            } else {
                System.out.println("get  prepayid exception, is null");
            }
        } catch (Exception e) {
            Log.e("PAY_GET", "异常：" + e.getMessage());
            Toast.makeText(mContext, "异常：" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        super.onPostExecute(result);
    }

}


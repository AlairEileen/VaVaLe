package space.alair.pay_common.service;

import android.app.Activity;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import space.alair.pay_common.entity.OrderPay;
import space.alair.pay_common.utils.Constants;
import space.alair.pay_common.utils.HttpKit;
import space.alair.we_chat_pay.JPay;


public class IPayLogic {
    private static IPayLogic mIPayLogic;
    private Activity mContext;

    private IPayLogic(Activity context) {
        mContext = context;
    }

    public static IPayLogic getInstance(Activity context) {
        if (mIPayLogic == null) {
            synchronized (IPayLogic.class) {
                if (mIPayLogic == null) {
                    mIPayLogic = new IPayLogic(context);
                }
            }
        }
        return mIPayLogic;
    }

    /**
     * 获取预付订单
     *
     * @param orderPay
     * @return
     */
    public String WXPay(OrderPay orderPay) {
        String body = orderPay.getBody();
        String attach = orderPay.getAttach();
        int total_fee = orderPay.getTotalFee();
        String notify_url = orderPay.getNofityUrl();
        String device_info = orderPay.getDeviceInfo();


        Map<String, String> queryParas = new HashMap<String, String>();
        queryParas.put("body", body);
        queryParas.put("attach", attach);
        queryParas.put("total_fee", total_fee * 100 + "");
        queryParas.put("notify_url", notify_url);
        queryParas.put("device_info", device_info);

        String result = HttpKit.get(orderPay.getPay_url(), queryParas);
        return result;
    }

    /**
     * 获取支付宝App支付订单信息
     *
     * @return
     */
    public String getAliPayOrderInfo(OrderPay orderPay) {
        String result = HttpKit.get(Constants.ALIPAY_URL);
        return result;
    }

    /**
     * 获取银联支付的tn信息
     * @param orderPay
     * @return
     */
    public String getUPPayOrderInfo(OrderPay orderPay) {
        String result = HttpKit.get(Constants.UPPAY_URL);
        return result;
    }


    public void startAliPay(final String orderInfo) {
        JPay.getIntance(mContext).toPay(JPay.PayMode.ALIPAY, orderInfo, new JPay.JPayListener() {
            @Override
            public void onPaySuccess() {
                Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPayError(int error_code, String message) {
                Toast.makeText(mContext, "支付失败>" + error_code + " " + message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPayCancel() {
                Toast.makeText(mContext, "取消了支付", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUUPay(String dataOrg, String sign, String mode) {

            }
        });
    }


    /**
     * 调起支付
     *
     * @param appId
     * @param partnerId
     * @param prepayId
     * @param nonceStr
     * @param timeStamp
     * @param sign
     */
    public void startWXPay(String appId, String partnerId, String prepayId,
                           String nonceStr, String timeStamp, String sign) {

        JPay.getIntance(mContext).toWxPay(appId, partnerId, prepayId, nonceStr, timeStamp, sign, new JPay.JPayListener() {
            @Override
            public void onPaySuccess() {
                Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPayError(int error_code, String message) {
                Toast.makeText(mContext, "支付失败>" + error_code + " " + message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPayCancel() {
                Toast.makeText(mContext, "取消了支付", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUUPay(String dataOrg, String sign, String mode) {

            }
        });
    }

    public void startUPPay(String tn) {

        JPay.getIntance(mContext).toUUPay("01",tn, new JPay.JPayListener() {
            @Override
            public void onPaySuccess() {
                Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPayError(int error_code, String message) {
                Toast.makeText(mContext, "支付失败>" + error_code + " " + message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPayCancel() {
                Toast.makeText(mContext, "取消了支付", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUUPay(String dataOrg, String sign, String mode) {
                Toast.makeText(mContext, "支付成功>需要后台查询订单确认>"+dataOrg+" "+mode, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

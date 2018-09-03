package space.alair.vavale.wxapi;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.List;


public class WeChatPayTool {

    private static WeChatPayTool weChatPayTool;
    private Activity activity;
    private IWXAPI iwxapi;
    private WxPayListener wxPayListener;

    private WeChatPayTool(Activity activity) {
        this.activity = activity;
    }

    public static WeChatPayTool getInstance(Activity activity) {
        if (weChatPayTool == null) {
            weChatPayTool = new WeChatPayTool(activity);
        }
        return weChatPayTool;
    }

    /**
     * 初始化微信支付接口
     *
     * @param appId
     */
    private void init(String appId) {
        iwxapi = WXAPIFactory.createWXAPI(activity, null);
        iwxapi.registerApp(appId);
    }

    public void doPay(WeChatPayModel weChatPayModel, WxPayListener listener) {
        wxPayListener = listener;
        init(weChatPayModel.getAppId());

        if (!checkWx()) {
            if (listener != null) {
                listener.onError(-2, "未安装微信或者微信版本过低");
            }
            return;
        }


        PayReq request = new PayReq();
        request.appId = weChatPayModel.getAppId();
        request.partnerId = weChatPayModel.getPartnerId();
        request.prepayId = weChatPayModel.getPrepayId();
        request.packageValue = "Sign=WXPay";
        request.nonceStr = weChatPayModel.getNonceStr();
        request.timeStamp = weChatPayModel.getTimeStamp();
        request.sign = weChatPayModel.getPaySign();
        iwxapi.sendReq(request);
    }

    //检测微信客户端是否支持微信支付
    private boolean checkWx() {
        return installedWeiChat() && iwxapi.isWXAppInstalled() && iwxapi.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
    }

    /**
     * 判断微信是否安装
     *
     * @return
     */
    private boolean installedWeiChat() {
        return installedApp("com.tencent.mm");
    }

    /**
     * 判断app是否安装
     *
     * @param packageName
     * @return
     */
    private boolean installedApp(String packageName) {
        final PackageManager packageManager = activity.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void onPayResponse(int errCode, String errStr) {
        if (wxPayListener != null) {
            switch (errCode) {
                case 0:
                    wxPayListener.onSuccess();
                    break;
                case -1:
                case -2:
                    wxPayListener.onError(errCode,errStr);
                    break;
            }
        }
    }

    public IWXAPI getWXApi() {
        return iwxapi;
    }

    public interface WxPayListener {
        void onError(int code, String msg);
        void onSuccess();
    }
}

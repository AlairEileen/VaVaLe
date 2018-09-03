package space.alair.vavale.wxapi;

import com.google.gson.annotations.SerializedName;

public class WeChatPayModel {
    @SerializedName("appid")
    private String appId;
    @SerializedName("noncestr")
    private String nonceStr;
    @SerializedName("package")
    private String wx_package;
    @SerializedName("partnerid")
    private String partnerId;
    @SerializedName("prepayid")
    private String prepayId;
    @SerializedName("signtype")
    private String signType;
    @SerializedName("timestamp")
    private String timeStamp;
    @SerializedName("paysign")
    private String paySign;

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getWx_package() {
        return wx_package;
    }

    public void setWx_package(String wx_package) {
        this.wx_package = wx_package;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }
}

package space.alair.vavale.models;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

/**
 * Created by Alair on 3/22/2018.
 */

public class BalanceModel extends BaseModel<BalanceModel> {
    @SerializedName("money")
    public Integer balance;
    private String payMoney;
    private String balanceString;

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getBalanceMoney() {
        if (balance == null) {
            return 0 + "";
        }
        double a = balance;
        double b = balance / 100;
        balanceString = new java.text.DecimalFormat("#.00").format(b);

        BigDecimal b1=new BigDecimal("100");
        BigDecimal b2=new BigDecimal(balance.toString());

        return b2.divide(b1,2,BigDecimal.ROUND_UNNECESSARY).toString();
    }

    public String getBalanceString() {

        return balanceString;
    }

    public void setBalanceString(String balanceString) {
        this.balanceString = balanceString;
    }
}

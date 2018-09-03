package space.alair.vavale.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alair on 3/22/2018.
 */

public class PaymentModel {
    @SerializedName("money")
    private Integer payment;
    @SerializedName("wx_notify_time")
    private String payment_date;

    public Integer getPayment() {
        return payment;
    }

    public void setPayment(Integer payment) {
        this.payment = payment;
    }

    public String getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(String payment_date) {
        this.payment_date = payment_date;
    }
}

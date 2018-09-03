package space.alair.vavale.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alair on 3/22/2018.
 */

public class MsgModel {

    private String phone;
    private String date;
    @SerializedName("tst")
    private String content;
    private double price;
    private String receiveNumber;
    private String number;
    private boolean isRead;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getReceiveNumber() {
        return receiveNumber;
    }

    public void setReceiveNumber(String receiveNumber) {
        this.receiveNumber = receiveNumber;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getPhone() {
        if (phone == null) return number;
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

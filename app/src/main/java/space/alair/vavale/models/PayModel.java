package space.alair.vavale.models;

public class PayModel {
    private Double money;
    private String trade_type="APP";

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getTrade_type() {
        return trade_type;
    }


}

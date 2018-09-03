package space.alair.vavale.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alair on 3/26/2018.
 */

public class MsgListContextModel {

    @SerializedName("data")
    List<MsgContentModel> msgContentModels;
    private Integer total;
    private Integer per_page;
    private Integer last_page;
    private Integer current_page;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPer_page() {
        return per_page;
    }

    public void setPer_page(Integer per_page) {
        this.per_page = per_page;
    }

    public Integer getLast_page() {
        return last_page;
    }

    public void setLast_page(Integer last_page) {
        this.last_page = last_page;
    }

    public Integer getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(Integer current_page) {
        this.current_page = current_page;
    }

    public List<MsgContentModel> getMsgContentModels() {
        return msgContentModels;
    }

    public void setMsgContentModels(List<MsgContentModel> msgContentModels) {
        this.msgContentModels = msgContentModels;
    }

    public List<MsgModel> convertToMsg() {
        if (msgContentModels == null) return null;
        List<MsgModel> mmList = new ArrayList<>();
        for (MsgContentModel mcm : msgContentModels) {
            MsgModel mm = new MsgModel();
            mm.setPhone(mcm.getPhone());
            mm.setContent(mcm.getContent());
            mm.setDate(mcm.getCreate_time());
            mmList.add(mm);
        }
        return mmList;
    }
}

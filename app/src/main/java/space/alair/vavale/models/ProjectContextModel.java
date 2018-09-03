package space.alair.vavale.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Alair on 3/26/2018.
 */

public class ProjectContextModel  {

    @SerializedName("data")
    List<ProjectModel> projectModels;
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

    public List<ProjectModel> getProjectModels() {
        return projectModels;
    }

    public void setProjectModels(List<ProjectModel> projectModels) {
        this.projectModels = projectModels;
    }
}

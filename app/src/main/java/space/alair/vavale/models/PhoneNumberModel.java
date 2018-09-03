package space.alair.vavale.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alair on 3/26/2018.
 */

public class PhoneNumberModel {
    private String number;
    @SerializedName("project_id")
    private String projectID;
    private String phone;
    private ProjectModel project;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getNumber() {
        if (phone != null && number != phone) number = phone;
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public ProjectModel getProject() {
        return project;
    }

    public void setProject(ProjectModel project) {
        this.project = project;
    }
}

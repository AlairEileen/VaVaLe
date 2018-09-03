package space.alair.vavale.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alair on 3/22/2018.
 */

public class ProjectModel {
    @SerializedName("name")
    private String content;
    @SerializedName("money")
    private double price;
    @SerializedName("matchingText")
    private String word;

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}

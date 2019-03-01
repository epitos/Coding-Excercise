package exercise.coding.codiingexercisejpmc.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Album extends RealmObject {

    @SerializedName("title")
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

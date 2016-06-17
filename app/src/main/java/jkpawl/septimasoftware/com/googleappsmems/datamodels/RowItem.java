package jkpawl.septimasoftware.com.googleappsmems.datamodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RowItem {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("img")
    @Expose
    public String img;

    public RowItem(Integer id, String name, String img) {
        this.id = id;
        this.name = name;
        this.img = img;
    }
}
package jkpawl.septimasoftware.com.googleappsmems.datamodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by J.Pawluczuk on 6/17/16.
 */
public class RowList {

    @SerializedName("list")
    @Expose
    public List<RowItem> list = new ArrayList<>();

}
package com.delta_inductions.delta_task_3.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultResponse {
    @SerializedName("labels")
    private List<Labels> listlabel;

    public List<Labels> getListlabel() {
        return listlabel;
    }
}

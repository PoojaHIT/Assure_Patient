package com.hit.assure.Model;

import java.io.Serializable;

public class NutrituonHelplist implements Serializable {

    private boolean isChecked = false;

    public NutrituonHelplist(String name) {
        this.name = name;

    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package com.hit.assure.Model.Note;

import com.google.gson.annotations.SerializedName;

public class NoteData {

    @SerializedName("note")
    private String note;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

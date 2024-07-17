package com.hit.assure.Model.Note;

import com.google.gson.annotations.SerializedName;

public class NoteResponse {

    @SerializedName("ResponseCode")
    private int ResponseCode;

    @SerializedName("Result")
    private String Result;

    @SerializedName("ResponseMsg")
    private String ResponseMsg;

    @SerializedName("data")
    private NoteData noteData;

    public int getResponseCode() {
        return ResponseCode;
    }

    public void setResponseCode(int responseCode) {
        ResponseCode = responseCode;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    public String getResponseMsg() {
        return ResponseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        ResponseMsg = responseMsg;
    }

    public NoteData getNoteData() {
        return noteData;
    }

    public void setNoteData(NoteData noteData) {
        this.noteData = noteData;
    }
}

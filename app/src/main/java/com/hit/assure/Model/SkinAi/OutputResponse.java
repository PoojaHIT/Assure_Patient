package com.hit.assure.Model.SkinAi;

import com.google.gson.annotations.SerializedName;

public class OutputResponse {


    @SerializedName("input")
    private InputData inputData;

    public InputData getInputData() {
        return inputData;
    }

    public void setInputData(InputData inputData) {
        this.inputData = inputData;
    }

    @SerializedName("output")
    private OutData outData;

    public OutData getOutData() {
        return outData;
    }

    public void setOutData(OutData outData) {
        this.outData = outData;
    }
}

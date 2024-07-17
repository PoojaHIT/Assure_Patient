package com.hit.assure.Model.SkinAi;

import com.google.gson.annotations.SerializedName;

public class OutData {

    @SerializedName("outputData")
    private OutputData outputData;

    @SerializedName("outputImages")
    private OutputImages outputImages;

    public OutputData getOutputData() {
        return outputData;
    }

    public void setOutputData(OutputData outputData) {
        this.outputData = outputData;
    }

    public OutputImages getOutputImages() {
        return outputImages;
    }

    public void setOutputImages(OutputImages outputImages) {
        this.outputImages = outputImages;
    }
}

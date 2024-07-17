package com.hit.assure.Retrofit;



import com.hit.assure.Model.SkinAi.OutputResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UploadReceiptService {

    @Headers({"Accept:application/json", "Ocp-Apim-Subscription-Key: 9024cfd08576433a93c1b0fd465faee8"})
    @POST("media/skinanalysis/upload?access_token=swUcLnLRFX32gEDY5YrcgND8zPuTryGofVNh5ZM49P9QzTZNdPIru5zcRJImOWwZ")
    Call<OutputResponse> getSkinResult(
            @Body RequestBody file);
}

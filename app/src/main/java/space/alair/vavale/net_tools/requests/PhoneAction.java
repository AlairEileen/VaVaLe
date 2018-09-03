package space.alair.vavale.net_tools.requests;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import space.alair.vavale.models.BaseModel;
import space.alair.vavale.models.PhoneNumberModel;

/**
 * Created by Alair on 3/26/2018.
 */

public interface PhoneAction {


    @GET("xcx/v1/phone/{projectId}")
    Call<BaseModel<PhoneNumberModel>> getPhoneNumber(@Path("projectId") String projectId);

    @GET("xcx/v1/phone/{projectId}")
    Call<BaseModel<PhoneNumberModel>> getPhoneNumber(@Path("projectId") String projectId, @Query("phone") String phoneNumber);

    @GET("xcx/v1/lastphone")
    Call<BaseModel<PhoneNumberModel>> getPrePhoneNumber();
}


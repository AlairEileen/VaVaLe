package space.alair.vavale.net_tools.requests;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import space.alair.vavale.models.ApiPayModel;
import space.alair.vavale.models.BalanceModel;
import space.alair.vavale.models.BaseModel;
import space.alair.vavale.models.PageContentModel;
import space.alair.vavale.models.PayModel;
import space.alair.vavale.models.PaymentModel;
import space.alair.vavale.wxapi.WeChatPayModel;

/**
 * Created by Alair on 3/26/2018.
 */

public interface BalanceAction {
    @GET("xcx/v1/account/read")
    Call<BalanceModel> getBalance();

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("xcx/v1/account")
    Call<BaseModel<WeChatPayModel>> getPayParams(@Body PayModel payModel);

    @GET("xcx/v1/account")
    Call<BaseModel<PageContentModel<PaymentModel>>> getPayList();
}

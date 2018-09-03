package space.alair.vavale.net_tools.requests;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import space.alair.vavale.models.BaseModel;
import space.alair.vavale.models.MsgContextModel;
import space.alair.vavale.models.MsgListContextModel;

/**
 * Created by Alair on 3/26/2018.
 */

public interface MsgAction {


    @GET("xcx/v1/sms/0")
    Call<BaseModel<MsgContextModel>> getMsg();

    @GET("xcx/v1/sms")
    Call<BaseModel<MsgListContextModel>> getMsgList(@Query("page") int pageIndex);
}


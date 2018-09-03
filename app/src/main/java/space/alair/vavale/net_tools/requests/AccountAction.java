package space.alair.vavale.net_tools.requests;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import space.alair.vavale.models.AccountModel;
import space.alair.vavale.net_tools.RequestUrl;

/**
 * Created by Alair on 3/23/2018.
 */

public interface AccountAction {
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST(RequestUrl.Account.ACTION_LOGIN)
    Call<AccountModel> getAccount(@Body AccountModel accountModel);

    @POST(RequestUrl.Account.ACTION_GET_TOKENS)
    Call<AccountModel> getToken(@Path(RequestUrl.Account.PATH_REFRESH) String token);
}

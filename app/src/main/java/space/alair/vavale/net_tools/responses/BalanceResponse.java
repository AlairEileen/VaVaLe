package space.alair.vavale.net_tools.responses;

import android.content.Context;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import space.alair.vavale.models.BalanceModel;
import space.alair.vavale.models.BaseModel;
import space.alair.vavale.models.DataExecutor;
import space.alair.vavale.models.PageContentModel;
import space.alair.vavale.models.PayModel;
import space.alair.vavale.models.PaymentModel;
import space.alair.vavale.net_tools.requests.BalanceAction;
import space.alair.vavale.wxapi.WeChatPayModel;

public class BalanceResponse extends BaseResponse<BalanceAction> {
    private static BalanceResponse projectResponse;

    public static BalanceResponse getBalanceRes() {
        if (projectResponse == null) projectResponse = new BalanceResponse();
        return projectResponse;
    }

    @Override
    protected RetrofitCreateType setRetrofitCreateType() {
        return RetrofitCreateType.hasHeader;
    }

    public void getBalance(Context context, ResponseListener<BalanceModel> balanceResListener) {
        getAction().getBalance().enqueue(doResponse(context,balanceResListener,()->{
            getBalance(context,balanceResListener);
        }));
    }

    public void getPayParams(Context context, PayModel payModel, ResponseListener<WeChatPayModel> balanceResListener) {
        getAction().getPayParams(payModel).enqueue(doResponse(context,balanceResListener,()->{
            getPayParams(context,payModel,balanceResListener);
        }));
    }

    public void getPayList(Context context, ResponseListener<PageContentModel<PaymentModel>> balanceResListener) {
        getAction().getPayList().enqueue(doResponse(context, balanceResListener, () -> {
            getPayList(context,balanceResListener);
        }));
    }



}

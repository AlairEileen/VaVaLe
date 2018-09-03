package space.alair.vavale.net_tools.responses;

import android.content.Context;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import space.alair.vavale.models.BaseModel;
import space.alair.vavale.models.DataExecutor;
import space.alair.vavale.models.MsgContextModel;
import space.alair.vavale.models.MsgListContextModel;
import space.alair.vavale.net_tools.requests.MsgAction;

public class MsgResponse extends BaseResponse<MsgAction> {
    private static MsgResponse projectResponse;

    public static MsgResponse getMsgRes() {
        if (projectResponse == null) projectResponse = new MsgResponse();
        return projectResponse;
    }

    @Override
    protected RetrofitCreateType setRetrofitCreateType() {
        return RetrofitCreateType.hasHeader;
    }

    public void getMsgList(Context context, int pageIndex, ResponseListener<MsgListContextModel> msgResListener) {
        getAction().getMsgList(pageIndex).enqueue(doResponse(context,msgResListener,()->{
            getMsgList(context,pageIndex,msgResListener);
        }));

    }

    public void getMsg(Context context, ResponseListener<MsgContextModel> msgResListener) {
        getAction().getMsg().enqueue(doResponse(context,msgResListener,()->{
            getMsg(context,msgResListener);
        }));
    }



}

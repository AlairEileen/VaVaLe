package space.alair.vavale.net_tools.responses;

import android.content.Context;
import android.support.annotation.Nullable;

import java.lang.reflect.ParameterizedType;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import space.alair.vavale.models.BaseModel;
import space.alair.vavale.models.DataExecutor;
import space.alair.vavale.net_tools.RetrofitBuilder;

public abstract class BaseResponse<T> {
    private Retrofit retrofitNoHeader;
    private Retrofit retrofit;
    private T action;


    public BaseResponse() {
        switch (setRetrofitCreateType()) {
            case both:
                retrofitNoHeader = RetrofitBuilder.buildNoHeader();
                retrofit = RetrofitBuilder.build();
                break;
            case noHeader:
                retrofitNoHeader = RetrofitBuilder.buildNoHeader();
                break;
            case hasHeader:
                retrofit = RetrofitBuilder.build();
                break;
            default:
                break;
        }
    }


    protected abstract RetrofitCreateType setRetrofitCreateType();

    protected T getNoHeaderAction() {

        if (action == null) {
            Class<T> tClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            action = retrofitNoHeader.create(tClass);
        }
        return action;
    }

    protected T getAction() {

        if (action == null) {
            Class<T> tClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            action = retrofit.create(tClass);
        }
        return action;
    }

    protected <D, P extends BaseModel<D>, M extends ResponseListener<D>> Callback<P> doResponse(Context context, M listener, @Nullable TokenListener tokenListener) {
        return new Callback<P>() {
            @Override
            public void onResponse(Call<P> call, Response<P> response) {
                P bm = response.body();
                if (bm == null) return;
                bm.getData(new DataExecutor<D>() {
                    @Override
                    public void success(D model) {
                        listener.success(model);
                    }

                    @Override
                    public void error(int errorCode) {
                        listener.netError();
                    }

                    @Override
                    public void onGetTokenSuccess() {
                        if (tokenListener != null)
                            tokenListener.onSuccessRefresh();
                    }
                }, context);
            }

            @Override
            public void onFailure(Call<P> call, Throwable t) {
                listener.netError();

            }
        };
    }

    protected <D, P extends BaseModel<D>, M extends ResponseListener<D>> Callback<P> doResponse(Context context, M listener, DataExecutor<D> dataExecutor) {
        return new Callback<P>() {
            @Override
            public void onResponse(Call<P> call, Response<P> response) {
                P bm = response.body();
                if (bm == null) return;
                bm.getData(dataExecutor, context);
            }

            @Override
            public void onFailure(Call<P> call, Throwable t) {
                listener.netError();

            }
        };
    }

    public enum RetrofitCreateType {
        hasHeader(0), noHeader(1), both(2);

        RetrofitCreateType(int value) {
        }
    }
}

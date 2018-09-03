package space.alair.vavale.net_tools;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import space.alair.vavale.app_datas.AppControl;

/**
 * Created by Alair on 3/23/2018.
 */

public class RetrofitBuilder {

    private static Retrofit retrofit;
    private static Retrofit retrofitNoHeader;

    public static Retrofit build() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(RequestUrl.DO_MAIN)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(genericClient())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit buildNoHeader() {
        if (retrofitNoHeader == null) {
            retrofitNoHeader = new Retrofit.Builder()
                    .baseUrl(RequestUrl.DO_MAIN)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitNoHeader;
    }

    private static OkHttpClient genericClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            String token=AppControl.control.getAccountSP().getAccountToken();
            Request request = original.newBuilder()
                    .header("token", token==null?"":token)
                    .method(original.method(), original.body()).build();
            return chain.proceed(request);
        }).build();
    }

}

package space.alair.vavale.models;

/**
 * Created by Alair on 3/23/2018.
 */

public interface DataExecutor<T> {
    void success(T model);

    void error(int errorCode);

    void onGetTokenSuccess();

}

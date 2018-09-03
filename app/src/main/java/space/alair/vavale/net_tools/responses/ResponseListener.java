package space.alair.vavale.net_tools.responses;

public interface ResponseListener<T> {
    void success(T model);
    void netError();
}

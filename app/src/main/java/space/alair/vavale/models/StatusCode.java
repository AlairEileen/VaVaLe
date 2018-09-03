package space.alair.vavale.models;

/**
 * Created by Alair on 3/23/2018.
 */

public interface StatusCode {

    int SUCCESS = 0;
    int ERROR = 2000;
    int TOKEN_TIME_OUT = 10410;
    int REFRESH_TOKEN_TIME_OUT = 21000;
    int NO_PHONE_NUMBER = 10411;
    int NUMBER_USED = 10412;

}

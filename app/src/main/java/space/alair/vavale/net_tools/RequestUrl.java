package space.alair.vavale.net_tools;

/**
 * Created by Alair on 3/23/2018.
 */

public interface RequestUrl {
    String DO_MAIN = "http://wwl.dev.360yingketong.com/";

    interface Account {
        String PATH_REFRESH = "refresh";

        String PARAM_REFRESH_TOKEN = "TOKEN";
        String PARAM_SIGN = "";
        String PARAM_ACCOUNT_NUMBER = "";
        String PARAM_ACCOUNT_PWD = "";
        String PARAM_TOKEN="token";

        String ACTION_LOGIN = "xcx/v1/login";
        String ACTION_GET_TOKENS = "xcx/v1/refresh/{"+PATH_REFRESH+"}";
    }

    interface Msg {

    }

    interface Project {

    }

    interface Test {
        String test = "index/index";
    }
}

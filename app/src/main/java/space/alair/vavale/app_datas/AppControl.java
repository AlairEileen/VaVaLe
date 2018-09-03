package space.alair.vavale.app_datas;

import android.app.Application;

/**
 * Created by Alair on 3/23/2018.
 */

public class AppControl extends Application {

    public static AppControl control;
    private SPHelper sp;
    private AccountSP accountSP;

    public AccountSP getAccountSP() {
        if (accountSP == null)
            accountSP = new AccountSP(sp);
        return accountSP;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        control = this;
        init();
    }

    private void init() {
        if (sp == null) {
            sp = new SPHelper(getApplicationContext(), getPackageName());
        }
    }


}


package space.alair.vavale.net_tools.responses;

import android.content.Context;

import space.alair.vavale.app_datas.AppControl;
import space.alair.vavale.models.AccountModel;
import space.alair.vavale.models.DataExecutor;
import space.alair.vavale.net_tools.requests.AccountAction;

public class AccountResponse extends BaseResponse<AccountAction> {
    private static AccountResponse accountResponse;

    public static AccountResponse getAccountRes() {
        if (accountResponse == null) accountResponse = new AccountResponse();
        return accountResponse;
    }

    @Override
    protected RetrofitCreateType setRetrofitCreateType() {
        return RetrofitCreateType.noHeader;
    }


    public boolean hasAccount() {
        return AppControl.control.getAccountSP().getAccountToken() != null;
    }


    public void doLogin(Context context, AccountModel account, ResponseListener<AccountModel> accountResListener) {
        getNoHeaderAction().getAccount(account).enqueue(doResponse(context, accountResListener, () -> {

        }));
        getNoHeaderAction().getAccount(account).enqueue(doResponse(context, new ResponseListener<AccountModel>() {
            @Override
            public void success(AccountModel model) {
                AppControl.control.getAccountSP().setAccountRefreshTokenName(model.getRefreshToken());
                AppControl.control.getAccountSP().setAccountToken(model.getToken());
                accountResListener.success(model);
            }

            @Override
            public void netError() {

            }
        }, () -> {

        }));
    }

    public <T> void getTokens(DataExecutor<T> dataExecutor, Context context) {

        getNoHeaderAction().getToken(AppControl.control.getAccountSP().getAccountRefreshToken()).enqueue(doResponse(context, new ResponseListener<AccountModel>() {
            @Override
            public void success(AccountModel model) {
                AppControl.control.getAccountSP().setAccountRefreshTokenName(model.getRefreshToken());
                AppControl.control.getAccountSP().setAccountToken(model.getToken());
                dataExecutor.onGetTokenSuccess();
            }

            @Override
            public void netError() {

            }
        }, () -> {

        }));
    }


}

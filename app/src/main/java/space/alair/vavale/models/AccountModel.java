package space.alair.vavale.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alair on 3/20/2018.
 */

public class AccountModel extends BaseModel<AccountModel> {

    @SerializedName("refresh_token")
    private String refreshToken;
    private String token;
    @SerializedName("account")
    private String accountName;
    @SerializedName("password")
    private String accountPwd;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountPwd() {
        return accountPwd;
    }

    public void setAccountPwd(String accountPwd) {
        this.accountPwd = accountPwd;
    }
}

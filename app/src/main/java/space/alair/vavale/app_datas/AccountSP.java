package space.alair.vavale.app_datas;

/**
 * Created by Alair on 3/23/2018.
 */

public class AccountSP {
    private static String token, refreshToken;
    private final SPHelper spHelper;
    private String accountTokenName = "accountTokenName";
    private String accountRefreshTokenName = "accountRefreshTokenName";

    public AccountSP(SPHelper spHelper) {
        this.spHelper = spHelper;
    }

    public String getAccountToken() {
        String spToken = spHelper.getString(accountTokenName);
        return spToken == null ? token : spToken;
    }

    public void setAccountToken(String accountToken) {
        token = accountToken;
        spHelper.putValues(new SPHelper.ContentValue(accountTokenName, accountToken));
    }

    public String getAccountRefreshToken() {
        String spRefreshToken = spHelper.getString(accountRefreshTokenName);

        return spRefreshToken == null ? refreshToken : spRefreshToken;
    }

    public void setAccountRefreshTokenName(String accountRefreshToken) {
        refreshToken = accountRefreshToken;
        spHelper.putValues(new SPHelper.ContentValue(accountRefreshTokenName, accountRefreshToken));
    }
}
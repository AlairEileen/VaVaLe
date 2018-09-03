package space.alair.vavale.net_tools.responses;

import android.content.Context;

import space.alair.vavale.models.DataExecutor;
import space.alair.vavale.models.PhoneNumberModel;
import space.alair.vavale.models.StatusCode;
import space.alair.vavale.net_tools.requests.PhoneAction;

public class PhoneResponse extends BaseResponse<PhoneAction> {
    private static PhoneResponse projectResponse;

    public static PhoneResponse getPhoneRes() {
        if (projectResponse == null) projectResponse = new PhoneResponse();
        return projectResponse;
    }

    @Override
    protected RetrofitCreateType setRetrofitCreateType() {
        return RetrofitCreateType.hasHeader;
    }

    public void getPrePhoneNumber(Context context, ResponseListener<PhoneNumberModel> phoneResListener) {
        getAction().getPrePhoneNumber().enqueue(doResponse(context, phoneResListener, () -> {
            getPrePhoneNumber(context, phoneResListener);
        }));
    }

    public void getPhoneNumber(Context context, String selectedProjectID, ResponseListener<PhoneNumberModel> phoneResListener) {
        getAction().getPhoneNumber(selectedProjectID).enqueue(doResponse(context, phoneResListener, () -> {
            getPhoneNumber(context, selectedProjectID, phoneResListener);
        }));
    }

    public void getPhoneNumber(Context context, String selectedProjectID, String number, PhoneResReGetListener<PhoneNumberModel> phoneResListener) {
        getAction().getPhoneNumber(selectedProjectID, number).enqueue(doResponse(context, phoneResListener, new DataExecutor<PhoneNumberModel>() {
            @Override
            public void success(PhoneNumberModel model) {
                phoneResListener.success(model);
            }

            @Override
            public void error(int errorCode) {
                switch (errorCode) {
                    case StatusCode.NUMBER_USED:
                    case StatusCode.NO_PHONE_NUMBER:
                        phoneResListener.noNumberUsed();
                        break;
                }
            }

            @Override
            public void onGetTokenSuccess() {
                getPhoneNumber(context, selectedProjectID, number, phoneResListener);
            }
        }));
    }


    public interface PhoneResReGetListener<T> extends ResponseListener<T> {
        void noNumberUsed();
    }
}

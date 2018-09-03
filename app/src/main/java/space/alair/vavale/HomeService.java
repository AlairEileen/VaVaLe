package space.alair.vavale;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.Timer;
import java.util.TimerTask;

import space.alair.vavale.models.MsgContextModel;
import space.alair.vavale.models.MsgModel;
import space.alair.vavale.models.PhoneNumberModel;
import space.alair.vavale.net_tools.responses.MsgResponse;
import space.alair.vavale.net_tools.responses.ResponseListener;

public class HomeService extends Service {
    public static HomeActivity homeActivity;
    public static HomeService homeService;
    public MsgModel msgModel;
    public boolean hasNumber;
    public PhoneNumberModel prePhone, currentPhone;
    public String selectedProjectID;
    private Timer timer;
    private MyBinder mBinder = new MyBinder();
    private Timer showNumberTimer;

    public HomeService() {
        homeService = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void getMsg() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            int times = 25;

            @Override
            public void run() {
                if (times == 0) {
                    onNumberChanged(false);
                    showNumberTimer.cancel();
                }
                MsgResponse.getMsgRes().getMsg(HomeService.this, new ResponseListener<MsgContextModel>() {
                    @Override
                    public void success(MsgContextModel model) {
                        msgModel = model.getRet();
                        if (homeActivity != null)
                            homeActivity.refreshMsg(model.getRet());
                        if (model.getRet() != null) {
                            onNumberChanged(false);
                            showNumberTimer.cancel();
                        }
//                        if (model.getRet() != null)
//                            Log.d("#########", new Gson().toJson(model));
                        times--;
                    }

                    @Override
                    public void netError() {
                        times--;

                    }
                });

            }
        }, 5000, 5000);

    }

    public void stopNumberTimer() {
        if (showNumberTimer != null)
            showNumberTimer.cancel();
    }

    public void onNumberChanged(boolean hasNumber) {
        this.hasNumber = hasNumber;
        homeActivity.showNumber();
        if (hasNumber) {
            showNumberTimer = new Timer();
            showNumberTimer.schedule(new TimerTask() {
                int lastS = 0;
                int lastM = 5;

                @Override
                public void run() {
                    int s = lastS > 0 ? lastS - 1 : 59;

                    int m = lastS == 0 ? lastM - 1 : lastM;
                    lastM = m;
                    lastS = s;
                    homeActivity.setNumberViewData(m + ":" + (s > 9 ? s : "0" + s) + "后自动释放");
                    if (s == 0 && m == 0) {
                        HomeService.this.hasNumber = false;
                        homeActivity.showNumber();
                        this.cancel();
                    }
                }
            }, 1000, 1000);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
    }

    public class MyBinder extends Binder {
        HomeService getService() {
            return HomeService.this;
        }
    }
}

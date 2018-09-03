package space.alair.pay_common.asyncTask;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import space.alair.pay_common.entity.OrderPay;
import space.alair.pay_common.service.IPayLogic;


public class UPPayTask extends AsyncTask<Object, Integer, String> {
	private Activity mContext;
	public UPPayTask(Activity context) {
		this.mContext = context;
	}
	
	@Override
	protected String doInBackground(Object... params) {
		try{
			return  IPayLogic.getInstance(mContext).getUPPayOrderInfo((OrderPay)params[0]);
		}catch (Exception e){
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(String result) {
		try {
			if (result!=null) {
				System.out.println("UPPay result>"+result);
				JSONObject data = new JSONObject(result);
				String message = data.getString("message");
				int code = data.getInt("code");

				if(code == 0){
					String orderInfo = data.getString("data");
                    System.out.println("获取到的tn>>>"+orderInfo);
                    Toast.makeText(mContext, "正在调起支付", Toast.LENGTH_SHORT).show();
					IPayLogic.getInstance(mContext).startUPPay(orderInfo);
				}else{

		        	Log.d("PAY_GET", "返回错误"+message);
//		        	Toast.makeText(mContext, "返回错误:"+message, Toast.LENGTH_SHORT).show();
				}
			}else {
				System.out.println("get  UPPay exception, is null");
			}
		} catch (Exception e) {
			Log.e("PAY_GET", "异常："+e.getMessage());
        	Toast.makeText(mContext, "异常："+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
		super.onPostExecute(result);
	}

}

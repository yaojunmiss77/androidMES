package tool;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

	/**
	 * 显示信息
	 * 
	 * @param context
	 * @param message
	 */
	public static void showMessage(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

}

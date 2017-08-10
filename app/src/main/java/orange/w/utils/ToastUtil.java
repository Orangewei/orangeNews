package orange.w.utils;

import android.content.Context;
import android.widget.Toast;

import orange.w.common.APP;


/**
 * Created by Administrator on 2015/12/5.
 吐司工具类
 */
public class ToastUtil {
    private static Toast toast;//单例的吐司
    public static void showToast(String text, Context context){
        if (toast==null){
            toast = Toast.makeText(context,text, Toast.LENGTH_SHORT);
        }else {
            toast.setText(text);//将文本设置给吐司
        }
        toast.show();
    }
    public static void showToast(String text){
        if (toast==null){
            toast = Toast.makeText(APP.appContext,text, Toast.LENGTH_SHORT);
        }else {
            toast.setText(text);//将文本设置给吐司
        }
        toast.show();
    }
    public static void toast(String text){
        showToast(text);
    }

}

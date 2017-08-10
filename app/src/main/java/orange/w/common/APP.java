package orange.w.common;

import android.app.Application;
import android.content.Context;

/**
 * Created by zqw on 2017/6/30.
 */

public class APP extends Application {
    public static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
    }
}

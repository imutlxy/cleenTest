package cleen.com.lxy.cleentest.util;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by lxy on 16-12-9.
 */
public class CleenApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        Fresco.initialize(context);
    }

}

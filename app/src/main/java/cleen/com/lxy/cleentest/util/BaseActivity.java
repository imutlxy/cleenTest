package cleen.com.lxy.cleentest.util;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import cleen.com.lxy.cleentest.R;
import cleen.com.lxy.cleentest.util.http.CleenHttpClient;

/**
 * Created by lxy on 16-12-9.
 */
public class BaseActivity extends ActionBarActivity {

    private boolean isAlive = false;

    public Context context;
    public BaseActivity mActivity;

    public TextView actionbar_title;

    public ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        isAlive = true;
        context = getApplicationContext();

        setActionBar();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

        isAlive = true;
        mActivity = this;
        context = getApplicationContext();
    }

    @Override
    protected void onStop() {
        isAlive = false;
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isAlive = false;
        CleenHttpClient.cancelActivityRequest(mActivity);
    }

    public void setActionBar() {
        actionBar = getSupportActionBar();

        actionBar.setDisplayShowCustomEnabled(true);//使自定义actionbar在title栏显示
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowTitleEnabled(false); //对应 DISPLAY_SHOW_TITLE
        actionBar.setShowHideAnimationEnabled(false); //actionbar设置为隐藏时，有一个动画，false：不播放动画。

        actionBar.setCustomView(R.layout.actionbar);//设置actionbar的layout

//       actionBar.setDisplayHomeAsUpEnabled(true); // 给左上角图标的左边加上一个返回的图标 。对应ActionBar.DISPLAY_HOME_AS_UP。默认为false。效果图见下方。
//       actionBar.setDisplayShowHomeEnabled(true); //使左上角图标可点击，对应id为android.R.id.home，对应ActionBar.同样，默认为false。DISPLAY_SHOW_HOME

        //使ActionBar横向充满屏幕
        Toolbar parent = (Toolbar) actionBar.getCustomView().getParent();
        parent.setContentInsetsAbsolute(0, 0);

        actionbar_title = (TextView) findViewById(R.id.actionbar_title);

    }

    public void setActionbarTitle(String title) {
        actionbar_title.setVisibility(View.VISIBLE);
        actionbar_title.setText(title);
    }

    public void setActionbarTitle(int titleId) {
        actionbar_title.setVisibility(View.VISIBLE);
        actionbar_title.setText(getResources().getString(titleId));
    }

    public <T> T decodeJson(Class<T> mClass, String response) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(response, mClass);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void mLog(String text) {
        CleenLog.d(text);
    }



    public boolean getIsAlive() {
        return isAlive;
    }

}

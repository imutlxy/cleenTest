package cleen.com.lxy.cleentest.util.http;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cleen.com.lxy.cleentest.R;
import cleen.com.lxy.cleentest.util.BaseActivity;
import cleen.com.lxy.cleentest.util.CleenLog;
import cleen.com.lxy.cleentest.util.Constants;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.protocol.HTTP;

/**
 * Created by lxy on 16-12-9.
 */
public class CleenHttpClient {

    private static AsyncHttpClient mClient = new AsyncHttpClient();


    public static void cancelAllRequest(Context context) {
        mClient.cancelRequests(context, true);
    }

    public static void cancelActivityRequest(Activity mActivity) {
        mClient.cancelRequests(mActivity, true);
    }


    // 发起get请求
    public static void get(final BaseActivity activity, String url, final RequestParams params,
                           final CleenHttpHandler cleenHttpHandler) {
        final Context context = activity.getApplicationContext();

        mClient.get(activity, url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (!activity.getIsAlive()) {
                    CleenLog.d("!activity.getIsAlive()");
                    return;
                }
                handlerResponseStatus(responseBody, cleenHttpHandler);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (!activity.getIsAlive()) {
                    return;
                }

                if (responseBody != null && responseBody.length > 0) {
                    String failRes = new String(responseBody);
                    CleenLog.d("failRes=" + failRes);
                }

                cleenHttpHandler.onFailure(Constants.HTTP_ERROR);
                Toast.makeText(context, R.string.http_network_error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void post(final BaseActivity activity, String url, final String json,
                            final CleenHttpHandler cleenHttpHandler) {
        final Context context = activity.getApplicationContext();

        StringEntity entity = null;
        entity = new StringEntity(json, HTTP.UTF_8);
        if (entity == null) {
            cleenHttpHandler.onFailure(Constants.HTTP_ERROR);
            Toast.makeText(context, R.string.http_network_error, Toast.LENGTH_SHORT).show();
            return;
        }

        mClient.post(activity, url, entity, "application/x-www-form-urlencoded",
                new AsyncHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, final byte[] responseBody) {
                        if (!activity.getIsAlive()) {
                            return;
                        }
                        handlerResponseStatus(responseBody, cleenHttpHandler);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        CleenLog.d("http onFailure : ");
                        if (!activity.getIsAlive()) {
                            return;
                        }
                    }
                });
    }

    private static void handlerResponseStatus(final byte[] responseBody, final CleenHttpHandler cleenHttpHandler) {
        // 将responseBody解析成字符串
        String responseString;
        try {
            responseString = new String(responseBody);
            cleenHttpHandler.onSuccess(responseString);
            return;
        } catch (Exception e) {
            e.printStackTrace();
            cleenHttpHandler.onFailure(Constants.HTTP_ERROR);
            return;
        }
    }


}

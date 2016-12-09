package cleen.com.lxy.cleentest.util.http;

/**
 * Created by lxy on 16-12-9.
 */
public interface CleenHttpHandler {
    void onSuccess(String responseString);
    void onFailure(int response_status);
}

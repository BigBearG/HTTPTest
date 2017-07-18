package textitei.httptest;

/**
 * Created by liuwei on 17-7-18.
 */

public interface HttpCallbackListenner {
    void onFinnsh(String response);
    void onError(Exception e);
}

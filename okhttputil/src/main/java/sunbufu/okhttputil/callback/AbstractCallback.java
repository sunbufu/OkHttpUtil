package sunbufu.okhttputil.callback;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

/**
 * 基础回调
 * @author sunbufu
 * @param <T>   返回参数的类型
 */
public abstract class AbstractCallback<T> {

    /**
     * 请求返回
     * @param response
     * @throws IOException
     */
    public void onResponse(Response response) throws IOException {
        onSuccess(convertResponse(response));
        onAfter();
    }

    /**
     * 请求出错
     * @param request
     * @param e
     */
    public void onFailure(Request request, Exception e) {
        onError(request, e);
        onAfter();
    }

    /**
     * 将收到的请求转换成T
     * @param response
     * @return
     * @throws IOException
     */
    public abstract T convertResponse(Response response) throws IOException;

    /**
     * 请求出错
     * @param request
     * @param e
     */
    public void onError(Request request, Exception e) {
        e.printStackTrace();
    }

    /**
     * 请求成功
     * @param result
     */
    public abstract void onSuccess(T result);

    /**
     * 请求完成
     */
    public void onAfter() {
    }
}

package sunbufu.okhttputil.callback;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;
import sunbufu.okhttputil.OkHttpUtil;

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
    public void onResponse(final Response response) {
        OkHttpUtil.getInstance().getHandler().post(new Runnable() {
            @Override
            public void run() {
                try {
                    onSuccess(convertResponse(response));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
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
     * 请求成功(在UI线程执行)
     * @param result
     */
    public abstract void onSuccess(T result);

    /**
     * 请求完成
     */
    public void onAfter() {
    }
}

package sunbufu.okhttputil;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import sunbufu.okhttputil.callback.AbstractCallback;
import sunbufu.okhttputil.cookie.CookieStore;
import sunbufu.okhttputil.request.GetRequest;
import sunbufu.okhttputil.request.PostRequest;
import sunbufu.okhttputil.util.HttpUtil;
import sunbufu.okhttputil.util.LogUtil;

public class OkHttpUtil {

    private static OkHttpUtil instance;
    private OkHttpClient okHttpClient;
    private Handler handler;

    private OkHttpUtil() {
        okHttpClient = new OkHttpClient.Builder().cookieJar(new CookieStore()).build();
        handler = new Handler(Looper.getMainLooper());
    }

    public static OkHttpUtil getInstance() {
        if (instance == null)
            synchronized (OkHttpUtil.class) {
                if (instance == null)
                    instance = new OkHttpUtil();
            }
        return instance;
    }

    /**
     *
     * @param url
     * @return
     */
    public static GetRequest get(String url) {
        return new GetRequest(url);
    }

    /**
     *
     * @param url
     * @return
     */
    public static PostRequest post(String url) {
        return new PostRequest(url);
    }

    /**
     * 执行Post请求
     * @param url
     * @param params
     * @param callback
     */
    public void executePostRequest(String url, List<Param> params, AbstractCallback callback) {
        if (TextUtils.isEmpty(url)) {
            LogUtil.e("url 不能为空");
            return;
        }
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null && params.size() > 0)
            for (Param param : params)
                builder.add(param.key, param.value);
        RequestBody body = builder.build();
        Request request = new Request.Builder().url(url).post(body).build();
        enqueueRequest(callback, request);
    }

    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    /**
     * 执行Post请求,上传文件
     * @param url
     * @param params
     * @param callback
     */
    public void executePostRequest(String url, List<Param> params, Map<String, File> fileParams, AbstractCallback callback) {
        if (TextUtils.isEmpty(url)) {
            LogUtil.e("url 不能为空");
            return;
        }
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        if (params != null && params.size() > 0)
            for (Param param : params)
                builder.addFormDataPart(param.key, param.value);
        if (fileParams != null && fileParams.size() > 0)
            for (String key : fileParams.keySet()) {
                builder.addFormDataPart(key, fileParams.get(key).getName(), RequestBody.create(MEDIA_TYPE_PNG, fileParams.get(key)));
            }
        RequestBody body = builder.build();
        Request request = new Request.Builder().url(url).post(body).build();
        enqueueRequest(callback, request);
    }

    /**
     * 执行Get请求
     * @param url
     * @param params
     * @param callback
     */
    public void executeGetRequest(String url, List<Param> params, AbstractCallback callback) {
        if (TextUtils.isEmpty(url)) {
            LogUtil.e("url 不能为空");
            return;
        }
        Request.Builder builder = new Request.Builder().url(HttpUtil.generateUrlFromParams(url, params));
        Request request = builder.build();
        enqueueRequest(callback, request);
    }

    /**
     * 执行请求
     * @param callback
     * @param request
     */
    private void enqueueRequest(final AbstractCallback callback, final Request request) {
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callback != null)
                    callback.onFailure(request, e);
            }

            @Override
            public void onResponse(Call call, Response response) {
                if (callback != null) {
                    try {
//                    callback.onResponse(response);
                        final Object o = callback.convertResponse(response);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onSuccess(o);
                            }
                        });
                        callback.onAfter();
                    } catch (IOException e) {
                        callback.onFailure(request, e);
                    }
                }
            }
        });
    }

    public Handler getHandler() {
        return handler;
    }
}

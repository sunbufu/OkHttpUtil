package sunbufu.okhttputil.request;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;
import sunbufu.okhttputil.OkHttpUtil;
import sunbufu.okhttputil.callback.AbstractCallback;
import sunbufu.okhttputil.convertor.AbstractConvertor;

/**
 * POST请求
 * @author sunbufu
 */
public class PostRequest extends BaseRequest {

    /**参数(文件)*/
    private Map<String, File> fileParams;

    public PostRequest(String url) {
        super(url);
    }

    public PostRequest param(String key, File value) {
        if (fileParams == null)
            fileParams = new HashMap<>();
        fileParams.put(key, value);
        return this;
    }

    @Override
    public void execute(AbstractCallback callback) {
        if (fileParams == null) {
            OkHttpUtil.getInstance().enqueuePostRequest(url, params, callback);
        } else {
            OkHttpUtil.getInstance().enqueuePostRequest(url, params, fileParams, callback);
        }
    }

    @Override
    public void execute() {
        if (fileParams == null) {
            OkHttpUtil.getInstance().enqueuePostRequest(url, params, null);
        } else {
            OkHttpUtil.getInstance().enqueuePostRequest(url, params, fileParams, null);
        }
    }

    @Override
    public <E> E executeSync(AbstractConvertor<E> convertor) {
        Response response = OkHttpUtil.getInstance().excutePostRequest(url, params);
        return (E) convertor.convert(response);
    }

}

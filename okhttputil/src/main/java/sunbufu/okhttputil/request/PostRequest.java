package sunbufu.okhttputil.request;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import sunbufu.okhttputil.OkHttpUtil;
import sunbufu.okhttputil.Param;
import sunbufu.okhttputil.callback.AbstractCallback;

/**
 * POST请求
 * @author sunbufu
 */
public class PostRequest {

    private String url;
    private List<Param> params;
    private Map<String, File> fileParams;

    public PostRequest(String url) {
        this.url = url;
    }

    public PostRequest param(String key, String value) {
        if (params == null)
            params = new LinkedList<>();
        params.add(new Param(key, value));
        return this;
    }

    public PostRequest param(String key, File value) {
        if (fileParams == null)
            fileParams = new HashMap<>();
        fileParams.put(key, value);
        return this;
    }

    public void execute(AbstractCallback callback) {
        if (fileParams == null) {
            OkHttpUtil.getInstance().executePostRequest(getUrl(), getParams(), callback);
        } else {
            OkHttpUtil.getInstance().executePostRequest(getUrl(), getParams(), fileParams, callback);
        }
    }

    public void execute() {
        if (fileParams == null) {
            OkHttpUtil.getInstance().executePostRequest(getUrl(), getParams(), null);
        } else {
            OkHttpUtil.getInstance().executePostRequest(getUrl(), getParams(), fileParams, null);
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Param> getParams() {
        return params;
    }

    public void setParams(List<Param> params) {
        this.params = params;
    }
}

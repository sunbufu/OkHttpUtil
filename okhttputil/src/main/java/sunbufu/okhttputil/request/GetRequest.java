package sunbufu.okhttputil.request;

import java.util.LinkedList;
import java.util.List;

import sunbufu.okhttputil.OkHttpUtil;
import sunbufu.okhttputil.Param;
import sunbufu.okhttputil.callback.AbstractCallback;

/**
 * GET请求
 * @author sunbufu
 */
public class GetRequest {

    private String url;
    private List<Param> params;

    public GetRequest(String url) {
        this.url = url;
    }

    public GetRequest param(String key, String value) {
        if (params == null)
            params = new LinkedList<>();
        params.add(new Param(key, value));
        return this;
    }

    public void execute(AbstractCallback callback) {
        OkHttpUtil.getInstance().executeGetRequest(getUrl(), getParams(), callback);
    }

    public void execute() {
        OkHttpUtil.getInstance().executeGetRequest(getUrl(), getParams(), null);
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

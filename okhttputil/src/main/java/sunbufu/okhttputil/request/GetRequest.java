package sunbufu.okhttputil.request;

import okhttp3.Response;
import sunbufu.okhttputil.OkHttpUtil;
import sunbufu.okhttputil.callback.AbstractCallback;
import sunbufu.okhttputil.convertor.AbstractConvertor;

/**
 * GET请求
 * @author sunbufu
 */
public class GetRequest extends BaseRequest {

    public GetRequest(String url) {
        super(url);
    }

    @Override
    public void execute(AbstractCallback callback) {
        OkHttpUtil.getInstance().enqueueGetRequest(url, params, callback);
    }

    @Override
    public void execute() {
        OkHttpUtil.getInstance().enqueueGetRequest(url, params, null);
    }

    @Override
    public <E> E executeSync(AbstractConvertor<E> convertor) {
        Response response = OkHttpUtil.getInstance().excuteGetRequest(url, params);
        return (E) convertor.convert(response);
    }

}

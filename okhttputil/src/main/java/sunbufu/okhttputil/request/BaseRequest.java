package sunbufu.okhttputil.request;

import java.util.LinkedList;
import java.util.List;

import sunbufu.okhttputil.Param;
import sunbufu.okhttputil.callback.AbstractCallback;
import sunbufu.okhttputil.convertor.AbstractConvertor;

/**
 * 基础请求
 * @author sunbufu
 */
public abstract class BaseRequest {

    protected String url;
    /**参数*/
    protected List<Param> params;

    public BaseRequest(String url) {
        this.url = url;
    }

    public BaseRequest param(String key, String value) {
        if (params == null)
            params = new LinkedList<>();
        params.add(new Param(key, value));
        return this;
    }

    /**
     * 异步执行
     * @param callback
     */
    public abstract void execute(AbstractCallback callback);

    /**
     * 异步执行
     */
    public abstract void execute();

    /**
     * 同步执行
     */
    public abstract <E> E executeSync(AbstractConvertor<E> convertor);

}

package sunbufu.okhttputil.convertor;

import okhttp3.Response;

/**
 * 转换接口
 */
public interface AbstractConvertor<T> {
    /**
     * 转换方法
     * @param response
     * @return
     */
    public T convert(Response response);
}

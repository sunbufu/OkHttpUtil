package sunbufu.okhttputil.convertor;

import java.io.IOException;

import okhttp3.Response;

/**
 * 字符串转换器
 */
public class StringConvertor implements AbstractConvertor<String> {

    @Override
    public String convert(Response response) {
        String result = null;
        if (response != null) {
            try {
                result = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}

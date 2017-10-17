package sunbufu.okhttputil.callback;

import java.io.IOException;

import okhttp3.Response;
import sunbufu.okhttputil.convertor.StringConvertor;

public abstract class StringCallback extends AbstractCallback<String> {
    private StringConvertor convertor;

    public StringCallback() {
        convertor = new StringConvertor();
    }

    @Override
    public String convertResponse(Response response) throws IOException {
        return convertor.convert(response);
    }
}

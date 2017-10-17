package sunbufu.okhttputil.callback;

import java.io.IOException;

import okhttp3.Response;

public abstract class StringCallback extends AbstractCallback<String> {
    @Override
    public String convertResponse(Response response) throws IOException {
        return response.body().string();
    }
}

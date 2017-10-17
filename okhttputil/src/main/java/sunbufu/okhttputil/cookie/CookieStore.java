package sunbufu.okhttputil.cookie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Cookie管理
 * @author sunbufu
 */
public class CookieStore implements CookieJar {
    Map<String, List> cookieStore = new HashMap<>();

    @Override
    public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
        cookieStore.put(httpUrl.host(), list);
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl httpUrl) {
        List<Cookie> cookies = cookieStore.get(httpUrl.host());
        return cookies != null ? cookies : new ArrayList<Cookie>();
    }
}

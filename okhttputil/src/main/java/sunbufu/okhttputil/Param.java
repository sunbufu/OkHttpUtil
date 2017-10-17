package sunbufu.okhttputil;

import java.net.URLEncoder;

/**
 * 参数
 * @author sunbufu
 */
public class Param {
    String key, value;

    public Param() {
    }

    public Param(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String toUrlParam() {
        try {
            return new StringBuilder(key)
                    .append('=')
                    .append(URLEncoder.encode(value, "UTF-8"))
                    .toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

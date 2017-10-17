package sunbufu.okhttputil.util;

import android.text.TextUtils;

import java.util.List;

import okhttp3.Response;
import sunbufu.okhttputil.Param;

public class HttpUtil {

    /** 将传递进来的参数拼接成 url */
    public static String generateUrlFromParams(String url, List<Param> params) {
        if (params == null || params.size() <= 0)
            return url;
        StringBuilder stringBuilder = new StringBuilder(url);
        if (url.indexOf('&') > 0 || url.indexOf('?') > 0)
            stringBuilder.append('&');
        else
            stringBuilder.append('?');
        for (Param param : params) {
            stringBuilder.append(param.toUrlParam()).append('&');
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    /** 根据响应头或者url获取文件名 */
    public static String getNetFileName(Response response, String url) {
        String fileName = getHeaderFileName(response);
        if (TextUtils.isEmpty(fileName)) fileName = getUrlFileName(url);
        if (TextUtils.isEmpty(fileName)) fileName = "nofilename";
        return fileName;
    }

    /** 解析文件头 Content-Disposition:attachment;filename=FileName.txt */
    private static String getHeaderFileName(Response response) {
        String dispositionHeader = response.header("Content-Disposition");
        if (dispositionHeader != null) {
            String split = "filename=";
            int indexOf = dispositionHeader.indexOf(split);
            if (indexOf != -1) {
                String fileName = dispositionHeader.substring(indexOf + split.length(), dispositionHeader.length());
                fileName = fileName.replaceAll("\"", "");   //文件名可能包含双引号,需要去除
                return fileName;
            }
        }
        return null;
    }

    /** 通过 ‘？’ 和 ‘/’ 判断文件名 */
    private static String getUrlFileName(String url) {
        int index = url.lastIndexOf('?');
        String filename;
        if (index > 1) {
            filename = url.substring(url.lastIndexOf('/') + 1, index);
        } else {
            filename = url.substring(url.lastIndexOf('/') + 1);
        }
        return filename;
    }

}

package sunbufu.okhttputil.util;

import android.text.TextUtils;
import android.util.Log;

/**
 * 日志工具类
 */
public class LogUtil {

    public static String tagPrefix = "sunbufuLog";//log前缀
    /**true则输出日志，false屏蔽日志*/
    public static boolean isOn = true;

    public static void d(Object o) {
        logger("d", o);
    }

    public static void e(Object o) {
        logger("e", o);
    }

    public static void i(Object o) {
        logger("i", o);
    }

    public static void w(Object o) {
        logger("w", o);
    }

    /**
     *
     * @param type logger级别
     * @param o   logger内容

     */
    private static void logger(String type, Object o) {
        if (!isOn) {
            return;
        }
        String msg = o + "";
        String tag = getTag(getCallerStackTraceElement());
        switch (type) {
            case "i":
                Log.i(tag, msg);
                break;
            case "d":
                Log.d(tag, msg);
                break;
            case "e":
                Log.e(tag, msg);
                break;
            case "w":
                Log.w(tag, msg);
                break;
        }
    }


    /**
     * 获取TAG
     * @param element
     * @return
     */
    private static String getTag(StackTraceElement element) {
        String tag = "%s.%s(Line:%d)"; // 占位符
        String callerClazzName = element.getClassName(); // 获取到类名

        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, element.getMethodName(), element.getLineNumber()); // 替换
        tag = TextUtils.isEmpty(tagPrefix) ? tag : tagPrefix + ":" + tag;
        return tag;
    }

    /**
     * 获取线程状态
     * @return
     */
    private static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[5];
    }
}

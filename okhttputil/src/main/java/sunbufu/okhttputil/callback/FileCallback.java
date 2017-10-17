package sunbufu.okhttputil.callback;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Request;
import okhttp3.Response;
import sunbufu.okhttputil.util.HttpUtil;
import sunbufu.okhttputil.OkHttpUtil;

public abstract class FileCallback extends AbstractCallback<File> {

    /**回调的刷新时间*/
    public static final int REFRESH_TIME = 100;

    public String filePath;

    public String genResponseFilePath(Response response) {
        StringBuilder stringBuilder = new StringBuilder()
                .append(Environment.getExternalStorageDirectory())
                .append(File.separator)
                .append("download")
                .append(File.separator)
                .append(HttpUtil.getNetFileName(response, response.request().url().toString()));
        return stringBuilder.toString();
    }

    @Override
    public File convertResponse(Response response) throws IOException {
        if (TextUtils.isEmpty(filePath))
            filePath = genResponseFilePath(response);

        InputStream inputStream = response.body().byteStream();
        FileOutputStream fileOutputStream = null;
        File file = new File(filePath);
        try {
            fileOutputStream = new FileOutputStream(file);
            byte[] buffer = new byte[2048];

            long lastRefreshTime = 0;//上次刷新的时间
            long lastWriteBytes = 0;//上次写入的字节数

            final long total = response.body().contentLength();
            long sum = 0;
            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                sum += len;
                //写入文件
                fileOutputStream.write(buffer, 0, len);

                //更新下载进度
                final long finalSum = sum;
                long curTime = System.currentTimeMillis();
                if (curTime - lastRefreshTime >= REFRESH_TIME || finalSum >= total) {
                    //计算下载速度
                    long diffTime = (curTime - lastRefreshTime) / 1000;
                    if (diffTime == 0) diffTime += 1;
                    long diffBytes = finalSum - lastWriteBytes;
                    final long networkSpeed = diffBytes / diffTime;
                    OkHttpUtil.getInstance().getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            onProgress(finalSum, total, finalSum * 1.0f / total, networkSpeed);   //进度回调的方法
                        }
                    });

                    lastRefreshTime = System.currentTimeMillis();
                    lastWriteBytes = finalSum;
                }
            }
            fileOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (fileOutputStream != null)
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return file;
    }

    @Override
    public void onError(Request request, Exception e) {

    }

    /**
     * 下载进度
     * @param currentSize       当前下载字节数
     * @param totalSize         总共字节数
     * @param progress          进度
     * @param networkSpeed      下载速度
     */
    public abstract void onProgress(long currentSize, long totalSize, float progress, long networkSpeed);

    @Override
    public abstract void onSuccess(File result);
}

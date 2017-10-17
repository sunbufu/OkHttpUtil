package sunbufu.okhttputil.callback;

import java.io.File;
import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;
import sunbufu.okhttputil.convertor.FileConvertor;

public abstract class FileCallback extends AbstractCallback<File> {

    private FileConvertor convertor;

    public FileCallback() {
        convertor = new FileConvertor(new FileConvertor.FileConvertorListener() {
            @Override
            public void onProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                FileCallback.this.onProgress(currentSize, totalSize, progress, networkSpeed);
            }
        });
    }

    @Override
    public File convertResponse(Response response) throws IOException {
        return convertor.convert(response);
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

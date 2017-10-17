package sunbufu.okhttptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;
import sunbufu.okhttputil.OkHttpUtil;
import sunbufu.okhttputil.callback.FileCallback;
import sunbufu.okhttputil.callback.StringCallback;
import sunbufu.okhttputil.util.LogUtil;

public class MainActivity extends AppCompatActivity {

    public static final String GAME_ADDRESS = "http://192.168.1.203:8080/YunTorchTexasHoldem/";
    public static final String LOGIN_PASSWORD = GAME_ADDRESS + "customer/authenticateWithPassWord";
    public static final String REFRESH_WALLET = GAME_ADDRESS + "customer/refreshWallet";
    public static final String TEST_UP_LOADE = GAME_ADDRESS + "customer/testUpLoad";

    public static final String AVATAR_URL = "https://www.shuishang.net/data/head/user/yh_1746053066.jpg";
    public static final String APK_URL = "https://www.shuishang.net/data/version/1.0.21.apk";

    public static final String VERSION_LAST = "https://www.shuishang.net/YunTorchSite/" + "version/versionNearby";

    @BindView(R.id.resultText)
    public TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.getBtn)
    public void get() {
        OkHttpUtil.get(VERSION_LAST)
                .param("device", "Android")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String result) {
                        LogUtil.e(result);
                        resultText.setText(result);
                    }
                });
    }

    @OnClick(R.id.postBtn)
    public void post() {
        OkHttpUtil.post(LOGIN_PASSWORD)
                .param("userName", "17758248536")
                .param("passWord", "*****************")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String result) {
                        LogUtil.e(result);
                        resultText.setText(result);
                    }

                    @Override
                    public void onError(Request request, Exception e) {
                        LogUtil.e("请求失败");
                        e.printStackTrace();
                    }
                });
    }

    @OnClick(R.id.checkBtn)
    public void check() {
        OkHttpUtil.post(REFRESH_WALLET)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String result) {
                        LogUtil.e(result);
                        resultText.setText(result);
                    }
                });
    }

    @OnClick(R.id.upLoadBtn)
    public void upLoad() {
        OkHttpUtil.post(TEST_UP_LOADE)
                .param("file", new File("/sdcard/screenshot.jpg"))
                .param("test", "123321")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String result) {
                        LogUtil.e(result);
                        resultText.setText(result);
                    }
                });
    }

    @OnClick(R.id.downLoadBtn)
    public void downLoad() {
        OkHttpUtil.get(APK_URL)
                .execute(new FileCallback() {
                    @Override
                    public void onProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        LogUtil.e("下载进度：" + currentSize + ", " + totalSize + ", " + progress + ", " + networkSpeed);
                        resultText.setText("下载进度：" + currentSize + ", " + totalSize + ", " + progress + ", " + networkSpeed);
                    }

                    @Override
                    public void onSuccess(File result) {
                        LogUtil.e("下载完成" + result.getName());
                        resultText.setText("下载完成" + result.getPath());
                    }
                });
    }
}

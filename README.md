[![](https://jitpack.io/v/sunbufu/OkHttpUtil.svg)](https://jitpack.io/#sunbufu/OkHttpUtil)
# OkHttpUtil
一个OkHttp的工具类库

默认使用OkHttp3.9.0


Add it in your root build.gradle at the end of repositories:

```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

Add the dependency

```
	dependencies {
	        compile 'com.github.sunbufu:OkHttpUtil:1.0.3'
	}
```
#### 1.异步操作

```
//GET
OkHttpUtil.get(url)
                .param("key", "value")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String result) {
                        LogUtil.e(result);
                    }
                });
//POST
OkHttpUtil.post(url)
                .param("key", "value")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String result) {
                        LogUtil.e(result);
                    }
                });
//UPLOAD
OkHttpUtil.post(url)
                .param("file", new File("/sdcard/screenshot.jpg"))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String result) {
                        LogUtil.e(result);
                    }
                });
//DOWNLOAD
OkHttpUtil.get(url)
                .execute(new FileCallback() {
                    @Override
                    public void onProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        LogUtil.e("当前下载字节数：" + currentSize + ", 总字节数" + totalSize + ", 当前进度" + progress + ", 下载速度" + networkSpeed);
                    }

                    @Override
                    public void onSuccess(File result) {
                        LogUtil.e("下载完成" + result.getName());
                    }
                });
```

#### 2.同步操作

```
//GET
String result = OkHttpUtil.get(url)
                        .param("key", "value")
                        .executeSync(new StringConvertor());
//POST
String result = OkHttpUtil.post(LOGIN_PASSWORD)
                        .param("key", "value")
                        .executeSync(new StringConvertor());
```


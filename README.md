Android开发工具 [![](https://jitpack.io/v/natural1law/briefness.svg)](https://jitpack.io/#natural1law/briefness)
======

### 工具目录
   #### 图表统计
   * [图表统计](https://github.com/natural1law/briefness/blob/master/app/src/main/java/com/androidx/briefness/homepage/activity/EchartsActivity.java "点击查看使用`图表统计`功能代码")
   #### 网络请求
   * [网络请求（WebSocket长连接、http/https）](https://github.com/natural1law/briefness/blob/master/app/src/main/java/com/androidx/briefness/homepage/activity/NetworkRequestActivity.java "点击查看使用`网络请求`功能代码")
   #### 常用工具
   * [正则表达式](https://github.com/natural1law/briefness/ "点击查看使用`正则表达式`功能代码")
   * [数据安全加密](https://github.com/natural1law/briefness/ "点击查看使用`安全加密`功能代码")
   * [excel导出](https://github.com/natural1law/briefness/ "点击查看使用`excel导出`功能代码")
   * [生成验证码](https://github.com/natural1law/briefness/ "点击查看使用`生成验证码`功能代码")
   * [短信倒计时](https://github.com/natural1law/briefness/ "点击查看使用`短信倒计时`功能代码")
   * [延时点击](https://github.com/natural1law/briefness/ "点击查看使用`延时点击`功能代码")
   * [轻量缓存](https://github.com/natural1law/briefness/ "点击查看使用`轻量缓存`功能代码")
   * [动态代理](https://github.com/natural1law/briefness/ "点击查看使用`动态代理`功能代码")
   * [网络状态](https://github.com/natural1law/briefness/ "点击查看使用`网络状态`功能代码")
   * [文件存储](https://github.com/natural1law/briefness/ "点击查看使用`文件存储`功能代码")
   * [activirt跳转](https://github.com/natural1law/briefness/blob/master/app/src/main/java/com/androidx/briefness/base/fragment/HomePageFrag.java "点击查看使用`activirt跳转`功能代码")
   * [toast提示](https://github.com/natural1law/briefness/blob/master/app/src/main/java/com/androidx/briefness/homepage/activity/MsgShowActivity.java "点击查看使用`toast提示`功能代码")
   #### 视图布局
   * [底部导航栏](https://github.com/natural1law/briefness/blob/master/app/src/main/java/com/androidx/briefness/base/MainActivity.java "点击查看使用`底部导航栏`功能代码")
   * [对话框](https://github.com/natural1law/briefness/blob/master/app/src/main/java/com/androidx/briefness/homepage/activity/DialogActivity.java "点击查看使用`对话框`功能代码")
   * [菜单框](https://github.com/natural1law/briefness/ "点击查看使用`菜单框`功能代码")
   * [分页列表](https://github.com/natural1law/briefness/blob/master/app/src/main/java/com/androidx/briefness/homepage/activity/PageRecyclerViewActivity.java "点击查看使用`分页列表`功能代码")
   * [扫描二维码](https://github.com/natural1law/briefness/ "点击查看使用`扫描二维码`功能代码")
   * [屏幕录制和截图](https://github.com/natural1law/briefness/blob/master/app/src/main/java/com/androidx/briefness/homepage/activity/ScreenCaptureActivity.java "点击查看使用`屏幕录制和截图`功能代码")
   * [头部导航栏](https://github.com/natural1law/briefness/blob/master/app/src/main/java/com/androidx/briefness/homepage/activity/TabActivity.java "点击查看使用`头部导航栏`功能代码")
   #### 加载动画（仿zyao89）
   * [加载动画](https://github.com/natural1law/briefness/ "点击查看使用`加载动画`功能代码")
### 使用依赖
  * 全部
  ```
    implementation 'com.github.natural1law.briefness:3.4.2'
  ```
  * 分支-网络请求
  ```
  implementation 'com.github.natural1law.briefness:network:3.4.2'
  ```
  * 分支-常用工具
  ```
  implementation 'com.github.natural1law.briefness:reduce:3.4.2'
  ```
  * 分支-图表统计
  ```
  implementation 'com.github.natural1law.briefness:echarts:3.4.2'
  ```
  * 分支-视图布局
  ```
  implementation 'com.github.natural1law.briefness:view:3.4.2'
  ```
  * 分支-加载动画（仿zyao89）
  ```
  implementation 'com.github.natural1law.briefness:animation:3.4.2'
  ```

### 工具使用
  * 网络请求使用示例(DELETE请求--同GET请求)
     * GET请求
     ```
        Map<String, Object> param = new WeakHashMap<>();
        param.put("a", "1");
        param.put("b", "2");
        Rn.sendMapGet(url, param, data -> {
            /* 普通响应数据(string类型) */
            Log.i("响应数据", String.valueOf(data));
        });
     ```
     ```
        Map<String, Object> param = new WeakHashMap<>();
        param.put("a", "1");
        param.put("b", "2");
        Rn.sendMapGet(url, param, JsonObject.class, data -> {
            /* 设置响应数据类型(json、map、bean) */
            Log.i("响应数据", data.get("").getAsString());
        });
     ```
     ```
        Map<String, Object> param = new WeakHashMap<>();
        param.put("a", "1");
        param.put("b", "2");
        Rn.sendMapGetList(url, param, new TypeToken<List<JsonObject>>(){}, data -> {
            /* 设置响应数据类型(json、map、bean、list) */
            Log.i("响应数据", data.get(0).get("").getAsString());
        });
     ```
     * POST请求
     ```
        Map<String, Object> param = new WeakHashMap<>();
        param.put("a", "1");
        param.put("b", "2");
        Rn.sendMapPOst(url, param, data -> {
            Log.i("响应数据", String.valueOf(data));
        });
     ```
     ```
        JsonObject param = new JsonObject();
        param.addProperty("a", "1");
        param.addProperty("b", "2");
        // Map<String, Object> param = new WeakHashMap<>();
        // param.put("a", "1");
        // param.put("b", "2");
        Rn.sendMapPost(url, param, data -> {
            /* 普通响应string类型数据 */
            Log.i("响应数据", String.valueOf(data));
        });
     ```
     ```
        // SendModule.Request param = SendModule.Request.newBuilder()
        //        .setData(ByteString.copyFromUtf8(""))
        //        .build();//protobuf
        byte[] param = "123".getBytes();
        Rn.sendBytes(url, param.toByteArray(), data -> {
            /* 响应byte[]类型数据 */
            String result = new String(data);
            //ReceiveModule.Result result = ReceiveModule.Result.parseFrom(data);
            Log.i("响应数据", String.valueOf(result));
        });
     ```
     ```
        JsonObject param = new JsonObject();
        param.addProperty("a", "1");
        param.addProperty("b", "2");
        Rn.sendJsonFrom(url, param, data -> {
            /* 普通响应string类型数据 */
            Log.i("响应数据", String.valueOf(data));
        });
     ```
     * 上传(图片、视频、文件等)
     ```
        //带参数(key默认file)
        String url = "http://localhost:8080/a/b";
        Map<String, Object> param = new WeakHashMap<>();
        param.put("a", "1");
        param.put("b", "2");
        String path = Storage.Locality.generatePicturesPath("/WeiXin/", "1.jpg");
        Rn.sendUpload(url, param, path, data -> {
            Log.i("响应数据", String.valueOf(data));
        });
        //不带参数(key默认file)
        Rn.sendUpload(url, path, data -> {
            Log.i("响应数据", String.valueOf(data));
        });
        //完整参数
        Rn.sendUpload(url, param, "key", path, data -> {
            Log.i("响应数据", String.valueOf(data));
        });
     ```
     * 下载(图片、视频、文件等)
     ```
         String path = "";//保存的地址
         Rn.sendDownload(url, path, new DownloadListener() {
            @Override
            public void start() {//开始下载
                
            }

            @Override
            public void running(BigDecimal process) {//当前进度

            }

            @Override
            public void finish(File file, double duration) {//下载完成(duration:总耗时)

            }

            @Override
            public void error(String error) {//下载错误信息

            }

            @Override
            public void fail(String fail) {//异常信息

            }
        });
     ```

### 更新日志
  * [历史版本](https://github.com/natural1law/briefness/blob/master/HISTORY_VERSION.md "点击查看历史版本")

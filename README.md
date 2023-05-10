Android开发工具-
======
> * 此工具使用兼容Android API 24(7.0)版本或以上、JDK1.8或以上</br>
> * 这里有很多工具是根据大佬们的工具进行二次封装的。 有些是没适配Androidx的, 有些是API较低的, 还有些是调用复杂的。所以我进行简单化后使用</br>
> * 此工具如有问题欢迎大家指教。</br>
> * 作者很懒, 没留下示例图
***
### 工具目录
   * [图表统计](#图形统计使用示例 "点击查看使用`图表统计`功能代码")
   * [网络请求（WebSocket、http、https）](#网络请求使用示例 "点击查看使用`网络请求`功能代码")
   * [正则表达式](#正则表达式使用示例 "点击查看使用`正则表达式`功能代码")
   * [数据安全](#数据安全使用示例 "点击查看使用`MD5、AES、RSA、Base64、SHA1`加密数据功能代码")
   * [excel导出](#excel使用示例 "点击查看使用`excel导出`功能代码")
   * [生成验证码](#生成验证码使用示例 "点击查看使用`生成验证码`功能代码")
   * [短信倒计时](#短信倒计时使用示例 "点击查看使用`短信倒计时`功能代码")
   * [延时点击](#延时点击使用示例 "点击查看使用`延时点击`功能代码")
   * [SharedPreferences缓存封装](#SharedPreferences缓存使用示例 "点击查看使用`轻量缓存`功能代码")
   * [动态代理](#动态代理使用示例 "点击查看使用`动态代理`功能代码")
   * [网络状态](#网络状态使用示例 "点击查看使用`网络状态`功能代码")
   * [文件存储](#文件存储使用示例 "点击查看使用`文件存储`功能代码")
   * [activirt跳转](#activirt跳转使用示例 "点击查看使用`activirt跳转`功能代码")
   * [toast提示](#toast提示使用示例 "点击查看使用`toast提示`功能代码")
   * [底部导航栏](#底部导航栏使用示例 "点击查看使用`底部导航栏`功能代码")
   * [对话框](#对话框使用示例 "点击查看使用`对话框`功能代码")
   * [菜单框](#菜单框使用示例 "点击查看使用`菜单框`功能代码")
   * [分页列表](#分页列表使用示例 "点击查看使用`分页列表`功能代码")
   * [扫描二维码](#扫描二维码或条形码使用示例 "点击查看使用`扫描二维码`功能代码")
   * [屏幕录制和截图](#截图工具使用示例 "点击查看使用`屏幕录制和截图`功能代码")
   * [Tab导航栏](#Tab导航栏使用示例 "点击查看使用`头部导航栏`功能代码")
   * [RecyclerView刷新和加载](#RecyclerView刷新和加载使用示例 "点击查看使用`RecyclerView刷新和加载`功能代码")
   * [加载动画](#加载动画使用示例 "点击查看使用`加载动画`功能代码")
### 使用依赖
  * 全部
  ```
  implementation 'com.github.natural1law.briefness:3.7.6'
  ```
  * 分支-网络请求
  ```
  implementation 'com.github.natural1law.briefness:network:3.7.6'
  ```
  * 分支-常用工具
  ```
  implementation 'com.github.natural1law.briefness:reduce:3.7.6'
  ```
  * 分支-图表统计
  ```
  implementation 'com.github.natural1law.briefness:echarts:3.7.6'
  ```
  * 分支-视图布局
  ```
  implementation 'com.github.natural1law.briefness:view:3.7.6'
  ```
  * 分支-加载动画（仿zyao89）
  ```
  implementation 'com.github.natural1law.briefness:animation:3.7.6'
  ```

### 工具使用
   ##### 网络请求使用示例
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
        Rn.sendMapPost(url, param, data -> {
            Log.i("响应数据", String.valueOf(data));
        });
     ```
     ```
        JsonObject param = new JsonObject();
        param.addProperty("a", "1");
        param.addProperty("b", "2");
        Rn.sendJsonPost(url, param, data -> {
            /* 普通响应string类型数据 */
            Log.i("响应数据", String.valueOf(data));
        });
     ```
   * POST请求-protobuf类型参数
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
   * POST请求-from表单形式提交
     ```
        JsonObject param = new JsonObject();
        param.addProperty("a", "1");
        param.addProperty("b", "2");
        Rn.sendJsonFrom(url, "key", param, data -> {
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
   * WebSocket(需要和服务端配合调用)
     ```
        public static Enqueue enqueue;
        
        enqueue = Rn.initWebSocket(wsUrl(), param)
                .setLoginCallback(() -> Toasts.i("webSocket", "连接成功"))
                .setMsgCallback((code, msg, data) -> publicKey = Secure.Base64.decode(data.toStringUtf8()));
                
        enqueue.send();
     ```
   ##### 图形统计使用示例
   > [echarts官网](https://echarts.apache.org/zh/index.html) </br>
   > [echarts Github](https://github.com/apache/echarts) </br>
   > [AgentWeb](https://github.com/Justson/AgentWeb/) </br>
   * android-xml代码
     ```
        <?xml version="1.0" encoding="utf-8"?>
        <androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
            xmlns:app="http://schemas.android.com/apk/res-auto"
            xmlns:tools="http://schemas.android.com/tools"
            android:layout_width="match_parent"
            android:layout_height="match_parent">
                
             <androidx.appcompat.widget.LinearLayoutCompat
                android:id="@+id/activity_web_layout"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:orientation="vertical"
                android:overScrollMode="never"
                android:scrollbars="none"
                app:layout_constraintBottom_toBottomOf="parent"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="parent" />
                
         </androidx.constraintlayout.widget.ConstraintLayout>
     ```
   * android代码
     ```
        public final class EchartsActivity extends BaseWebActivity {
        
            @BindView(R.id.activity_web_layout)
            public LinearLayoutCompat webView;
            
            private Unbinder unbinder;
            private final EchartsActivity aThis = this;
    
            @Override
            protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.xxx);
                unbinder = ButterKnife.bind(aThis);
                super.initWeb(webView).setLoadListener(() -> {
                   Map<String, Object> param = new ConcurrentHashMap<>();
                   Rn.sendMapPost(url, param, data -> {
                       JsonObject json = new Gson().fromJson(String.valueOf(data), JsonObject.class);
                       super.setCallJs("callJS", json);
                       super.setStart("index.html");
                   });
               });
            } 
            
            @Override
            public void onDestroy() {
                super.onDestroy();
                unbinder.unbind();
            }
        }
     ```
   * html代码(assets/index.html)
     ```
        <!DOCTYPE html>
        <html style="width:100%; height:100%;">
          <head>
            <meta charset="UTF-8">
            <script type="text/javascript" src="./echarts.min.js"></script>
            <script type="text/javascript" src="./jquery.js"></script>
          </head>
          <body style="width: 98%; height: 95%;">
            <div id="container" style="width: 100%; height: 100%;"></div>
            <script type="text/javascript" src="./index.js"></script>
          </body>
        </html>
     ```
   * js代码(assets/index.js)
     ```
        //android代码中对应 super.setCallJs("callJS", json);
        function callJS(param){
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('container'));
        // 指定图表的配置项和数据
        var option = {
            title: {
                    text: ''
                },
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    data: ['目标干球', '干球温度', '目标湿球', '湿球温度'],
                    textStyle: {
                        "fontSize": 18
                    }
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                toolbox: {
                    feature: {
                        dataZoom: {
                            yAxisIndex: 'none'
                        },
                        magicType: {type: ['line', 'bar']},
                        restore: {}
                    }
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: param.times,
                    axisLabel:{
      //                  interval: 0
                    }
                },
                yAxis: {
                    type: 'value',
                    min:0,
                    max: 80,
                    axisLabel: {
                        formatter: '{value} °C'
                    }
                },
                series: [
                    {
                        name: '目标干球',
                        type: 'line',
                        smooth: true,
                        lineStyle: {
                            width: 1,
                            type: 'dashed'
                        },
                        data: param.tdbList
                    },
                    {
                        name: '干球温度',
                        type: 'line',
                        smooth: true,
                        data: param.dbtList
                    },
                    {
                        name: "目标湿球",
                        type: 'line',
                        smooth: true,
                        lineStyle: {
                            width: 1,
                            type: 'dashed'
                        },
                        data: param.twbList
                    },
                    {
                        name: "湿球温度",
                        type: 'line',
                        smooth: true,
                        data: param.wbtList
                    },
                    {
                        name: "火力档位",
                        type: 'line',
                        smooth: false,
                        showSymbol: false,
                        symbolSize: -1,
                        lineStyle: {
                            width: 0, // 线宽是0
                            color: 'rgba(0, 0, 0, 0)' // 线的颜色是透明的
                        },
      //                  stack: '总量',
                        data: param.gearsList
                    }
                ]
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
     }

     ```
   ##### 截图工具使用示例
   * 初始化屏幕录制对象
     ```
     private ScreenRecording sr;
     
     @Override
     public void onCreate(@Nullable Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         sr = ScreenRecording.build(aThis).setNotification(NotificationBar.setSystem(this, "正在使用录屏丨截屏功能", "", R.mipmap.radio_on));
     }
     ```
   * 销毁对象
     ```
     @Override
     protected void onDestroy() {
         super.onDestroy();
         sr.onDestroy();
     }
     ```
   * 调用的截图功能
     ```
       sr.onStartCapture((fileUrl, exists) -> {
           Log.i("截图地址-" + exists, fileUrl);
       });
     ```
   * 调用的录屏功能
     ```
       sr.onStartRecording((fileUrl, exists) -> {
           Log.i("视频地址-" + exists, fileUrl);
       });
     ```
   * 停止的录屏功能
     ```
       sr.onStopRecording();
     ```
   ##### 正则表达式使用示例
   * 默认
     ```
       String mobile = "13344445555";
       if(!Regular.isMobile(mobile)){//条件不成立} else {//条件成立}
     ```
   * 自定义
     ```
       String mobile = "13344445555";
       String regex = "^((13[0-9])|(14[5,7]|[9])|(15[0-3,5-9])|(17[0-8])|(18[0-9])|(19[8-9]))\d{8}$";
       if(!Regular.isMobile(regex, mobile)){//条件不成立} else {//条件成立}
     ```
   ##### 数据安全使用示例
   * MD5
     ```
       String pass = "123456";
       Log.i("MD5加密数据", Secure.MD5.encrypt(pass));//含随机盐(48位)
     ```
     ```
       String pass = "123456";
       String salt = "0";
       Log.i("MD5加密数据", Secure.MD5.encrypt(pass, salt));//非含随机盐(48位)
     ```
     ```
       String pass = "123456";
       String md5Pass = Secure.MD5.encrypt(pass);
       Log.i("MD5数据校验", Secure.MD5.verify(pass, md5Pass));
     ```
   * AES
     ```
       private final String key = Secure.AES.key();
     ```
     ```
       String pass = "123456";
       String enPass = Secure.AES.encrypt(key, pass);
       Log.i("AES数据加密", enPass);
       String dePass = Secure.AES.decrypt(key, enPass);
       Log.i("AES数据解密", dePass);
     ```
   * RSA
     ```
       private String puk;
       private String prk;
       
       KeyPair key = Secure.RSA.keyPair();
       puk = Secure.RSA.publicKey(key);//公钥
       prk = Secure.RSA.privateKey(key);//私钥
     ```
     ```
       String data = "123456";
       String enData = Secure.RSA.encryptPublic(puk, data);//公钥加密
       Log.i("RSA公钥加密数据", enData);
       String deData = Secure.RSA.decryptPrivate(prk, enData);//私钥解密
       Log.i("RSA私钥解密数据", deData);
     ```
     ```
       String data = "123456";
       String enData = Secure.RSA.encryptPrivate(prk, data);//私钥加密
       Log.i("RSA私钥加密数据", enData);
       String deData = Secure.RSA.decryptPublic(puk, enData);//公钥解密
       Log.i("RSA公钥解密数据", deData);
     ```
     ```
       String data = "123456";
       String enData = Secure.RSA.encryptPrivate(prk, data);//私钥加密
       Log.i("RSA私钥加密数据", enData);
       String sign = Secure.RSA.sign(prk, enData);
       Log.i("RSA加密数据私钥签名", sign);
       String deData = Secure.RSA.decryptPublic(puk, enData);//公钥解密
       Log.i("RSA公钥解密数据", deData);
       String verify = Secure.RSA.verify(puk, enData, sign);//公钥解密
       Log.i("RSA私钥数据校验", verify);
     ```
   * Base64
     ```
       String data = "123456";
       String enData = Secure.Base64.encode(data);
       Log.i("Base64加密数据", enData);
       String deData = Secure.RSA.decode(enData);
       Log.i("Base64解密数据", deData);
     ```
   * SHA1
     ```
       String data = "123456";
       Log.i("SHA1加密数据", Secure.SHA1.encrypt(data));
       Log.i("SHA224加密数据", Secure.SHA224.encrypt(data));
       Log.i("SHA256加密数据", Secure.SHA256.encrypt(data));
       Log.i("SHA384加密数据", Secure.SHA384.encrypt(data));
       Log.i("SHA512加密数据", Secure.SHA512.encrypt(data));
     ```
   ##### excel使用示例
   > 目前只是导出简单excel, 后续会慢慢完善
   * 导出
     ```
       File file = new File(Storage.Locality.generateDownloadPath("/包名/", "包名/", 文件名.后缀名));//保存地址
       // File file = new File(Storage.Locality.generateDownloadPath("/包名/包名/", 文件名.后缀名));//保存地址
       // Uri uri = Storage.Locality.generateDownLoadPath(aThis, "包名/", "文件名", "文件后缀名(.xlsx)");
       List<String> header = new ArrayList<>();
       List<Map<String, Object> data = new ArrayList<>();
       boolean isSuccess = Excel.write(file, "titleName", header, data);
       // boolean isSuccess = Excel.write(uri, "titleName", header, data);
       Log.i("导出是否成功", String.ofValue(isSuccess));
     ```
   ##### 生成验证码使用示例
   * 生成随机验证码
     ```
       Captcha.getInstance().lineNumber(1)//干扰线
                .backColor(0xdf)//背景颜色
                .fontSize(15)//字体大小
                .size(0, 0)//布局宽高
                .type(CHARS)//类型 NUMBER(纯数字)、LETTER(纯英文)、CHARS(数字和英文混合)
                .codeLength(6)//验证码长度
                .into(imageView);//存放显示验证码的对象
     ```
   ##### 短信倒计时使用示例
   * 调用
     ```
       CountDown cd = CountDown.builder()
                .setView(textView)//textView的对象
                .setMillisInFuture(60 * 1000)//总时长
                .setFinishWordage("重新获取")//完成时的内容
                .setPrefixWordage("还剩")//未完成显示的内容前缀
                .setSuffixWordage("s")//未完成显示的内容后缀
                .build();
        cd.onFinish();//停止倒计时
     ```
   ##### 延时点击使用示例
   * 调用
     ```
       if (Idle.isClick()){//默认0.8秒内不能触发}
       if (Idle.isClick(2000)){//默认2秒内不能触发}
     ```
   ##### SharedPreferences缓存使用示例
   > SharedPreferences二次封装, 只是为了缩短代码引用
   * 调用
     ```
       MicroCache mc = MicroCache.getInstance(aThis);
       mc.setApply("1", "1");
       mc.setCommit("2", "2");
       Log.i("Apply的值", mc.getString("1"));
       Log.i("Commit的值", mc.getString("2"));
     ```
   ##### 动态代理使用示例
   * 调用
     ```
       public interface A {
        void c();
       }

       private enum B implements A {
           b;
           @Override
           public void c() {
           }
       }
       private void initView() {
           A a = Proxys.build(B.b).getProxy();
           a.c();
       }
     ```
   ##### 网络状态使用示例
   * 调用
     ```
       NetworkConfiguration nc = NetworkConfiguration.build(this);
       Log.i("获取运营商网络IP地址", nc.getMobileIp());
       Log.i("获取WIFI网络IP地址", nc.getWifiIp());
       Log.i("判断连通性", String.valueOf(nc.isConnected()));
       Log.i("判断以太网", String.valueOf(nc.isEtherLink()));
       Log.i("判断运营商网络", String.valueOf(nc.isMobile()));
       Log.i("判断WIFI网络", String.valueOf(nc.isWiFi()));
       Log.i("判断VPN网络", String.valueOf(nc.isVPN()));
     ```
   ##### 文件存储使用示例
   * 保存文件
     ```
       String path = Storage.Locality.generatePicturesPath("/test.txt");//保存地址并创建(内容为空)
       Storage.write(path, "你好");
     ```
   * 读取文件
     ```
       String path = Storage.Locality.generatePicturesPath("/test.txt");//保存地址并创建
       Storage.readDecode(path);
     ```
   * 缓存数据
     ```
       /* 名称相同数据会覆盖 */
       Storage.Cache.write(this, "test", "你好");//缓存string数据
       Storage.Cache.write(this, "test", 1);//缓存int数据
       Storage.Cache.write(this, "test", 'a');//缓存char数据
       String data = Storage.Cache.read(this, "test");//读取数据
     ```
   * 获取本地url
     ```
       Log.i("创建视频文件", Storage.Locality.generateVideoPath());
       Log.i("创建音频文件", Storage.Locality.generateMusicPath());
       Log.i("创建文档文件", Storage.Locality.generateDocumentsPath());
       Log.i("创建下载文件", Storage.Locality.generateDownloadPath());
       Log.i("创建图片文件", Storage.Locality.generateDCIMPath());
       Log.i("创建图片文件", Storage.Locality.generatePicturesPath());
       Log.i("创建截图文件", Storage.Locality.generateScreenshotsPath());
     ```
   ##### activirt跳转使用示例
   * 调用文件管理器中的图片（单选）
     ```
       launcher = This.initLauncher(aThis, (resultCode, intent) -> This.resultListener(aThis, intent, data -> {
            File file = new File(data);
            Toasts.i("回调数据", file.isFile());

        }));//此方法必须放到主UI线程中(onCreate、onStart等方法中)，不然异常
        this.resultAction(launcher).start();//随意使用
     ```
   * 有返回值的activity
     ```
       launcher = This.initLauncher(this, (resultCode, intent) -> {

       });//此方法必须放到主UI线程中(onCreate、onStart等方法中)，不然异常
       
       This.build().startLauncher(MsgShowActivity.class, launcher).execute();//MsgShowActivity.class目标activity
     ```
   * 跳转目标activity中（主要解决在子线程执行异常同时简化使用时代码）
     ```
       This.build().activity(this, MsgShowActivity.class).start();
     ```
     ```
       Bundle bundle = new Bundle();
       bundle.putString("", "");
       This.build().activity(this, MsgShowActivity.class, bundle).start();//带参数跳转
     ```
     ```
       This.build().activity(this, MsgShowActivity.class, true).start();跳转后关闭当前页面
     ```
   ##### toast提示使用示例
   > 引用Toasty进行二次封装/[Toasty项目地址](https://github.com/GrenderG/Toasty)
   * Toast
     ```
       Toasts toasts = Toasts.builder(aThis);
       toasts.setMsg("成功").showSuccess();
       toasts.setMsg("失败").showError();
       toasts.setMsg("无").showNormal();
       toasts.setMsg("提示").showInfo();
       toasts.setMsg("警告").showWarning();
       toasts.setMsg("原生").showOriginal();
     ```
   * Log
     ```
        Toasts.setDebug(false); //true 开启日志输出(e无效, 默认true)
        Toasts.i("TAG", "你好!");
        Toasts.e("TAG", "你好!");
        Toasts.d("TAG", "你好!");
        Toasts.w("TAG", "你好!");
        Toasts.v("TAG", "你好!");
        Toasts.wtf("TAG", "你好!");
     ```
   ##### 底部导航栏使用示例
   * 调用
     ```
       public class MainActivity extends BaseActivity {

         private final MainActivity aThis = this;

         @Override
         protected int layoutId() { return R.layout.activity_main; }

         @Override
         protected void onCreate() {
             //底部导航栏
             NavigationBar.builder(aThis)
                    .setMenu(R.menu.nav_menu_default)//选项菜单
                    .setAddFragment(new HomePageFrag())//添加fragment对象
                    .setAddFragment(new MyPageFrag())//添加顺序影响显示顺序
                    .setBackgroundColor(R.color.gray)//背景颜色
                    .build();
         }
     }
     ```
   * fragment对象创建
     ```
       public final class HomePageFrag extends BaseFragment {//默认Fragment也可以
           @Override
           protected int layoutId() { return R.layout.frag_homepage; }
           
           @Override
           protected void onCreateView(View view) {
           }
           
           @Override
           protected void onViewCreated(View view) {
           }
       }
     ```
   * mainActivity布局
     ```
       <?xml version="1.0" encoding="utf-8"?>
       <FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
           xmlns:tools="http://schemas.android.com/tools"
           android:layout_width="match_parent"
           android:layout_height="match_parent"
           android:orientation="vertical"
           tools:context=".base.MainActivity">

           <!-- 必须添加 -->
           <include layout="@layout/nav_layout" />

       </FrameLayout>
     ```
   * 选项菜单
     ```
       <?xml version="1.0" encoding="utf-8"?>
       <menu xmlns:android="http://schemas.android.com/apk/res/android"
           xmlns:tools="http://schemas.android.com/tools"
           tools:ignore="MissingDefaultResource">
 
           <item
               android:id="@+id/first"
               android:icon="@drawable/nav_icon1"
               android:title="首页" />

           <item
               android:id="@+id/second"
               android:icon="@drawable/nav_icon4"
               android:title="我的"
               tools:ignore="HardcodedText" />

       </menu>
     ```
   ##### 对话框使用示例
   * 不带取消dialog
     ```
       DialogDefault.console(aThis, "content", dialog -> {
           toasts.setMsg("确认").showSuccess();
           dialog.cancel();
       });
     ```
   * 带取消dialog
     ```
       DialogDefault.alert(aThis, "title", "content", dialog -> {
           toasts.setMsg("确认").showSuccess();
           dialog.cancel();
       });
     ```
     ```
       DialogDefault.alert(aThis, "title", "content", new OnClickTriggerListener() {
           @Override
           public void ok(DialogServlet dialog) {
               toasts.setMsg("确认").showSuccess();
               dialog.cancel();
           }
           @Override
           public void no(DialogServlet dialog) {
               toasts.setMsg("取消").showSuccess();
               dialog.cancel();
           }
       });
     ```
   * 相机相册选择dialog(单选)
     ```
       DialogDefault.camera(aThis, photos -> {
           Toasts.i("Uri", photos.get(0).uri);
           Toasts.i("Path", photos.get(0).path);
       });
     ```
   * dialog倒计时关闭
     ```
       DialogDefault.countDownTime(aThis, "正在加载", "还有", 60, "秒加载完成", () -> {
           toasts.setMsg("加载完成").showSuccess();
           Toasts.i("正在加载", "加载完成");
       });
     ```
   * 自定义dialog
     ```
       DialogCall.builder()
                .setLayoutView(R.layout.dialog_alert)
                .setLayoutViewId(R.id.dialog_frame)
                .setCanceled(false)
                .setCancelable(false)
                .setLayoutGravity(CENTER)
                .build()
                .get(context)
                .setText(R.id.dialog_title, title)
                .setText(R.id.dialog_content, content)
                .setOnClickListener(R.id.dialog_affirm, listener)
                .setOnClickListener(R.id.dialog_quit, listener::no)
                .show();
     ```
   * 自定义布局
     ```
       <?xml version="1.0" encoding="utf-8"?>
       <FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
           xmlns:tools="http://schemas.android.com/tools"
           android:id="@+id/dialog_frame"
           android:layout_width="match_parent"
           android:layout_height="wrap_content"
           android:layout_gravity="center"
           android:layout_marginStart="40dp"
           android:layout_marginEnd="40dp"
           android:background="@drawable/border_dialog_cue"
           android:orientation="vertical">

           <androidx.appcompat.widget.AppCompatTextView
               android:id="@+id/dialog_title"
               android:layout_width="match_parent"
               android:layout_height="55dp"
               android:padding="15dp"
               android:text="标题"
               android:textColor="@color/hint"
               android:textSize="18sp"
               android:textStyle="bold"
               tools:ignore="HardcodedText" />

           <androidx.appcompat.widget.AppCompatTextView
               android:id="@+id/dialog_content"
               android:layout_width="match_parent"
               android:layout_height="match_parent"
               android:layout_marginTop="55dp"
               android:layout_marginBottom="50dp"
               android:gravity="center_horizontal"
               android:padding="10dp"
               android:text="内容"
               android:textColor="#282222"
               android:textSize="15sp"
               android:textStyle="normal"
               tools:ignore="HardcodedText" />

           <androidx.appcompat.widget.AppCompatTextView
               android:id="@+id/dialog_affirm"
               android:layout_width="match_parent"
               android:layout_height="50dp"
               android:layout_gravity="bottom"
               android:layout_marginTop="140dp"
               android:background="@drawable/border_bottom"
               android:gravity="center"
               android:text="确认"
               android:textColor="#ffffff"
               android:textSize="14sp"
               tools:ignore="HardcodedText,RtlSymmetry,TextContrastCheck" />

        </FrameLayout>
     ```
   ##### 菜单框使用示例
   > [FloatMenu项目地址](https://github.com/GrenderG/Toasty)
   * 声明point
     ```
       private final Point point = new Point();
     ```
   * 调用
     ```
       FloatMenu menu = new FloatMenu(aThis);
           menu.inflate(R.menu.setting, Convert.Pixel.get(aThis).dp(150));
           menu.setOnItemClickListener((v, position) -> {
               switch (position) {
                  case 0:
                      break;
                  case 1:
                      break;
              }
              menu.dismiss();
          });
       // menu.show();
       menu.show(point);
     ```
   * 获取屏幕坐标
     ```
       @Override
       public boolean dispatchTouchEvent(MotionEvent ev) {
           if (ev.getAction() == MotionEvent.ACTION_DOWN) {
               point.x = (int) ev.getRawX();
               point.y = (int) ev.getRawY();
           }
           return super.dispatchTouchEvent(ev);
       }
     ```
   * menu
     ```
       <?xml version="1.0" encoding="utf-8"?>
       <menu xmlns:app="http://schemas.android.com/apk/res-auto"
           xmlns:tools="http://schemas.android.com/tools">
 
           <item
               app:icon="@mipmap/frame7"
               app:menu_title="第一条"
               tools:ignore="MenuTitle" />

           <item
               app:icon="@mipmap/frame9"
               app:menu_title="第二条"
               tools:ignore="MenuTitle" />
      </menu>
     ```
   ##### 分页列表使用示例
   * 布局
     ```
       <com.androidx.view.page.PaginationRecycleView
           android:id="@+id/listview"
           android:layout_width="match_parent"
           android:layout_height="match_parent"
           android:layout_marginTop="100dp"
           android:orientation="vertical"
           android:overScrollMode="never"
           android:scrollbars="none"
           app:count="7"
           app:layout_constraintBottom_toBottomOf="parent"
           app:layout_constraintEnd_toEndOf="parent"
           app:layout_constraintStart_toStartOf="parent"
           app:layout_constraintTop_toBottomOf="@+id/title_layout"
           app:progressAnimation="SnakeCircleBuilder"
           app:progressColor="@color/irs" />
     ```
   * 调用
     ```
       @BindView(R.id.listview)
       public PaginationRecycleView<JsonObject> listView;
       
       listView.setAdapterAndManager(new PageAdapter(), new LinearLayoutManager(aThis));
       listView.setListener(position -> {//position当前页码
           listView.addItem(position, jsonList, 99);//jsonList数据集合/99数据总和
           listView.loadingFinish();//加载完成
       });
     ```
   * adapter
     ```
       public final class PageAdapter extends PaginationRecycleView.Adapter<JsonObject> {

           @Override
           protected int onLayoutId() {
               return R.layout.adapter_page;
           }

           @Override
           protected void onBindHolderView(HolderView holder, JsonObject json) {
               holder.setText(R.id.info, json.get("name").getAsString());
               holder.setOnClickListener(R.id.info, view -> holder.setFlexible(R.id.info1));
           }
       }
     ```
   ##### 扫描二维码或条形码使用示例
   > [扫描二维码项目地址](https://github.com/bingoogolapple/BGAQRCode-Android)
   * 回调
     ```
       ScanTools.callback(aThis, (resultCode, data) -> {
           Log.i("回调码", String.valueOf(resultCode));
           Log.i("回调数据", data);
       });
     ```
   * 调用
     ```
       ScanTools.start();
     ```
   ##### Tab导航栏使用示例
   * 布局
     ```
         <?xml version="1.0" encoding="utf-8"?>
         <androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
             xmlns:app="http://schemas.android.com/apk/res-auto"
             xmlns:tl="http://schemas.android.com/apk/res-auto"
             xmlns:tools="http://schemas.android.com/tools"
             android:layout_width="match_parent"
             android:layout_height="match_parent"
             tools:ignore="MissingDefaultResource">

             <com.androidx.view.tab.layout.SegmentTabLayout
                 android:id="@+id/sliding"
                 android:layout_width="match_parent"
                 android:layout_height="50dp"
                 android:background="@color/gray"
                 app:layout_constraintEnd_toEndOf="parent"
                 app:layout_constraintStart_toStartOf="parent"
                 app:layout_constraintTop_toBottomOf="@+id/title_layout"
                 tl:tl_divider_color="#1A000000"
                 tl:tl_divider_padding="13dp"
                 tl:tl_divider_width="1dp"
                 tl:tl_indicator_color="#7ADFFF"
                 tl:tl_indicator_height="1.5dp"
                 tl:tl_indicator_width_equal_title="true"
                 tl:tl_tab_padding="22dp"
                 tl:tl_tab_space_equal="true"
                 tl:tl_textSelectColor="#7ADFFF"
                 tl:tl_textUnselectColor="@color/black1"
                 tl:tl_underline_color="#1A000000"
                 tl:tl_underline_height="1dp" />

             <androidx.viewpager2.widget.ViewPager2
                 android:id="@+id/vp2"
                 android:layout_width="match_parent"
                 android:layout_height="match_parent"
                 android:layout_marginTop="100dp"
                 android:layout_weight="1"
                 android:overScrollMode="never"
                 android:scrollbars="none"
                 app:layout_constraintBottom_toBottomOf="parent"
                 app:layout_constraintEnd_toEndOf="parent"
                 app:layout_constraintStart_toStartOf="parent"
                 app:layout_constraintTop_toBottomOf="@+id/sliding" />

         </androidx.constraintlayout.widget.ConstraintLayout>
     ```
   * 声明
     ```
       @BindView(R.id.sliding)
       public SegmentTabLayout segmentTabLayout;// CommonTabLayout、SlidingTabLayout
       @BindView(R.id.vp2)
       public ViewPager2 viewPager2;
       
       private TabLayoutBar tabView;
     ```
   * 销毁
     ```
       @Override
       public void onDestroy() {
           super.onDestroy();
           tabView.destroy();
       }
     ```
   * 调用
     ```
       tabView = TabLayoutBar.builder()
                .setActivity(aThis)
                .setViewPager2(viewPager2)
                .setTabLayout(segmentTabLayout)
                .setTitles("Common", "Sliding", "Segment")
                .setFragments(new CommonFragment(), new SlidingFragment(), new SegmentFragment())
                .initBuild();
        tabView.execute();
     ```
   ##### RecyclerView刷新和加载使用示例
   * code
     ```
       RefreshRecycler.execute(aThis, new Adapter(), (refresh, adapter, pageCode, status) -> {
            Toasts.i("当前页码", pageCode);
            Map<String, Object> param = new ConcurrentHashMap<>();
            param.put("pageCode", "pageCode");
            Rn.sendMapPost(url(), param, JsonObject.class, data -> {
                adapter.addTotalItem(data.get("total").getAsInt());
                List<String> dataList = new Gson().fromJson(data.get("dataList"), new TypeToken<List<String>>() {
                }.getType());
                if (status) {
                    adapter.addItem(dataList);
                    refresh.finishRefresh();
                } else {
                    adapter.loadItem(dataList);
                    refresh.finishLoadMore();
                }
            });
        }, module -> {
            Toasts.i("item数据", module);
            toasts.setMsg(module).showSuccess();
        });
     ```
   * adapter
     ```
       public static final class Adapter extends RefreshAdapter<String> {
         @Override
         protected int layoutId() { return R.layout.adapter_refresh; }
         @Override
         protected void dispose(@NonNull HolderView holder, int position, String model) {
             int a = R.id.a;
             holder.setText(a, model);
             holder.setOnClickListener(a, view -> setOnClickItemListener(position));
         }
      }
     ```
   * layout
     ```
       <?xml version="1.0" encoding="utf-8"?>
       <androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
           xmlns:app="http://schemas.android.com/apk/res-auto"
           android:layout_width="match_parent"
           android:layout_height="match_parent">

           <include
               android:id="@+id/title_layout"
               layout="@layout/title_bar"
               app:layout_constraintEnd_toEndOf="parent"
               app:layout_constraintStart_toStartOf="parent"
               app:layout_constraintTop_toTopOf="parent" />

           <include
               layout="@layout/refresh_recycler_view"
               android:layout_width="match_parent"
               android:layout_height="match_parent"
               android:layout_marginTop="50dp"
               app:layout_constraintBottom_toBottomOf="parent"
               app:layout_constraintEnd_toEndOf="parent"
               app:layout_constraintStart_toStartOf="parent"
               app:layout_constraintTop_toBottomOf="@+id/title_layout" />
       </androidx.constraintlayout.widget.ConstraintLayout>
     ```
   ##### 加载动画使用示例
   > [ZLoading项目地址](https://github.com/zyao89/ZLoading)
   * 布局
     ```
       <com.androidx.animation.view.ProgressView
           android:id="@+id/progress"
           android:layout_width="wrap_content"
           android:layout_height="wrap_content"
           android:layout_gravity="center"
           app:layout_constraintBottom_toBottomOf="parent"
           app:layout_constraintEnd_toEndOf="parent"
           app:layout_constraintStart_toStartOf="parent"
           app:layout_constraintTop_toTopOf="parent"
           app:progressColor="@color/irs"
           app:progressType="STAIRS_PATH" />
      ```
     </br>
### 更新日志
  * [历史版本](https://github.com/natural1law/briefness/blob/master/HISTORY_VERSION.md "点击查看历史版本")

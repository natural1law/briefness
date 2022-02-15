Android开发工具 [![](https://jitpack.io/v/natural1law/briefness.svg)](https://jitpack.io/#natural1law/briefness)
======

### 工具目录
   #### 图形统计
   * [图表统计](#图形统计使用示例 "点击查看使用`图表统计`功能代码")
   #### 网络请求
   * [网络请求（WebSocket长连接、http/https）](#网络请求使用示例 "点击查看使用`网络请求`功能代码")
   #### 常用工具
   * [正则表达式](#正则表达式使用示例 "点击查看使用`正则表达式`功能代码")
   * [数据安全加密](#数据安全加密使用示例 "点击查看使用`安全加密`功能代码")
   * [excel导出](# "点击查看使用`excel导出`功能代码")
   * [生成验证码](# "点击查看使用`生成验证码`功能代码")
   * [短信倒计时](# "点击查看使用`短信倒计时`功能代码")
   * [延时点击](# "点击查看使用`延时点击`功能代码")
   * [轻量缓存](# "点击查看使用`轻量缓存`功能代码")
   * [动态代理](# "点击查看使用`动态代理`功能代码")
   * [网络状态](# "点击查看使用`网络状态`功能代码")
   * [文件存储](# "点击查看使用`文件存储`功能代码")
   * [activirt跳转](# "点击查看使用`activirt跳转`功能代码")
   * [toast提示](# "点击查看使用`toast提示`功能代码")
   #### 视图布局
   * [底部导航栏](# "点击查看使用`底部导航栏`功能代码")
   * [对话框](# "点击查看使用`对话框`功能代码")
   * [菜单框](# "点击查看使用`菜单框`功能代码")
   * [分页列表](# "点击查看使用`分页列表`功能代码")
   * [扫描二维码](# "点击查看使用`扫描二维码`功能代码")
   * [屏幕录制和截图](#截图工具使用示例 "点击查看使用`屏幕录制和截图`功能代码")
   * [头部导航栏](# "点击查看使用`头部导航栏`功能代码")
   #### 加载动画（仿zyao89）
   * [加载动画](#工具使用)
### 使用依赖
  * 全部
  ```
    implementation 'com.github.natural1law.briefness:3.4.7'
  ```
  * 分支-网络请求
  ```
  implementation 'com.github.natural1law.briefness:network:3.4.7'
  ```
  * 分支-常用工具
  ```
  implementation 'com.github.natural1law.briefness:reduce:3.4.7'
  ```
  * 分支-图表统计
  ```
  implementation 'com.github.natural1law.briefness:echarts:3.4.7'
  ```
  * 分支-视图布局
  ```
  implementation 'com.github.natural1law.briefness:view:3.4.7'
  ```
  * 分支-加载动画（仿zyao89）
  ```
  implementation 'com.github.natural1law.briefness:animation:3.4.7'
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
   ##### 图形统计使用示例
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
     private void capture(){
         sr.onStartCapture((fileUrl, exists) -> {
            Log.i("截图地址-" + exists, fileUrl);
         });
     }
     ```
   * 调用的录屏功能
     ```
     private void startRecording(){
         sr.onStartRecording((fileUrl, exists) -> {
             Log.i("视频地址-" + exists, fileUrl);
         });
     }
     ```
   * 停止的录屏功能
     ```
     private void startRecording(){
         sr.onStopRecording();
     }
     ```
   ##### 正则表达式使用示例
   * 默认
     ```
     public void estimate() {
         String mobile = "13344445555";
         if(!Regular.isMobile(mobile)){//条件不成立
             
         } else {//条件成立
         
         }
     }
     ```
   * 自定义
     ```
     public void estimate() {
         String mobile = "13344445555";
         String regex = "^((13[0-9])|(14[5,7]|[9])|(15[0-3,5-9])|(17[0-8])|(18[0-9])|(19[8-9]))\d{8}$";
         if(!Regular.isMobile(regex, mobile)){//条件不成立
             
         } else {//条件成立
         
         }
     }
     ```
   ##### 数据安全加密使用示例
   * MD5
     ```
       /**
        * 含随机盐(48位)
        */
       public void example(){
           String pass = "123456";
           Log.i("MD5加密数据", Secure.MD5.encrypt(pass));
       }
     ```
     ```
       /**
        * 非含随机盐(48位)
        */
       public void example(){
           String pass = "123456";
           String salt = "0";
           Log.i("MD5加密数据", Secure.MD5.encrypt(pass, salt));
       }
     ```
     ```
       public void example(){
           String pass = "123456";
           String md5Pass = Secure.MD5.encrypt(pass);
           Log.i("MD5数据校验", Secure.MD5.verify(pass, md5Pass));
       }
     ```
   * AES
     ```
       private final String key = Secure.AES.key();
     ```
     ```
       public void example(){
           String pass = "123456";
           String aesPass = Secure.AES.encrypt(key, pass);
           Log.i("AES数据加密", aesPass);
       }
     ```
     ```
       public void example(){
           String pass = "123456";
           String aesPass = Secure.AES.decrypt(pass);
           Log.i("AES数据解密", aesPass);
       }
     ```
   * RSA
     ```
       private String puk;
       private String prk;
       
       public void example(){
           KeyPair key = Secure.RSA.keyPair();
           puk = Secure.RSA.publicKey(key);//公钥
           prk = Secure.RSA.privateKey(key);//私钥
       }
     ```
     ```
       public void example(){
           String data = "123456";
           String enData = Secure.RSA.encryptPublic(puk, data);//公钥加密
           Log.i("RSA公钥加密数据", enData);
           String deData = Secure.RSA.decryptPrivate(prk, enData);//私钥解密
           Log.i("RSA私钥解密数据", deData);
       }
     ```
     ```
       public void example(){
           String data = "123456";
           String enData = Secure.RSA.encryptPrivate(prk, data);//私钥加密
           Log.i("RSA私钥加密数据", enData);
           String deData = Secure.RSA.decryptPublic(puk, enData);//公钥解密
           Log.i("RSA公钥解密数据", deData);
       }
     ```
     ```
       public void example(){
           String data = "123456";
           String enData = Secure.RSA.encryptPrivate(prk, data);//私钥加密
           Log.i("RSA私钥加密数据", enData);
           String sign = Secure.RSA.sign(prk, enData);
           Log.i("RSA加密数据私钥签名", sign);
           String deData = Secure.RSA.decryptPublic(puk, enData);//公钥解密
           Log.i("RSA公钥解密数据", deData);
           String verify = Secure.RSA.verify(puk, enData, sign);//公钥解密
           Log.i("RSA私钥数据校验", verify);
       }
     ```
   * Base64
     ```
       public void example(){
           String data = "123456";
           String enData = Secure.Base64.encode(data);
           Log.i("Base64加密数据", enData);
           String deData = Secure.RSA.decode(enData);
           Log.i("Base64解密数据", deData);
       }
     ```
   * SHA1
     ```
       public void example(){
           String data = "123456";
           Log.i("SHA1加密数据", Secure.SHA1.encrypt(data));
           Log.i("SHA224加密数据", Secure.SHA224.encrypt(data));
           Log.i("SHA256加密数据", Secure.SHA256.encrypt(data));
           Log.i("SHA384加密数据", Secure.SHA384.encrypt(data));
           Log.i("SHA512加密数据", Secure.SHA512.encrypt(data));
       }
     ```
### 更新日志
  * [历史版本](https://github.com/natural1law/briefness/blob/master/HISTORY_VERSION.md "点击查看历史版本")

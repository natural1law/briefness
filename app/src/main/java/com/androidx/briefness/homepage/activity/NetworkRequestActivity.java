package com.androidx.briefness.homepage.activity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.provider.Telephony.Carriers.USER;
import static com.androidx.briefness.base.App.kv;
import static com.androidx.briefness.homepage.service.NotificationService.enqueue;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.androidx.briefness.R;
import com.androidx.briefness.base.BaseActivity;
import com.androidx.briefness.homepage.module.LoginModule;
import com.androidx.briefness.homepage.service.NotificationService;
import com.androidx.http.use.Rn;
import com.androidx.reduce.tools.Idle;
import com.androidx.reduce.tools.Secure;
import com.androidx.reduce.tools.Storage;
import com.androidx.reduce.tools.Toasts;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * @date 2021/04/30
 */
@SuppressLint("NonConstantResourceId")
public final class NetworkRequestActivity extends BaseActivity {

    private final AppCompatActivity aThis = this;
    @BindView(R.id.title_layout)
    public FrameLayout titleLayout;
    @BindView(R.id.title_return_image)
    public AppCompatImageView imageView;
    @BindView(R.id.title_text)
    public AppCompatTextView titleView;
    @BindView(R.id.network_content)
    public AppCompatTextView contentView;

    private String publicKey;

    private static native String url();

    private static native String url1();

    private static native String wsUrl();

    private static native String rsa(String key, String value);

    @Override
    protected int layoutId() {
        return R.layout.activity_network;
    }

    @Override
    protected void onCreate() {
        setTitle(titleLayout, imageView, titleView);

//    Map<String, Object> map = new WeakHashMap<>();
//    map.put("username", "18841280510");
//    enqueue = Rn.initWebSocket("http://192.168.1.92:8082/cmp/push/notification", map)
//        .setLoginCallback(() -> Toasts.i("webSocket", "连接成功"))
//        .setMsgCallback((code, msg, data) -> {
//          Toasts.i("webSocket1", msg);
//          Toasts.i("webSocket2", data);
//        });

        Map<String, Object> map = new WeakHashMap<>();
        map.put("id", "123");
        enqueue = Rn.initWebSocket(wsUrl(), map)
                .setLoginCallback(() -> Toasts.i("webSocket", "连接成功"))
                .setMsgCallback((code, msg, data) -> publicKey = Secure.Base64.decode(data.toStringUtf8()));

//            initView();
//            KeyPair key = Secure.RSA.keyPair();
//            String k1;
//            Toasts.i("公钥", k1 =Secure.RSA.publicKey(key));
//            String k2;
//            Toasts.i("私钥", k2 = Secure.RSA.privateKey(key));
//            String v1;
//            Toasts.i("私钥加密", v1 = Secure.RSA.encryptPrivate(k2, "http://192.168.1.122:9981/api/user/login.ios"));
//            Toasts.i("公钥加密", Secure.RSA.decryptPublic(k1, v1));

        publicKey = "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAxsUJNFAFtPyVonKfnTYdWLJnZG;vIGLPjZ8ihNvebye5EM8R;Aj60hGwksitw5e1rP2M3C4YFj97iEMmfqVy@oQQEAjX@licT8F0iCetvE8kdIJWph04As;srRW85K6aOT8zqYCMh@gEU0n6IEo3m1ygtfA8TEdf5u;Mwa@vet11JLuIPCx9rg5PL7ufP54xJkDLJ;0Ka5r65qf1Wz;gDPa22aAv3SSRLZQ2T9rbsv9uOQF4j2kOrUSbeXnCHldcSL5hNHzDR4oAYAGt@w9XLq@hVRpXN7fLDyGfY53RdZPgiyFmuq56crHDzwUdFUy7@hrFvppnCZQyxHPzPVu@8T@U12qcX4S@bu@1dqwxZKzAxTD0tlBIBSDp2cYogKwR4BXc6mFNX0iG1UQ9UUpLfMvlsg91sz7D8KTOs8l3;cQuKgUIxgebA5103SOuAMZExPndZNGwDX1KcPmoAirpjx48h4V3Ci4Azd5pwdkK2PXAd8tCDxiS@pwLd3Rtk2cS2QJSZ8ifTdRR3u9n5w5gKcZZQh2bEiatfs5n3B68AowbFObvPpJbRxTjMb;HPe;dtIktx651H@z1ncNMj9ziAtQvo@mYwHro3Abe6hqWjcvu3jXmmVeZsvwezmtONoPPsUZ2YeeuE7SCknZltFsv4nrDviqnppQ@cXYQAWvQvUsCAwEAAQ==";
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (enqueue != null) enqueue.close();
    }

    @OnClick(R.id.title_return_image)
    public void imageReturn() {
        if (Idle.isClick()) finish();
    }

    private void initView() {
//        mc.setApply(USER, "小纯");
        kv.encode(USER, "小纯");
        Intent intent = new Intent(aThis, NotificationService.class);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }
    }

    @OnClick(R.id.network_send)
    public void send() {
//        String path1 = Storage.Locality.generateDownloadPath("/default/", System.currentTimeMillis() + ".png");
//        String path1 = Storage.Locality.generatePicturesPath("", "1.jpg");
//        Rn.sendUpload("http://192.168.1.92:9981/api/user/upload.android", path1, data -> toasts.setMsg(data).showSuccess());
//        Rn.show();
        String url = "http://192.168.1.94:8081/app/qualitySupervision/imgvideoUrl";
        Map<String, Object> param = new WeakHashMap<>();
        param.put("userId", "18841280510");
        param.put("projectId", "965");
        String path1 = Storage.Locality.generatePicturesPath("/WeiXin/", "1.jpg");
        Rn.sendUpload(url, param, path1, data -> {
            Toasts.i("结果", data);
        });

    }

    @OnClick(R.id.network_send1)
    public void send1() {
        Map<String, Object> param = new ConcurrentHashMap<>();
        param.put("userName", "18604900857");
        param.put("password", "LNgz@082");
        param.put("version", "1.1.6");
        Rn.sendMapPost(url(), param, data -> Toasts.i("msg", data));
    }

    @OnClick(R.id.network_send2)
    public void send2() {

//        JsonObject json = new JsonObject();
//        json.addProperty("mobile", "15555555555");
//        json.addProperty("password", "123456");
//        Rn.sendJsonPost(url1(), json, data -> {
//            Toasts.i("数据", data);
//        });

        String key = Secure.AES.key();
        LoginModule.LoginParam param = LoginModule.LoginParam.newBuilder()
                .setMobile(Secure.AES.encrypt(key, "15555555555"))
                .setPassword(Secure.AES.encrypt(key, "123456"))
                .build();

        Rn.setInterceptor(chain -> {
            Request req = chain.request().newBuilder()
                    .addHeader("Connection", "Upgrade, HTTP2-Settings")
                    .addHeader("Upgrade", "h2c")
                    .addHeader("key", rsa(publicKey, key))
                    .build();//请求
            return chain.proceed(req);//响应
        });
        Rn.show();
        Rn.sendBytes(url1(), param.toByteArray(), data -> {
            Toasts.i("数据", new String(data));
//            ReceiveModule.ReceiveResult result = ReceiveModule.ReceiveResult.parseFrom(data);
//            if (result.getCode() == 1) {
//                if (Secure.RSA.verify(publicKey, result.getToken(), result.getSign())) {
//                    String aesKey = Secure.RSA.decryptPublic(publicKey, result.getToken());
//                    String decode = Secure.AES.decrypt(aesKey, result.getData());
//                    JsonObject resData = new Gson().fromJson(decode, JsonObject.class);
//                    contentView.setText(resData.toString());
//                } else contentView.setText("无效数据");
//            } else toasts.setMsg(result.getMsg()).showError();
        });

    }

}

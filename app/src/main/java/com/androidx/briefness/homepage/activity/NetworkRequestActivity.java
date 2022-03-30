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

//        Map<String, Object> map = new WeakHashMap<>();
//        map.put("id", "123");
//        enqueue = Rn.initWebSocket(wsUrl(), map)
//                .setLoginCallback(() -> Toasts.i("webSocket", "连接成功"))
//                .setMsgCallback((code, msg, data) -> publicKey = Secure.Base64.decode(data.toStringUtf8()));

//            initView();
//            KeyPair key = Secure.RSA.keyPair();
//            String k1;
//            Toasts.i("公钥", k1 =Secure.RSA.publicKey(key));
//            String k2;
//            Toasts.i("私钥", k2 = Secure.RSA.privateKey(key));
//            String v1;
//            Toasts.i("私钥加密", v1 = Secure.RSA.encryptPrivate(k2, "http://192.168.1.122:9981/api/user/login.ios"));
//            Toasts.i("公钥加密", Secure.RSA.decryptPublic(k1, v1));

//        publicKey = "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAxSE3hpppa50zhIeqb6x5qQZo7KGd2mEi0qB5;aLGkjaoeJnsVjSeEtRfTeRfZqUFfQSqE@oMtp6yzbzqSmiKJ@1JWqu6YSIKZEjgTUY2BExjzoFuK60Q6ABdXDIVo43Uab3DAXmpLGgf1oJtVvndOzKcAWcpRPV;vOGasQQC8bzhkIyij6G8SGBYimMcziAe4ZMfNudQiu30qaVgP39ZrBaU;riXsbyaBDp5;Hrh@lJGXXMJNZ4Ifx2SI9HtZ6YgOo9Xk59E0UnWmlN0r;ne6@RdDWqfKIgaUqgzYbcGBRzzkJvSilhsKKMxkVAK4esOq;Evr2t@o2;FGRNAV3o5rOYk3kc7;TBiXqvdsr7f30kErJ1P4kHgnxsyyvLJwzfVKzh5eUMz;0oHhcDzDgjeuHkFMdLAgUOvmSp1l6FzOt@Lvpt1@Tgb3dSyg9b98803ETUgyu;@fN@O5rd6z7GASho5U566Q4R9OMRGoH6lSAPR0pLdinWdvLsxVLEsBcwZKFAYgU;3YjbEDzn55v4LtG4jVmjBAMvF4xhRNkHxFKAroevLnZzUiivqWQZGIN7ITqUxhaEC829@jrHdltOotIBD@Oo5zDBc4HeQ@smwhTTIEAZ8STz1zObLLQTDa6r7ZFE8X0NQRwUHuTpcvcqtch2dnHbYzGCOsXhgaEgm6DECAwEAAQ==";
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

        String key = Secure.AES.key();
        LoginModule.LoginParam param = LoginModule.LoginParam.newBuilder()
                .setMobile(Secure.AES.encrypt(key, "15555555555"))
                .setPassword(Secure.AES.encrypt(key, "123456"))
                .build();

        Rn.setInterceptor(chain -> {
            Request req = chain.request().newBuilder()
                    .addHeader("Connection", "Upgrade, HTTP2-Settings")
                    .addHeader("Upgrade", "h2c")
                    .addHeader("token", rsa(publicKey, key))
                    .build();//请求
            return chain.proceed(req);//响应
        });
        Rn.show();
        Rn.sendBytes(url1(), param.toByteArray(), data -> {
            LoginModule.LoginResult result = LoginModule.LoginResult.parseFrom(data);
            contentView.setText(result.getMsg());
            if (result.getCode() == 200) {
                if (Secure.RSA.verify(publicKey, result.getToken(), result.getSign())) {
                    String token = Secure.RSA.decryptPublic(publicKey, result.getToken());
                    LoginModule.LoginResult enRes = LoginModule.LoginResult.newBuilder()
                            .setId(Secure.AES.decrypt(token, result.getId()))
                            .setMobile(Secure.AES.decrypt(token, result.getMobile()))
                            .setUsername(Secure.AES.decrypt(token, result.getUsername()))
                            .build();
                    contentView.setText(enRes.toString());
                } else contentView.setText("无效数据");
            }
        });

    }

}

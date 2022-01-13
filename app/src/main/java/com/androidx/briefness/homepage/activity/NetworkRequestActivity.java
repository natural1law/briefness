package com.androidx.briefness.homepage.activity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.provider.Telephony.Carriers.USER;
import static com.androidx.briefness.base.App.kv;
import static com.androidx.briefness.base.App.toasts;
import static com.androidx.briefness.homepage.service.NotificationService.enqueue;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.androidx.briefness.R;
import com.androidx.briefness.base.BaseActivity;
import com.androidx.briefness.homepage.module.ReceiveModule;
import com.androidx.briefness.homepage.module.SendModule;
import com.androidx.briefness.homepage.service.NotificationService;
import com.androidx.http.use.Rn;
import com.androidx.reduce.tools.Idle;
import com.androidx.reduce.tools.Secure;
import com.androidx.reduce.tools.Storage;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.protobuf.ByteString;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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
    private Unbinder unbinder;
    private String publicKey;

    private static native String url();

    private static native String url1();

    private static native String wsUrl();

    private static native String ios();

    private static native String publicKey();

    @Override
    protected void onCreate() {
        setContentView(R.layout.activity_network);
        unbinder = ButterKnife.bind(aThis);
        titleLayout.setBackgroundColor(getResources().getColor(R.color.gray, getTheme()));
        titleView.setTextColor(getResources().getColor(R.color.black1, getTheme()));
        imageView.setVisibility(View.VISIBLE);
        imageView.setColorFilter(R.color.black);
        titleView.setText(getIntent().getStringExtra(getResources().getString(R.string.title)));

//    Map<String, Object> map = new WeakHashMap<>();
//    map.put("username", "18841280510");
//    enqueue = Rn.initWebSocket("http://192.168.1.92:8082/cmp/push/notification", map)
//        .setLoginCallback(() -> toasts.i("webSocket", "连接成功"))
//        .setMsgCallback((code, msg, data) -> {
//          toasts.i("webSocket1", msg);
//          toasts.i("webSocket2", data);
//        });

//        Map<String, Object> map = new WeakHashMap<>();
//        map.put("id", "123");
//        enqueue = Rn.initWebSocket(wsUrl(), map)
//                .setLoginCallback(() -> toasts.i("webSocket", "连接成功"))
//                .setMsgCallback((code, msg, data) -> publicKey = data.toStringUtf8());

//            initView();
//            KeyPair key = Secure.RSA.keyPair();
//            String k1;
//            toasts.i("公钥", k1 =Secure.RSA.publicKey(key));
//            String k2;
//            toasts.i("私钥", k2 = Secure.RSA.privateKey(key));
//            String v1;
//            toasts.i("私钥加密", v1 = Secure.RSA.encryptPrivate(k2, "http://192.168.1.122:9981/api/user/login.ios"));
//            toasts.i("公钥加密", Secure.RSA.decryptPublic(k1, v1));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
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
        String path1 = Storage.Locality.generatePicturesPath("", "1.jpg");
        Rn.sendUpload("http://192.168.1.92:9981/api/user/upload.android", path1, data -> toasts.setMsg(data).showSuccess());
        Rn.show();
    }

    @OnClick(R.id.network_send1)
    public void send1() {
        Map<String, Object> param = new ConcurrentHashMap<>();
        param.put("userName", "18604900857");
        param.put("password", "LNgz@082");
        param.put("version", "1.1.6");
        Rn.sendMapPost(url(), param, data -> toasts.i("msg", data));
    }

    @OnClick(R.id.network_send2)
    public void send2() {
        String key = Secure.AES.key();
        String rsaKey = Secure.RSA.encryptPublic(publicKey, key);
        JsonObject json = new JsonObject();
        json.addProperty("mobile", "15555555555");
        json.addProperty("pass", "123456");
        String encode = Secure.AES.encrypt(key, json.toString());
        SendModule.Request request = SendModule.Request.newBuilder()
                .setToken(rsaKey)
                .setData(ByteString.copyFromUtf8(encode))
                .build();
        Rn.sendBytes(url1(), request.toByteArray(), data -> {
            ReceiveModule.Result result = ReceiveModule.Result.parseFrom(data);
            toasts.i("msg", result.getData());
            if (result.getCode() == 1) {
                if (Secure.RSA.verify(result.getPuk(), result.getToken(), result.getSign())) {
                    String aesKey = Secure.RSA.decryptPublic(publicKey, result.getToken());
                    String decode = Secure.AES.decrypt(aesKey, result.getData());
                    JsonObject json1 = new Gson().fromJson(decode, JsonObject.class);
                    contentView.setText(json1.toString());
                } else contentView.setText("数据被篡改");
            } else toasts.setMsg(result.getMsg()).showError();
        });

    }

}

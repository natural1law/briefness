package com.androidx.view.screen.config;

import static android.graphics.PixelFormat.RGBA_8888;
import static android.icu.text.IDNA.DEFAULT;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.androidx.reduce.tools.Storage;

import java.util.Objects;
import java.util.UUID;

/**
 * 屏幕配置
 *
 * @date 2021/07/28
 */
public final class ScreenConfig implements Parcelable {

    public static Builder builder() {
        return new Builder();
    }

    /*获取activity对象*/
    private AppCompatActivity activity;
    /*设置屏幕宽度*/
    private final int basicsWidth;
    /*设置屏幕高度*/
    private final int basicsHeight;
    /*设置屏幕密度*/
    private final int basicsDpi;

    /*设置截图储存地址*/
    private final String captureUrl;
    /*设置截图格式*/
    private final int captureFormat;
    /*设置图片最大获取缓存数*/
    private final int captureMaxImages;

    /*设置声音来源*/
    private final int videoAudioSource;
    /*设置视频来源*/
    private final int videoSource;
    /*设置输出视频格式*/
    private final int videoOutputFormat;
    /*设置声音编码*/
    private final int videoAudioEncoder;
    /*设置视频储存地址*/
    private final String videoOutputFile;
    /*设置视频编码*/
    private final int videoEncoder;
    /*设置录制视频的捕获帧速率*/
    private final int videoFrameRate;
    /*设置码率*/
    private final int videoEncodingBitRate;

    public AppCompatActivity getActivity() {
        return activity;
    }

    public int getBasicsWidth() {
        return basicsWidth;
    }

    public int getBasicsHeight() {
        return basicsHeight;
    }

    public int getBasicsDpi() {
        return basicsDpi;
    }

    public String getCaptureUrl() {
        return captureUrl;
    }

    public int getCaptureFormat() {
        return captureFormat;
    }

    public int getCaptureMaxImages() {
        return captureMaxImages;
    }

    public int getVideoAudioSource() {
        return videoAudioSource;
    }

    public int getVideoSource() {
        return videoSource;
    }

    public int getVideoOutputFormat() {
        return videoOutputFormat;
    }

    public int getVideoAudioEncoder() {
        return videoAudioEncoder;
    }

    public String getVideoOutputFile() {
        return videoOutputFile;
    }

    public int getVideoEncoder() {
        return videoEncoder;
    }

    public int getVideoFrameRate() {
        return videoFrameRate;
    }

    public int getVideoEncodingBitRate() {
        return videoEncodingBitRate;
    }

    protected ScreenConfig(Parcel in) {
        activity = in.readParcelable(activity.getClassLoader());
        basicsWidth = in.readInt();
        basicsHeight = in.readInt();
        basicsDpi = in.readInt();
        captureUrl = in.readString();
        captureFormat = in.readInt();
        captureMaxImages = in.readInt();
        videoAudioSource = in.readInt();
        videoSource = in.readInt();
        videoOutputFormat = in.readInt();
        videoAudioEncoder = in.readInt();
        videoOutputFile = in.readString();
        videoEncoder = in.readInt();
        videoFrameRate = in.readInt();
        videoEncodingBitRate = in.readInt();
    }

    private ScreenConfig(Builder builder) {
        this.activity = builder.activity;
        if (activity == null) {
            this.basicsWidth = builder.basicsWidth;
            this.basicsHeight = builder.basicsHeight;
            this.basicsDpi = builder.basicsDpi;
        } else {
            WindowManager windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            windowManager.getDefaultDisplay().getRealMetrics(dm);
            this.basicsWidth = dm.widthPixels;
            this.basicsHeight = dm.heightPixels;
            this.basicsDpi = dm.densityDpi;
        }
        this.captureUrl = builder.captureUrl;
        this.captureFormat = builder.captureFormat;
        this.captureMaxImages = builder.captureMaxImages;
        this.videoAudioSource = builder.videoAudioSource;
        this.videoSource = builder.videoSource;
        this.videoOutputFormat = builder.videoOutputFormat;
        this.videoAudioEncoder = builder.videoAudioEncoder;
        this.videoOutputFile = builder.videoOutputFile;
        this.videoEncoder = builder.videoEncoder;
        this.videoFrameRate = builder.videoFrameRate;
        this.videoEncodingBitRate = builder.videoEncodingBitRate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(activity.getSupportParentActivityIntent(), DEFAULT);
        dest.writeInt(basicsWidth);
        dest.writeInt(basicsHeight);
        dest.writeInt(basicsDpi);
        dest.writeString(captureUrl);
        dest.writeInt(captureFormat);
        dest.writeInt(captureMaxImages);
        dest.writeInt(videoAudioSource);
        dest.writeInt(videoSource);
        dest.writeInt(videoOutputFormat);
        dest.writeInt(videoAudioEncoder);
        dest.writeString(videoOutputFile);
        dest.writeInt(videoEncoder);
        dest.writeInt(videoFrameRate);
        dest.writeInt(videoEncodingBitRate);
    }

    public static final class Builder {

        /*获取activity对象*/
        private AppCompatActivity activity = null;
        /*设置屏幕宽度*/
        private int basicsWidth = 720;
        /*设置屏幕高度*/
        private int basicsHeight = 1280;
        /*设置屏幕密度*/
        private int basicsDpi = 2;

        /*设置截图储存地址*/
        private String captureUrl = picturePath();
        /*设置截图格式*/
        private int captureFormat = RGBA_8888;
        /*设置图片最大获取缓存数*/
        private int captureMaxImages = 10;

        /*设置声音来源*/
        private int videoAudioSource = MediaRecorder.AudioSource.VOICE_COMMUNICATION;
        /*设置视频来源*/
        private int videoSource = MediaRecorder.VideoSource.SURFACE;
        /*设置输出视频格式*/
        private int videoOutputFormat = MediaRecorder.OutputFormat.MPEG_4;
        /*设置声音编码*/
        private int videoAudioEncoder = MediaRecorder.AudioEncoder.HE_AAC;
        /*设置视频储存地址*/
        private String videoOutputFile = videoPath();
        /*设置视频编码*/
        private int videoEncoder = MediaRecorder.VideoEncoder.H264;
        /*设置录制视频的捕获帧速率*/
        private int videoFrameRate = 30;
        /*设置码率*/
        private int videoEncodingBitRate = 2;

        /**
         * 获取activity对象
         */
        public Builder setActivity(AppCompatActivity activity) {
            this.activity = activity;
            return builder;
        }

        /**
         * 设置屏幕宽度
         */
        public Builder setBasicsWidth(int basicsWidth) {
            this.basicsWidth = basicsWidth;
            return builder;
        }

        /**
         * 设置屏幕高度
         */
        public Builder setBasicsHeight(int basicsHeight) {
            this.basicsHeight = basicsHeight;
            return builder;
        }

        /**
         * 设置屏幕密度
         */
        public Builder setBasicsDpi(int basicsDpi) {
            this.basicsDpi = basicsDpi;
            return builder;
        }

        /**
         * 设置截图储存地址
         */
        public Builder setCaptureUrl(String captureUrl) {
            this.captureUrl = captureUrl;
            return builder;
        }

        /**
         * 设置截图格式
         */
        public Builder setCaptureFormat(int captureFormat) {
            this.captureFormat = captureFormat;
            return builder;
        }

        /**
         * 设置图片最大获取缓存数
         */
        public Builder setCaptureMaxImages(int captureMaxImages) {
            this.captureMaxImages = captureMaxImages;
            return builder;
        }

        /**
         * 设置声音来源
         */
        public Builder setVideoAudioSource(int videoAudioSource) {
            this.videoAudioSource = videoAudioSource;
            return builder;
        }

        /**
         * 设置视频来源
         */
        public Builder setVideoSource(int videoSource) {
            this.videoSource = videoSource;
            return builder;
        }

        /**
         * 设置输出视频格式
         */
        public Builder setVideoOutputFormat(int videoOutputFormat) {
            this.videoOutputFormat = videoOutputFormat;
            return builder;
        }

        /**
         * 设置声音编码
         */

        public Builder setVideoAudioEncoder(int videoAudioEncoder) {
            this.videoAudioEncoder = videoAudioEncoder;
            return builder;
        }

        /**
         * 设置视频储存地址
         */
        public Builder setVideoOutputFile(String videoOutputFile) {
            this.videoOutputFile = videoOutputFile;
            return builder;
        }

        /**
         * 设置视频编码
         */
        public Builder setVideoEncoder(int videoEncoder) {
            this.videoEncoder = videoEncoder;
            return builder;
        }

        /**
         * 设置录制视频的捕获帧速率
         */
        public Builder setVideoFrameRate(int videoFrameRate) {
            this.videoFrameRate = videoFrameRate;
            return builder;
        }

        /**
         * 设置码率
         */
        public Builder setVideoEncodingBitRate(int videoEncodingBitRate) {
            this.videoEncodingBitRate = videoEncodingBitRate;
            return builder;
        }

        /**
         * 默认截图地址
         */
        private String picturePath() {
            String uuid = UUID.fromString(UUID.randomUUID().toString()).toString().replace("-", "");
            return Storage.Locality.generatePicturesPath("/" + uuid + ".png");
        }

        /**
         * 默认视频地址
         */
        private String videoPath() {
            String uuid = UUID.fromString(UUID.randomUUID().toString()).toString().replace("-", "");
            return Storage.Locality.generateVideoPath("/" + uuid + ".mp4");
        }

        private final Builder builder = this;

        private Builder() {
        }

        public ScreenConfig build() {
            return new ScreenConfig(builder);
        }
    }

    protected static final Creator<ScreenConfig> CREATOR = new Creator<ScreenConfig>() {
        @Override
        public ScreenConfig createFromParcel(Parcel in) {
            return new ScreenConfig(in);
        }

        @Override
        public ScreenConfig[] newArray(int size) {
            return new ScreenConfig[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScreenConfig)) return false;
        ScreenConfig that = (ScreenConfig) o;
        return getBasicsWidth() == that.getBasicsWidth() &&
                getBasicsHeight() == that.getBasicsHeight() &&
                getBasicsDpi() == that.getBasicsDpi() &&
                getCaptureFormat() == that.getCaptureFormat() &&
                getCaptureMaxImages() == that.getCaptureMaxImages() &&
                getVideoAudioSource() == that.getVideoAudioSource() &&
                getVideoSource() == that.getVideoSource() &&
                getVideoOutputFormat() == that.getVideoOutputFormat() &&
                getVideoAudioEncoder() == that.getVideoAudioEncoder() &&
                getVideoEncoder() == that.getVideoEncoder() &&
                getVideoFrameRate() == that.getVideoFrameRate() &&
                getVideoEncodingBitRate() == that.getVideoEncodingBitRate() &&
                getActivity().equals(that.getActivity()) &&
                getCaptureUrl().equals(that.getCaptureUrl()) &&
                getVideoOutputFile().equals(that.getVideoOutputFile());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getActivity(), getBasicsWidth(), getBasicsHeight(), getBasicsDpi(), getCaptureUrl(), getCaptureFormat(), getCaptureMaxImages(), getVideoAudioSource(), getVideoSource(), getVideoOutputFormat(), getVideoAudioEncoder(), getVideoOutputFile(), getVideoEncoder(), getVideoFrameRate(), getVideoEncodingBitRate());
    }
}

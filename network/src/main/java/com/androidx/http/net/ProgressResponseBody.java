package com.androidx.http.net;

import android.icu.math.BigDecimal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.androidx.http.net.listener.DownloadListener;
import com.androidx.reduce.tools.Convert;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

public class ProgressResponseBody extends ResponseBody {

    //实际的待包装响应体
    private final ResponseBody responseBody;
    //进度回调接口
    private final DownloadListener downloadListener;

    public ProgressResponseBody(ResponseBody responseBody, DownloadListener downloadListener) {
        this.responseBody = responseBody;
        this.downloadListener = downloadListener;
    }

    /**
     * 读取，回调进度接口
     *
     * @param source Source
     * @return Source
     */
    private Source source(Source source) {

        return new ForwardingSource(source) {
            //当前读取字节数
            long totalBytesRead = 0L;

            @Override
            public long read(@NonNull Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                //增加当前读取的字节数，如果读取完成了bytesRead会返回-1
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                //计算百分比进度
                BigDecimal progress = Convert.Scm.set((((totalBytesRead * 1.0 / contentLength()) % 100) * 100), 1);
                if (downloadListener != null) downloadListener.running(progress);
                //回调，如果contentLength()不知道长度，会返回-1
                return bytesRead;
            }
        };
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @NonNull
    @Override
    public BufferedSource source() {
        return Okio.buffer(source(responseBody.source()));
    }

}

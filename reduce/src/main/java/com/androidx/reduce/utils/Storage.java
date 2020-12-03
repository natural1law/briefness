package com.androidx.reduce.utils;

import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.UUID;

import static android.util.Base64.DEFAULT;

/**
 * 存储磁盘
 *
 * @
 */
public final class Storage {

    private static final String MODE = "rw";
    private static final int POSITION = 0;

    private Storage() {
    }

    /**
     * 获取保存地址
     */
    public static String getSaveDirectory(String fileName, String suffix) {
        try {
            String filePath = UUID.randomUUID().toString().replace("-", "");
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String rootDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + fileName + "/";
                File file = new File(rootDir);
                if (!file.exists()) {
                    if (!file.mkdirs()) {
                        return "";
                    }
                }
                return rootDir + filePath + suffix;
            } else {
                return "";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * 数据保存到本地储存地址
     *
     * @param fileName 文件夹名字
     * @param suffix   文件后缀
     * @return 文件保存地址
     */
    private static String getPath(String fileName, String suffix) {
        String filePath = String.valueOf(System.currentTimeMillis());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String rootDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + fileName + "/";
            File file = new File(rootDir);
            if (!file.exists() && !file.mkdirs()) {
                return String.valueOf(false);
            } else {
                return rootDir + filePath + suffix;
            }
        } else {
            return String.valueOf(false);
        }
    }

    /**
     * 将文件保存到本地
     *
     * @param img      base64图片
     * @param fileName 文件夹名字
     * @param suffix   文件后缀
     */
    public static void savePicture(String img, String fileName, String suffix) {
        try {
            RandomAccessFile raf = new RandomAccessFile(getPath(fileName, suffix), MODE);
            FileChannel fileChannel = raf.getChannel();
            byte[] data = Base64.decode(img, DEFAULT);
            for (int i = 0; i < data.length; ++i) {
                if (data[i] < 0) {// 调整异常数据
                    data[i] += 256;
                }
            }
            MappedByteBuffer mbb = fileChannel.map(FileChannel.MapMode.READ_WRITE, POSITION, data.length + 1);
            mbb.put(data);
            mbb.flip();
            mbb.clear();
            fileChannel.close();
            raf.close();
        } catch (Exception e) {
            Log.e("保存图片异常", String.valueOf(e.getMessage()));
        }
    }

}

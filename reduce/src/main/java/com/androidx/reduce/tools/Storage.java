package com.androidx.reduce.tools;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.UUID;

import static android.util.Base64.DEFAULT;
import static java.lang.Boolean.TRUE;

/**
 * 存储工具
 *
 * @
 */
public final class Storage {

    private Storage() {
    }

    /**
     * 生成文件保存url地址
     */
    public static String generatePath(String... files) {
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String rootDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + files[0] + "/";
                File file = new File(rootDir);
                if (!file.exists()) {
                    if (!file.mkdirs()) {
                        return "";
                    }
                }
                switch (files.length) {
                    case 1:
                        return rootDir + UUID.randomUUID().toString().replace("-", "") + ".txt";
                    case 2:
                        return rootDir + UUID.randomUUID().toString().replace("-", "") + files[1];
                    case 3:
                        return rootDir + files[1] + files[2];
                    default:
                        return "";
                }
            } else {
                return "";
            }
        } catch (Exception e) {
            return String.valueOf(e.getMessage());
        }
    }

    /**
     * 本地存储
     */
    public static final class ThisLocality {

        private static final String MODE = "rw";
        private static final int POSITION = 0;

        private ThisLocality() {
        }

        /**
         * 数据保存到本地储存地址
         *
         * @param files [0]-文件夹名字 [1]-文件名 [2]-文件后缀
         * @return 文件保存地址
         */
        public static String save(String... files) {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String rootDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + files[0] + "/";
                File file = new File(rootDir);
                if (!file.exists() && !file.mkdirs()) {
                    return String.valueOf(false);
                } else {
                    return rootDir + files[1] + files[2];
                }
            } else {
                return String.valueOf(false);
            }
        }

        /**
         * 将base64图片以文件形式保存到本地
         *
         * @param img      base64图片
         * @param fileName 文件夹名字
         * @param suffix   文件后缀
         */
        public static void savePicture(String img, String fileName, String suffix) {
            try {
                RandomAccessFile raf = new RandomAccessFile(save(fileName, suffix), MODE);
                FileChannel fileChannel = raf.getChannel();
                byte[] data = Base64.decode(img, DEFAULT);
                // 调整异常数据
                for (int i = 0; i < data.length; ++i) if (data[i] < 0) data[i] += 256;
                FileChannel.MapMode mode = FileChannel.MapMode.READ_WRITE;
                int size = data.length + 1;
                MappedByteBuffer mbb = fileChannel.map(mode, POSITION, size);
                mbb.put(data);
                mbb.flip();
                mbb.clear();
                fileChannel.close();
                raf.close();
            } catch (Exception e) {
                Log.e("保存图片异常", String.valueOf(e.getMessage()));
            }
        }

        @SuppressWarnings("ResultOfMethodCallIgnored")
        public static File generatePath(String... files) {
            try {
                String rootDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
                File file = new File(rootDir, files[0]);
                if (!file.exists()) if (!file.mkdirs()) file.setWritable(TRUE);
                return new File(file.getAbsoluteFile() + "/" + files[1] + files[2]);
            } catch (Exception e) {
                return new File(String.valueOf(e.getMessage()));
            }
        }

        /**
         * 生成存储地址
         *
         * @param param param[0]文件目录 param[1] 文件名 param[2] 后缀
         * @return uri
         */
        @RequiresApi(api = Build.VERSION_CODES.Q)
        public static Uri generatePath(Context c, String... param) {
            ContentResolver contentResolver = c.getContentResolver();
            Uri uri = MediaStore.Downloads.EXTERNAL_CONTENT_URI;
            ContentValues values = new ContentValues();
            String path = Environment.DIRECTORY_DOWNLOADS + "/" + param[0];
            values.put(MediaStore.Downloads.RELATIVE_PATH, path);
            values.put(MediaStore.Downloads.DISPLAY_NAME, param[1] + "." + param[2]);
            values.put(MediaStore.Downloads.TITLE, path);
            return contentResolver.insert(uri, values);
        }

    }

    /**
     * 本地缓存
     */
    public static final class Cache {

        public static <D> void writeFile(Context c, String fileName, D data) {
            try {
                write(c, fileName, data);
            } catch (Exception e) {
                Log.e("本地缓存写入异常", e.getMessage(), e);
            }
        }

        public static String readFile(Context context, String fileName) {
            if (exists(context, fileName)) {
                try {
                    return read(context, fileName);
                } catch (Exception e) {
                    Log.e("本地缓存读取异常", e.getMessage(), e);
                    return null;
                }
            } else {
                return null;
            }
        }

        /**
         * 保存数据
         */
        private static <D> void write(Context c, String f, D d) throws Exception {
            //设置文件名称，以及存储方式
            FileOutputStream out = c.openFileOutput(f, Context.MODE_PRIVATE);
            //创建一个OutputStreamWriter对象，传入BufferedWriter的构造器中
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
            //向文件中写入数据
            if (d instanceof String) {
                writer.write(String.valueOf(d));
            } else if (d instanceof Byte) {
                writer.write((Byte) d);
            } else if (d instanceof Integer) {
                writer.write((Integer) d);
            } else if (d instanceof char[]) {
                writer.write((char[]) d);
            } else {
                Log.w("存储工具", "未匹配到数据类型");
            }
            out.flush();
            writer.close();
            out.close();
        }

        /**
         * 获取数据
         */
        private static String read(Context c, String f) throws Exception {
            FileInputStream in = c.openFileInput(f);
            //设置将要打开的存储文件名称
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) content.append(line);
            reader.close();
            in.close();
            return content.toString();
        }

        /**
         * 检验文件是否存在
         */
        private static boolean exists(Context c, String f) {
            boolean flag = false;
            if (f != null) {
                File file = new File(c.getFilesDir(), f);
                if (file.exists()) flag = file.delete();
            }
            return flag;
        }

    }


}

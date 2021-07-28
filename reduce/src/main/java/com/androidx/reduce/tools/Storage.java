package com.androidx.reduce.tools;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

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
     * 本地存储
     */
    public static final class Locality {

        private static final StringBuilder sb = new StringBuilder();

        private Locality() {

        }

        /**
         *
         */
        public static String generateVideoPath(String... files) {
            try {
                sb.delete(0, sb.length());
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    sb.append(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).getAbsolutePath());
                    for (String s : files) if (!s.contentEquals(".")) sb.append("/").append(s);
                    Path path = Paths.get(sb.substring(sb.indexOf("/"), sb.indexOf(".") - 1));
                    File file = Paths.get(path.getParent().toString()).toFile();
                    if (!file.exists()) {
                        if (!file.mkdirs()) System.out.println(file.setWritable(TRUE));
                    }
                } else {
                    sb.append(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).getAbsolutePath());
                    for (String s : files) if (!s.contentEquals(".")) sb.append("/").append(s);
                    File file = new File(sb.substring(sb.indexOf("/"), sb.indexOf(".") - 1));
                    if (!file.exists()) {
                        if (!file.mkdirs()) System.out.println(file.setWritable(TRUE));
                    }
                }
                return sb.toString();
            } catch (Exception e) {
                return Log.getStackTraceString(e);
            }
        }
        /**
         *
         */
        public static String generatePicturesPath(String... files) {
            try {
                sb.delete(0, sb.length());
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    sb.append(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath());
                    for (String s : files) if (!s.contentEquals(".")) sb.append("/").append(s);
                    Path path = Paths.get(sb.substring(sb.indexOf("/"), sb.indexOf(".") - 1));
                    File file = Paths.get(path.getParent().toString()).toFile();
                    if (!file.exists()) {
                        if (!file.mkdirs()) System.out.println(file.setWritable(TRUE));
                    }
                } else {
                    sb.append(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath());
                    for (String s : files) if (!s.contentEquals(".")) sb.append("/").append(s);
                    File file = new File(sb.substring(sb.indexOf("/"), sb.indexOf(".") - 1));
                    if (!file.exists()) {
                        if (!file.mkdirs()) System.out.println(file.setWritable(TRUE));
                    }
                }
                return sb.toString();
            } catch (Exception e) {
                return Log.getStackTraceString(e);
            }
        }
        /**
         *
         */
        public static String generateDownloadPath(String... files) {
            try {
                sb.delete(0, sb.length());
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    sb.append(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
                    for (String s : files) if (!s.contentEquals(".")) sb.append("/").append(s);
                    Path path = Paths.get(sb.substring(sb.indexOf("/"), sb.indexOf(".") - 1));
                    File file = Paths.get(path.getParent().toString()).toFile();
                    if (!file.exists()) {
                        if (!file.mkdirs()) System.out.println(file.setWritable(TRUE));
                    }
                } else {
                    sb.append(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
                    for (String s : files) if (!s.contentEquals(".")) sb.append("/").append(s);
                    File file = new File(sb.substring(sb.indexOf("/"), sb.indexOf(".") - 1));
                    if (!file.exists()) {
                        if (!file.mkdirs()) System.out.println(file.setWritable(TRUE));
                    }
                }
                return sb.toString();
            } catch (Exception e) {
                return Log.getStackTraceString(e);
            }
        }
        /**
         *
         */
        public static String generateMusicPath(String... files) {
            try {
                sb.delete(0, sb.length());
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    sb.append(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath());
                    for (String s : files) if (!s.contentEquals(".")) sb.append("/").append(s);
                    Path path = Paths.get(sb.substring(sb.indexOf("/"), sb.indexOf(".") - 1));
                    File file = Paths.get(path.getParent().toString()).toFile();
                    if (!file.exists()) {
                        if (!file.mkdirs()) System.out.println(file.setWritable(TRUE));
                    }
                } else {
                    sb.append(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath());
                    for (String s : files) if (!s.contentEquals(".")) sb.append("/").append(s);
                    File file = new File(sb.substring(sb.indexOf("/"), sb.indexOf(".") - 1));
                    if (!file.exists()) {
                        if (!file.mkdirs()) System.out.println(file.setWritable(TRUE));
                    }
                }
                return sb.toString();
            } catch (Exception e) {
                return Log.getStackTraceString(e);
            }
        }

        /**
         * 生成存储地址(下载空间)
         *
         * @param param param[0]文件目录 param[1] 文件名 param[2] 后缀
         * @return uri
         */
        @RequiresApi(api = Build.VERSION_CODES.Q)
        public static Uri generateDownLoadPath(Context c, String... param) {
            ContentResolver cr = c.getContentResolver();
            Uri uri = MediaStore.Downloads.EXTERNAL_CONTENT_URI;
            ContentValues values = new ContentValues();
            String path = Environment.DIRECTORY_DOWNLOADS + "/" + param[0];
            values.put(MediaStore.Downloads.RELATIVE_PATH, path);
            values.put(MediaStore.Downloads.DISPLAY_NAME, param[1] + "." + param[2]);
            values.put(MediaStore.Downloads.TITLE, path);
            return cr.insert(uri, values);
        }

        /**
         * 生成存储地址(图片空间)
         *
         * @param param param[0]文件目录 param[1] 文件名 param[2] 后缀
         * @return uri
         */
        @RequiresApi(api = Build.VERSION_CODES.Q)
        public static Uri generatePicturesPath(Context c, String... param) {
            ContentResolver cr = c.getContentResolver();
            Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            ContentValues values = new ContentValues();
            String path = Environment.DIRECTORY_PICTURES + "/" + param[0];
            values.put(MediaStore.Images.Media.RELATIVE_PATH, path);
            values.put(MediaStore.Images.Media.DISPLAY_NAME, param[1] + "." + param[2]);
            values.put(MediaStore.Images.Media.TITLE, path);
            return cr.insert(uri, values);
        }

        /**
         * 生成存储地址(截屏空间)
         *
         * @param param param[0]文件目录 param[1] 文件名 param[2] 后缀
         * @return uri
         */
        @RequiresApi(api = Build.VERSION_CODES.Q)
        public static Uri generateScreenshotsPath(Context c, String... param) {
            ContentResolver cr = c.getContentResolver();
            Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            ContentValues values = new ContentValues();
            String path = Environment.DIRECTORY_SCREENSHOTS + "/" + param[0];
            values.put(MediaStore.Images.Media.RELATIVE_PATH, path);
            values.put(MediaStore.Images.Media.DISPLAY_NAME, param[1] + "." + param[2]);
            values.put(MediaStore.Images.Media.TITLE, path);
            return cr.insert(uri, values);
        }

        /**
         * 生成存储地址(视频空间)
         *
         * @param param param[0]文件目录 param[1] 文件名 param[2] 后缀
         * @return uri
         */
        @RequiresApi(api = Build.VERSION_CODES.Q)
        public static Uri generateVideoPath(Context c, String... param) {
            ContentResolver cr = c.getContentResolver();
            Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            ContentValues values = new ContentValues();
            String path = Environment.DIRECTORY_MOVIES + "/" + param[0];
            values.put(MediaStore.Video.Media.RELATIVE_PATH, path);
            values.put(MediaStore.Video.Media.DISPLAY_NAME, param[1] + "." + param[2]);
            values.put(MediaStore.Video.Media.TITLE, path);
            return cr.insert(uri, values);
        }

        /**
         * 生成存储地址(音频空间)
         *
         * @param param param[0]文件目录 param[1] 文件名 param[2] 后缀
         * @return uri
         */
        @RequiresApi(api = Build.VERSION_CODES.Q)
        public static Uri generateAudioPath(Context c, String... param) {
            ContentResolver cr = c.getContentResolver();
            Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            ContentValues values = new ContentValues();
            String path = Environment.DIRECTORY_MUSIC + "/" + param[0];
            values.put(MediaStore.Audio.Media.RELATIVE_PATH, path);
            values.put(MediaStore.Audio.Media.DISPLAY_NAME, param[1] + "." + param[2]);
            values.put(MediaStore.Audio.Media.TITLE, path);
            return cr.insert(uri, values);
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
                Log.e("本地缓存写入异常", Log.getStackTraceString(e));
            }
        }

        public static String readFile(Context context, String fileName) {
            if (exists(context, fileName)) {
                try {
                    return read(context, fileName);
                } catch (Exception e) {
                    Log.e("本地缓存读取异常", Log.getStackTraceString(e));
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

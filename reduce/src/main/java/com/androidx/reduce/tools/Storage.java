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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * 存储工具
 *
 * @
 */
public final class Storage {

    private Storage() {
    }

    /**
     * 保存文件
     *
     * @param outPath 输出地址(保存在手机中的地址)
     * @param is      文件流
     */
    public static File write(String outPath, InputStream is) {
        try {
            BufferedInputStream bis = new BufferedInputStream(is);
            FileOutputStream fos = new FileOutputStream(outPath);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            int n;
            while ((n = bis.read()) != -1) bos.write(n);
            bos.flush();
            bos.close();
            fos.close();
            bis.close();
            is.close();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) return Paths.get(outPath).toFile();
            else return new File(outPath);
        } catch (Exception e) {
            Log.e(Storage.class.getName(), Log.getStackTraceString(e));
            return null;
        }
    }

    /**
     * 将文件保存到本地存储中
     *
     * @param url  保存地址 (Storage.Locality.generateDownloadPath("log.txt"))
     * @param data 将要保存的数据
     * @param <D>  消息类型
     */
    public static <D> boolean write(String url, D data) {
        try {
            FileOutputStream fos = new FileOutputStream(url);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter writer = new BufferedWriter(osw);
            if (data instanceof char[]) {
                char[] parseData = (char[]) data;
                writer.write(parseData, 0, parseData.length);
            } else {
                String parseData = String.valueOf(data);
                writer.write(parseData, 0, parseData.length());
            }
            writer.flush();
            writer.close();
            osw.close();
            fos.close();
            return true;
        } catch (Exception e) {
            Log.e(Locality.class.getName(), Log.getStackTraceString(e));
            return false;
        }
    }

    /**
     * 读取文件
     */
    public static String read(String url) {
        try {
            String line, content = "";
            BufferedReader reader;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                Path path = Paths.get(url);
                reader = Files.newBufferedReader(path);
            } else {
                FileReader fr = new FileReader(url);
                reader = new BufferedReader(fr);
                fr.close();
            }
            while ((line = reader.readLine()) != null) {
                if (line.length() > 0) content = line;
            }
            reader.close();
            return content;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 加密保存文件
     */
    public static <D> boolean writeEncode(String url, D data) {
        String enData = Secure.Base64.encode(String.valueOf(data));
        return write(url, Secure.Base64.encode(enData));
    }

    /**
     * 解密获取文件
     */
    public static String readDecode(String url) {
        String data = Secure.Base64.decode(read(url));
        return Secure.Base64.decode(data);
    }

    /**
     * 本地缓存
     */
    public static final class Cache {

        public static <D> boolean write(Context c, String fileName, D data) {
            return saveWrite(c, fileName, data);
        }

        public static String read(Context context, String fileName) {
            if (exists(context, fileName)) return saveRead(context, fileName);
            else return null;
        }

        /**
         * 保存数据
         */
        private static <D> boolean saveWrite(Context c, String fileName, D data) {
            try {
                //设置文件名称，以及存储方式
                FileOutputStream out = c.openFileOutput(fileName, Context.MODE_PRIVATE);
                OutputStreamWriter osw = new OutputStreamWriter(out);
                BufferedWriter writer = new BufferedWriter(osw);
                if (data instanceof char[]) {
                    char[] parseData = (char[]) data;
                    writer.write(parseData, 0, parseData.length);
                } else if (data instanceof String) {
                    String parseData = String.valueOf(data);
                    writer.write(parseData, 0, parseData.length());
                } else {
                    Log.e(Locality.class.getName(), "数据类型不匹配");
                    return false;
                }
                writer.flush();
                writer.close();
                osw.close();
                out.close();
                return true;
            } catch (Exception e) {
                Log.e(Locality.class.getName(), Log.getStackTraceString(e));
                return false;
            }
        }

        /**
         * 获取数据
         */
        private static String saveRead(Context c, String f) {
            try {
                FileInputStream in = c.openFileInput(f);
                //设置将要打开的存储文件名称
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) content.append(line);
                reader.close();
                in.close();
                return content.toString();
            } catch (Exception e) {
                Log.e(Storage.class.getName(), Log.getStackTraceString(e));
                return null;
            }
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
                String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).getAbsolutePath();
                if (files == null || files.length == 0) return defaultValue(root, ".mp4");
                else return baseGenerate(root, files);
            } catch (Exception e) {
                return Log.getStackTraceString(e);
            }
        }

        /**
         *
         */
        public static String generateMusicPath(String... files) {
            try {
                String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath();
                if (files == null || files.length == 0) return defaultValue(root, ".mp3");
                else return baseGenerate(root, files);
            } catch (Exception e) {
                return Log.getStackTraceString(e);
            }
        }

        /**
         *
         */
        public static String generateDocumentsPath(String... files) {
            try {
                String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath();
                if (files == null || files.length == 0) return defaultValue(root, ".txt");
                else return baseGenerate(root, files);
            } catch (Exception e) {
                return Log.getStackTraceString(e);
            }
        }

        /**
         *
         */
        public static String generateDownloadPath(String... files) {
            try {
                String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
                if (files == null || files.length == 0) return defaultValue(root, ".txt");
                else return baseGenerate(root, files);
            } catch (Exception e) {
                return Log.getStackTraceString(e);
            }
        }

        public static String generateDCIMPath(String... files) {
            try {
                String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
                if (files == null || files.length == 0) return defaultValue(root, ".jpg");
                else return baseGenerate(root, files);
            } catch (Exception e) {
                return Log.getStackTraceString(e);
            }
        }

        /**
         *
         */
        public static String generatePicturesPath(String... files) {
            try {
                String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
                if (files == null || files.length == 0) return defaultValue(root, ".png");
                else return baseGenerate(root, files);
            } catch (Exception e) {
                return Log.getStackTraceString(e);
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.Q)
        public static String generateScreenshotsPath(String... files) {
            try {
                String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_SCREENSHOTS).getAbsolutePath();
                if (files == null || files.length == 0) return defaultValue(root, ".jpg");
                else return baseGenerate(root, files);
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
            String path = Environment.DIRECTORY_DOWNLOADS + "/" + param[0];
            return baseGenerateUri(c, path, param);
        }

        /**
         * 生成存储地址(图片空间)
         *
         * @param param param[0]文件目录 param[1] 文件名 param[2] 后缀
         * @return uri
         */
        @RequiresApi(api = Build.VERSION_CODES.Q)
        public static Uri generatePicturesPath(Context c, String... param) {
            String path = Environment.DIRECTORY_PICTURES + "/" + param[0];
            return baseGenerateUri(c, path, param);
        }

        /**
         * 生成存储地址(截屏空间)
         *
         * @param param param[0]文件目录 param[1] 文件名 param[2] 后缀
         * @return uri
         */
        @RequiresApi(api = Build.VERSION_CODES.Q)
        public static Uri generateScreenshotsPath(Context c, String... param) {
            String path = Environment.DIRECTORY_SCREENSHOTS + "/" + param[0];
            return baseGenerateUri(c, path, param);
        }

        /**
         * 生成存储地址(视频空间)
         *
         * @param param param[0]文件目录 param[1] 文件名 param[2] 后缀
         * @return uri
         */
        @RequiresApi(api = Build.VERSION_CODES.Q)
        public static Uri generateVideoPath(Context c, String... param) {
            String path = Environment.DIRECTORY_MOVIES + "/" + param[0];
            return baseGenerateUri(c, path, param);
        }

        /**
         * 生成存储地址(音频空间)
         *
         * @param param param[0]文件目录 param[1] 文件名 param[2] 后缀
         * @return uri
         */
        @RequiresApi(api = Build.VERSION_CODES.Q)
        public static Uri generateAudioPath(Context c, String... param) {
            String path = Environment.DIRECTORY_MUSIC + "/" + param[0];
            return baseGenerateUri(c, path, param);
        }

        private static String defaultValue(String root, String suffix) throws IOException {
            File file;
            String uuid = UUID.randomUUID().toString().replace("-", "");
            String url = root + "/default/" + uuid + suffix;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                Path path = Paths.get(url);
                file = path.toFile();
                File parent = path.getParent().toFile();
                if (!parent.exists()) System.out.println(parent.mkdirs());
            } else {
                file = new File(url);
                File parent = new File(String.valueOf(file.getParent()));
                if (!parent.exists()) System.out.println(parent.mkdirs());
            }
            if (!file.isFile()) System.out.println(file.createNewFile());
            return file.getPath();
        }

        private static String baseGenerate(String root, String... files) throws IOException {
            File file;
            sb.delete(0, sb.length());
            sb.append(root);
            for (String s : files) sb.append(s.contains(".") ? "" : "/").append(s);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                Path path = Paths.get(sb.toString());
                file = path.toFile();
                File parent = path.getParent().toFile();
                if (!parent.exists()) System.out.println(parent.mkdirs());
            } else {
                file = new File(sb.toString());
                File parent = new File(String.valueOf(file.getParent()));
                if (!parent.exists()) System.out.println(parent.mkdirs());
            }
            if (!file.isFile()) System.out.println(file.createNewFile());
            return file.getPath();
        }

        @RequiresApi(api = Build.VERSION_CODES.Q)
        private static Uri baseGenerateUri(Context c, String path, String... param) {
            ContentResolver cr = c.getContentResolver();
            Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            ContentValues values = new ContentValues();
            values.put(MediaStore.Audio.Media.RELATIVE_PATH, path);
            values.put(MediaStore.Audio.Media.DISPLAY_NAME, param[1] + "." + param[2]);
            values.put(MediaStore.Audio.Media.TITLE, path);
            return cr.insert(uri, values);
        }
    }

}

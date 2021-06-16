package com.androidx.reduce.tools;

import android.content.Context;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.UUID;

import static android.util.Base64.DEFAULT;

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
                for (int i = 0; i < data.length; ++i) {
                    if (data[i] < 0) {// 调整异常数据
                        data[i] += 256;
                    }
                }
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
    }

    /**
     * 本地缓存
     */
    public static final class Cache {

        /**
         * 保存数据
         */
        public static <T> void write(Context context, String fileName, T data) {
            FileOutputStream out = null;
            BufferedWriter writer = null;
            try {
                //设置文件名称，以及存储方式
                out = context.openFileOutput(fileName, Context.MODE_PRIVATE);
                //创建一个OutputStreamWriter对象，传入BufferedWriter的构造器中
                writer = new BufferedWriter(new OutputStreamWriter(out));
                //向文件中写入数据
                if (data instanceof String) {
                    writer.write(String.valueOf(data));
                } else if (data instanceof Byte) {
                    writer.write((Byte) data);
                } else if (data instanceof Integer) {
                    writer.write((Integer) data);
                } else if (data instanceof char[]) {
                    writer.write((char[]) data);
                } else {
                    Log.w("存储工具", "未匹配到数据类型");
                }
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (writer != null) {
                        writer.close();
                    }
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * 获取数据
         */
        public static String read(Context context, String fileName) {
            FileInputStream in = null;
            BufferedReader reader = null;
            StringBuilder content = new StringBuilder();
            try {
                //设置将要打开的存储文件名称
                in = context.openFileInput(fileName);
                //FileInputStream -> InputStreamReader ->BufferedReader
                reader = new BufferedReader(new InputStreamReader(in));
                String line;
                //读取每一行数据，并追加到StringBuilder对象中，直到结束
                while ((line = reader.readLine()) != null) {
                    content.append(line);
                }
            } catch (IOException e) {
                return e.getMessage();
            } finally {
                try {
                    if (reader != null) {
                        reader.close();
                    }
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return content.toString();
        }

        /**
         * 检验文件是否存在
         */
        public static boolean isFile(Context context, String fileName) {
            boolean flag = false;
            if (fileName != null) {
                File file = new File(context.getFilesDir(), fileName);
                if (file.exists()) {
                    flag = file.delete();
                }
            }
            return flag;
        }

    }


}

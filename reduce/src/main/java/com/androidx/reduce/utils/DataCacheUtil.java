package com.androidx.reduce.utils;


import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public final class DataCacheUtil {

    private final Context context;

    private DataCacheUtil(Context base) {
        this.context = base;
    }

    public static DataCacheUtil obj(Context context) {
        synchronized (DataCacheUtil.class) {
            return new DataCacheUtil(context);
        }
    }

    public <T> void writeFile(T obj, String fileName) {
        String data = String.valueOf(obj);
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            //设置文件名称，以及存储方式
            out = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            //创建一个OutputStreamWriter对象，传入BufferedWriter的构造器中
            writer = new BufferedWriter(new OutputStreamWriter(out));
            //向文件中写入数据
            writer.write(data);
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

    public String readFile(String fileName) {
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

    public boolean deleteFile(String fileName) {
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

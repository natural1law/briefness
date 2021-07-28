package com.androidx.reduce.tools;

import android.content.ContentResolver;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public final class Excel {

    public static WritableFont arial14font = null;
    public static WritableCellFormat arial14format = null;
    public static WritableFont arial10font = null;
    public static WritableCellFormat arial10format = null;
    public static WritableFont arial12font = null;
    public static WritableCellFormat arial12format = null;

    public final static String UTF8_ENCODING = "UTF-8";

    public static void format() {
        try {
            arial14font = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD);
            arial14font.setColour(jxl.format.Colour.WHITE);
            arial14format = new WritableCellFormat(arial14font);
            arial14format.setAlignment(jxl.format.Alignment.CENTRE);
            arial14format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
            arial14format.setBackground(jxl.format.Colour.WHITE);
            arial10font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
            arial10format = new WritableCellFormat(arial10font);
            arial10format.setAlignment(jxl.format.Alignment.CENTRE);
            arial10format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
            arial10format.setBackground(jxl.format.Colour.WHITE);
            arial12font = new WritableFont(WritableFont.ARIAL, 12);
            arial12format = new WritableCellFormat(arial12font);
            arial12format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
        } catch (WriteException e) {
            Log.e("excel格式异常", Log.getStackTraceString(e));
        }
    }

    public static boolean write(File file, String titleName, List<String> colName, List<Map<String, Object>> objList) throws Exception {
        format();
        InputStream in = new FileInputStream(file);
        Workbook workbook = Workbook.getWorkbook(in);
        WritableWorkbook writeBook = Workbook.createWorkbook(file, workbook);
        WritableSheet sheet = writeBook.createSheet(titleName, 0);
        WorkbookSettings setEncode = new WorkbookSettings();
        setEncode.setEncoding(UTF8_ENCODING);
        sheet.addCell(new Label(0, 0, file.getName(), arial14format));
        AtomicInteger i2 = new AtomicInteger();
        colName.forEach(s -> {
            try {
                sheet.addCell(new Label(i2.get(), 0, s, arial10format));
                i2.getAndIncrement();
            } catch (WriteException e) {
                Log.e("excel写入异常", Log.getStackTraceString(e));
            }
        });
        for (int i = 0; i < objList.size(); i++) {
            List<String> list = new ArrayList<>();
            objList.get(i).forEach((k, v) -> list.add(String.valueOf(v)));
            for (int i1 = 0; i1 < list.size(); i1++) {
                sheet.addCell(new Label(i1, i + 1, list.get(i1), arial12format));
            }
        }
        writeBook.write();
        writeBook.close();
        workbook.close();
        in.close();
        return new File(titleName).exists();
    }

    public static boolean write(Uri uri, String titleName, ContentResolver cr, List<String> colName, List<Map<String, Object>> objList) throws Exception {
        format();
        WritableWorkbook writeBook = Workbook.createWorkbook(cr.openOutputStream(uri, "rw"));
        WorkbookSettings setEncode = new WorkbookSettings();
        setEncode.setEncoding(UTF8_ENCODING);
        WritableSheet sheet = writeBook.createSheet(titleName, 0);
        sheet.addCell(new Label(0, 0, titleName, arial14format));
        AtomicInteger i = new AtomicInteger();
        colName.forEach(s -> {
            try {
                sheet.addCell(new Label(i.get(), 0, s, arial10format));
                i.getAndIncrement();
            } catch (WriteException e) {
                Log.e("excel写入异常", Log.getStackTraceString(e));
            }
        });
        for (int i1 = 0; i1 < objList.size(); i1++) {
            List<String> list = new ArrayList<>();
            objList.get(i1).forEach((k, v) -> list.add(String.valueOf(v)));
            for (int i2 = 0; i2 < list.size(); i2++) {
                sheet.addCell(new Label(i2, i1 + 1, list.get(i2), arial12format));
            }
        }
        writeBook.write();
        writeBook.close();
        return new File(titleName).exists();
    }

}

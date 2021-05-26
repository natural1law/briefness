package com.androidx.reduce.statics;

import android.Manifest;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_WIFI_STATE;
import static android.Manifest.permission.ADD_VOICEMAIL;
import static android.Manifest.permission.BLUETOOTH_ADMIN;
import static android.Manifest.permission.BLUETOOTH_PRIVILEGED;
import static android.Manifest.permission.BODY_SENSORS;
import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.CAPTURE_AUDIO_OUTPUT;
import static android.Manifest.permission.CHANGE_WIFI_STATE;
import static android.Manifest.permission.GET_ACCOUNTS;
import static android.Manifest.permission.MODIFY_AUDIO_SETTINGS;
import static android.Manifest.permission.PROCESS_OUTGOING_CALLS;
import static android.Manifest.permission.READ_CALENDAR;
import static android.Manifest.permission.READ_CALL_LOG;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.READ_SMS;
import static android.Manifest.permission.RECEIVE_MMS;
import static android.Manifest.permission.RECEIVE_SMS;
import static android.Manifest.permission.RECEIVE_WAP_PUSH;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS;
import static android.Manifest.permission.SEND_SMS;
import static android.Manifest.permission.USE_SIP;
import static android.Manifest.permission.WRITE_CALENDAR;
import static android.Manifest.permission.WRITE_CALL_LOG;
import static android.Manifest.permission.WRITE_CONTACTS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * @className: Permission
 * @classDescription:权限定义类
 * @author: Pan_
 * @createTime: 2018/10/26
 */
public final class PermissionParam {

    /**
     * 日历权限
     */
    public static final String[] CALENDAR = {
            READ_CALENDAR,
            WRITE_CALENDAR
    };
    /**
     * 相机权限
     */
    public static final String[] CAMERA = {
            Manifest.permission.CAMERA
    };
    /**
     * NFC权限
     */
    public static final String[] NFC = {
            Manifest.permission.NFC
    };
    /**
     * 联系人权限
     */
    public static final String[] CONTACTS = {
            READ_CONTACTS,
            WRITE_CONTACTS,
            GET_ACCOUNTS
    };
    /**
     * 定位权限
     */
    public static final String[] LOCATION = {
            ACCESS_FINE_LOCATION,
            ACCESS_COARSE_LOCATION,
            ACCESS_WIFI_STATE,
            CHANGE_WIFI_STATE,
    };
    /**
     * 麦克风权限
     */
    public static final String[] MICROPHONE = {
            RECORD_AUDIO,
            CAPTURE_AUDIO_OUTPUT,
            MODIFY_AUDIO_SETTINGS};
    /**
     * 电话状态权限
     */
    public static final String[] PHONE = {
            READ_PHONE_STATE,
            CALL_PHONE,
            READ_CALL_LOG,
            WRITE_CALL_LOG,
            ADD_VOICEMAIL,
            USE_SIP,
            PROCESS_OUTGOING_CALLS};
    /**
     * 传感器权限
     */
    public static final String[] SENSORS = {BODY_SENSORS};
    /**
     * 短信权限
     */
    public static final String[] SMS = {
            SEND_SMS,
            RECEIVE_SMS,
            READ_SMS,
            RECEIVE_WAP_PUSH,
            RECEIVE_MMS
    };
    /**
     * 存储权限
     */
    public static final String[] STORAGE = {
            READ_EXTERNAL_STORAGE,
            WRITE_EXTERNAL_STORAGE
    };

    /**
     * 蓝牙权限
     */
    public static final String[] BLUETOOTH = {
            Manifest.permission.BLUETOOTH,
            BLUETOOTH_ADMIN,
            BLUETOOTH_PRIVILEGED
    };

    /**
     * 后台运行白名单权限
     */
    public static final String[] WHITELIST = {
            REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
    };

    private PermissionParam() {
    }
}


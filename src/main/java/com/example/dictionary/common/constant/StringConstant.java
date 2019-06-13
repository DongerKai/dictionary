package com.example.dictionary.common.constant;

import com.example.dictionary.base.api.ApiState;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class StringConstant {
    private StringConstant(){}

    /*======================================常量=================================*/
    public static final String DATE_FORMAT_01 = "yyyy-HH-dd HH:mm:ss";
    public static final String GMT_8 = "GMT+8";
    public static final String CONNECTOR = "-";
    public static final String CHARSET_UTF_8 = "UTF-8";
    public static final String USER_LIST = "用户列表";
    public static final String BLANK = "";
    public static final String DOT = ".";
    public static final String SLASH = "/";

    /*=====================================正则匹配==============================*/
    public static final String P_SPECIAL_CHARACTER = "[%./\\\\?*:|<>$@]";//特殊字符
    public static final String P_MOBILE_PHONE = "^[1][0-9]{10}$";//简易手机号匹配，1开头11位数字
    public static final String P_MOBILE_PHONE_COMPLEX = "^(0|86|17951)?(13[0-9]|15[012356789]|166|17[3678]|18[0-9]|14[57])[0-9]{8}$";//严格手机号匹配
    public static final String P_BOOLEAN_STR = "^[0-1]{1}$";//取值范围0-1，位数1
    public static final String P_IP = "^((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))$";//匹配IP
    public static final String P_PORT = "^([1-9][0-9]{0,3}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]{1}|6553[0-5])$";//匹配端口号0-65535
    public static final String P_CHINESE_ENGLISH_NUMBER = "^[\u4e00-\u9fa5a-zA-Z0-9]+$";//匹配中文、字母和数字
    public static final String P_EMAIL = "^[a-zA-Z0-9_]+@[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)+$";//匹配eMail
    public static final String P_NATURAL_NUMBER = "^[0-9]+$";//自然数
    public static final String P_FILE_NAME = ".*\\.";


    /*==================================自定义错误===============================*/
    @Getter
    @AllArgsConstructor
    public enum State implements ApiState{
        DOWNLOAD_FILE_PATH_WRONG(2002, false, "文件下载地址有误"),
        FILE_NAME_EXIST_FAIL(2001, false, "原始文件名丢失！");

        private int code;
        private boolean status;
        private String message;
    }
}

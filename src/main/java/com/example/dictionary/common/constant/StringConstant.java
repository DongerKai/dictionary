package com.example.dictionary.common.constant;

public class StringConstant {
    private StringConstant(){}

    /*======================================常量=================================*/
    public static final String DATE_FORMAT_01 = "yyyy-HH-dd HH:mm:ss";
    public static final String GMT_8 = "GMT+8";
    public static final String CONNECTOR = "-";

    /*=====================================正则匹配==============================*/
    public static final String P_SPECIAL_CHARACTER = "[%./\\\\?*:|<>$@]";//特殊字符
    public static final String P_MOBILE_PHONE = "^[1][0-9]{10}$";
    public static final String P_SMS_V_CODE = "^[0-9]{6}$";
    public static final String P_BOOLEAN_STR = "^[0-1]{1}$";
    public static final String P_FEEDBACK_POINT = "^[0-2]{1}$";
    public static final String P_FEEDBACK_STATUS = "^[0-3]{1}$";
    public static final String P_IP = "^((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))$";
    public static final String P_PORT = "^([1-9][0-9]{0,3}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]{1}|6553[0-5])$";
    public static final String P_CHINESE_ENGLISH_NUMBER = "^[\u4e00-\u9fa5a-zA-Z0-9]+$";
    public static final String P_ENGLISH_NUMBER = "^[a-zA-Z0-9]+$";
    public static final String P_EMAIL = "^[a-zA-Z0-9_]+@[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)+$";
    public static final String P_ALARM_VALUE = "^([1-9][0-9]*)+(.[0-9]*)?$";//"([0-9]{1,}[.][0-9]*)$"
    public static final String P_ALARM_TYPE = "^[01]$";
    public static final String P_DATA_TYPE = "^[012]$";
    public static final String P_NATURAL_NUMBER = "^[0-9]+$";
}

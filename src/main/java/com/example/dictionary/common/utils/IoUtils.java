package com.example.dictionary.common.utils;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * 文件输出
 *
 * @author DongerKai
 * @since 2019/6/12 17:22 ，1.0
 **/
public class IoUtils {
    private IoUtils(){}
    private static final int DEFAULT_BUFFER_SIZE = 8192;
    public static void pipe(InputStream in, OutputStream out)throws Exception{
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        int read;
        while ((read = in.read(buffer, 0, DEFAULT_BUFFER_SIZE)) >= 0){
            out.write(buffer, 0, read);
        }
    }
}

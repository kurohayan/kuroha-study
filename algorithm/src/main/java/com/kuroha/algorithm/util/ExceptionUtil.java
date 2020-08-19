package com.kuroha.algorithm.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil {
    /**
     *
     * @param e
     * @return
     */
    public static String getGromException(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw, true));
        return DateUtil.getTime(0,"yyyy-MM-dd HH:mm:ss") + "\r\n" + sw.toString();
    }

}

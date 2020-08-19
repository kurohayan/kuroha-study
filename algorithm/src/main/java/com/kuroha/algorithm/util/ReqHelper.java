package com.kuroha.algorithm.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * @author kuroha
 */
public class ReqHelper {
	public static String getAllParameters(HttpServletRequest request) {
		String str = "";
		Enumeration enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			String paraName = (String) enu.nextElement();
			str += paraName + ": " + request.getParameter(paraName);
		}
		return str;
	}
	
	
    public static String getParameterStr(HttpServletRequest request, String key, String defVal) {
        String val = request.getParameter(key);
        if (val == null || val.isEmpty()) {
            return defVal;
        }
        return val.trim();
    }

    public static String getParameterStr(HttpServletRequest request, String key) {
        return getParameterStr(request, key, null);
    }

    public static Long getParameterLong(HttpServletRequest request, String key, Long defVal) {
        String val = getParameterStr(request, key);
        if (val == null || !val.matches("^[-+]?[0-9]+$")) {
            return defVal;
        }
        return Long.parseLong(val);
    }

    public static Long getParameterLong(HttpServletRequest request, String key) {
        Long res = getParameterLong(request, key, null);
        return res;
    }

    public static Integer getParameterInt(HttpServletRequest request, String key, Integer defVal) {
        Long res = getParameterLong(request, key, null);
        if (res == null) {
            return defVal;
        }
        return res.intValue();
    }

    public static Integer getParameterInt(HttpServletRequest request, String key) {
        return getParameterInt(request, key, null);
    }

    public static Float getParameterFloat(HttpServletRequest request, String key, Float defVal) {
        String val = getParameterStr(request, key);
        if (val == null || !val.matches("^[-+]?[0-9]+(\\.[0-9]+)?$")) {
            return defVal;
        }
        return Float.parseFloat(val);
    }

    public static Float getParameterFloat(HttpServletRequest request, String key) {
        return getParameterFloat(request, key, null);
    }

    public static void respondText(HttpServletResponse response, String text) {
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(text);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void redirect(HttpServletRequest request, HttpServletResponse response, String relativeUrl) {
        response.setStatus(302);
        String targetUrl = request.getContextPath();
        if (targetUrl.isEmpty()) {
            targetUrl = "/";
        }
        targetUrl += relativeUrl;

        response.addHeader("Location", targetUrl);
    }

    public static void redirect(HttpServletRequest request, HttpServletResponse response) {
        redirect(request, response, "");
    }

    /**
     * 获取请求页面
     *
     * @return String
     * @author SunC 2016年5月25日 下午3:05:26
     */
    public static String getRequestPage(HttpServletRequest request) {

        String referer = request.getHeader("referer");

        if (referer != null) {
            String url = request.getRequestURL().toString();
            String uri = request.getRequestURI();
            String localName = url.substring(0, url.indexOf(uri));
            String tpage = referer.replace(localName, "");
            int index = tpage.indexOf("?");
            if (index > 0) {
                String page = tpage.substring(0, index);
                return page;
            } else {
                return tpage;
            }
        } else {
            return request.getRequestURI();
        }
    }

    /**
     * 获取请求IP
     *
     * @return String
     * @author SunC 2016年5月25日 下午3:05:07
     */
    public static String getRequestIp(HttpServletRequest request) {

        String ipAddress = request.getRemoteAddr();

        if (ipAddress == null || ipAddress.length() == 0) {
            return "";
        } else {
            return ipAddress;
        }
    }
}

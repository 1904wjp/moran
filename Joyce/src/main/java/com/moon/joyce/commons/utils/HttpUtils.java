package com.moon.joyce.commons.utils;

import com.github.kevinsawicki.http.HttpRequest;
import com.moon.joyce.commons.constants.Constant;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Author: XingDaoRong
 * @Date: 2021/12/28
 */
public class HttpUtils {
    private HttpUtils(){}
    /*
      设置浏览器下载响应头
   */
    static void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(),"ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * get请求
     * @param url
     * @return
     * @throws HttpRequest.HttpRequestException
     */
    public static HttpRequest get(final URL url) throws HttpRequest.HttpRequestException {
        return new HttpRequest(url, "GET");
    }
    /**
     * get请求
     * @param url
     * @return
     * @throws HttpRequest.HttpRequestException
     */
    public static HttpRequest get(String url) throws HttpRequest.HttpRequestException, MalformedURLException {
        return get(new URL(url));
    }
}

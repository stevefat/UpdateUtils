package com.stevefat.updateutilslib.http;
/**
 * Created by stevefat on 17-10-14.
 */

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Author : stevefat
 * Email :ngh8897@gmail.com
 * Created : 17-10-14 上午8:31
 */
public class HttpServer implements IHttpServer {


    IHttpListener httpListener;
    String downurl;
    HttpURLConnection urlConnection;

    @Override
    public void setUrl(String url) {
        this.downurl = url;
    }

    @Override
    public void excute() {
        httpUrlcontentPost();
    }

    @Override
    public void setHttpListenter(IHttpListener iHttpListener) {
        this.httpListener = iHttpListener;
    }


    public void httpUrlcontentPost() {
        URL url = null;
        try {
            url = new URL(downurl);

            urlConnection = (HttpURLConnection) url.openConnection();  //打开http连接
            urlConnection.setConnectTimeout(6000);  //设置连接超时时间

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = urlConnection.getInputStream();
                httpListener.onSuccess(inputStream,urlConnection.getContentLength());
            }


        } catch (Exception e) {
            e.printStackTrace();
            httpListener.onFail();
        } finally {
            urlConnection.disconnect(); // 使用完毕关闭连接释放资源
        }
    }


}

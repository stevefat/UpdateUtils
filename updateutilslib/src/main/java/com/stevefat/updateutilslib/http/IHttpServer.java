package com.stevefat.updateutilslib.http;
/**
 * Created by stevefat on 17-10-13.
 */

/**
 * Author : stevefat
 * Email :ngh8897@gmail.com
 * Created : 17-10-13 上午11:11
 */
public interface IHttpServer {

    void setUrl(String url);

    void excute();

    void setHttpListenter(IHttpListener iHttpListener);
}

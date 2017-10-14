package com.stevefat.updateutilslib.http;
/**
 * Created by stevefat on 17-10-14.
 */

/**
 * Author : stevefat
 * Email :ngh8897@gmail.com
 * Created : 17-10-14 上午9:42
 */
public class HttpTask implements Runnable {

    IHttpServer httpServer;

    public <T> HttpTask(String url, IHttpServer httpService, IHttpListener httpListener) {
        this.httpServer = httpService;
        httpService.setUrl(url);
        httpService.setHttpListenter(httpListener);
    }


    @Override
    public void run() {
        httpServer.excute();
    }
}

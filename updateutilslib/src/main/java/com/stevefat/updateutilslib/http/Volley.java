package com.stevefat.updateutilslib.http;
/**
 * Created by stevefat on 17-10-14.
 */

import java.util.concurrent.FutureTask;

/**
 * Author : stevefat
 * Email :ngh8897@gmail.com
 * Created : 17-10-14 上午9:45
 */
public class Volley {

    public static void downFile(String url, IHttpListener httpListener) {
        IHttpServer httpService = new HttpServer();

        HttpTask task = new HttpTask(url, httpService, httpListener);

        //将请求放到请求队列中
        ThreadPollManager.getInstance().add(new FutureTask<Object>(task, null));

    }
}

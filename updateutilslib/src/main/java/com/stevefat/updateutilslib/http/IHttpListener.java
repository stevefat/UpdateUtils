package com.stevefat.updateutilslib.http;
/**
 * Created by stevefat on 17-10-13.
 */

import java.io.InputStream;

/**
 * Author : stevefat
 * Email :ngh8897@gmail.com
 * Created : 17-10-13 上午11:12
 */
public interface IHttpListener {

    void onSuccess(InputStream inputStream, int total);

    void onFail();
}

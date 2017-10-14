package com.stevefat.updateutils;
/**
 * Created by stevefat on 17-10-11.
 */

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Author : stevefat
 * Email :ngh8897@gmail.com
 * Created : 17-10-11 上午9:34
 */
public interface APIServer {
    public static String BASE_URL = "http://book.gisroad.com/";   //正式

    @GET("webservice/webserviceandroid30.ashx")
    Observable<SoftUpdate> updateSoft(@Query("dt") String dt, @Query("act") String act, @Query("validate") String validate, @Query("softver") String softver);


}

package com.stevefat.updateutils;
/**
 * Created by stevefat on 17-10-11.
 */

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author : stevefat
 * Email :ngh8897@gmail.com
 * Created : 17-10-11 上午9:34
 */
public class ApiClient {

    private static ApiClient instance;
    //网络接口服务提供
    private APIServer apiServer;
    private long DEFAULT_TIMEOUT =10;

    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();


    public static ApiClient getInstance() {
        if (instance == null) {
            instance = new ApiClient();
        }
        return instance;
    }


    public ApiClient() {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIServer.BASE_URL)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .client(client)
                .build();


        this.apiServer = retrofit.create(APIServer.class);
    }

    /**
     * 获取提供服务的接口
     *
     * @return
     */
    public APIServer getApiServer() {
        return apiServer;
    }
}

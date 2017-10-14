package com.stevefat.updateutils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.stevefat.updateutilslib.dialog.MaterialDialogUtils;
import com.stevefat.updateutilslib.http.IHttpListener;
import com.stevefat.updateutilslib.http.Volley;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    MaterialDialogUtils materialDialogUtils;
    public String SD_FOLDER;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x01:
                    Toast.makeText(MainActivity.this, "最新版本了", Toast.LENGTH_SHORT).show();
                    break;
                case 0x02:
                    SoftUpdate.SoftupdateBean softupdateBean = (SoftUpdate.SoftupdateBean) msg.obj;

                    materialDialogUtils.showUpdate(softupdateBean.getVersion(), softupdateBean.getContent(), softupdateBean.getUrl());

                    break;

                case 0x03:
                    int length = msg.arg1;
                    int total = msg.arg2;

                    materialDialogUtils.showProgress(total, length);

                    break;

            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        materialDialogUtils = new MaterialDialogUtils(this);

        initView();

    }

    @OnClick(R.id.buttonPanel)
    public void checkUpdate(View view) {
        ApiClient.getInstance().getApiServer().updateSoft("json", "getsoftupdate", "zzsoftmis", "2.1.1").subscribeOn(Schedulers.io())   //发送在io 的线程中
                .observeOn(AndroidSchedulers.mainThread())    //运行在android额主线程中
                .subscribe(new Subscriber<SoftUpdate>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(SoftUpdate softUpdate) {
                        if (softUpdate.getStatus().equals("success")) {
                            String url = softUpdate.getSoftupdate().getUrl();
                            String version = softUpdate.getSoftupdate().getVersion();

                            String old = getAppVersionName();

                            int res = ToolUtils.compareVersion(old, version);

                            if (res == -1) {
                                Message message = Message.obtain();
                                message.obj = softUpdate.getSoftupdate();
                                message.what = 0x02;
                                handler.sendMessage(message);  //有更新
                            } else {
                                handler.sendEmptyMessage(0x01);  //最新版

                            }
                        }
                    }
                });

    }

    public String getAppVersionName() {
        try {
            PackageManager mPackageManager = getPackageManager();
            PackageInfo _info = mPackageManager.getPackageInfo(getPackageName(), 0);
            return _info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    private void initView() {
        materialDialogUtils.setDownClickl(new MaterialDialogUtils.DownClick() {

            @Override
            public void downStart(String url) {
                downFile(url);
                materialDialogUtils.initProgress();
            }

            @Override
            public void success(int length) {
                //下载完成

                String fileName = SD_FOLDER + "zzreader.apk";

                if (new File(fileName).length() == length) {
                    ToolUtils.installApk(MainActivity.this, new File(fileName));

                }

            }
        });
    }

    public void downFile(String url) {
        Volley.downFile(url, new IHttpListener() {

            @Override
            public void onSuccess(InputStream inputStream, int total) {
                httpUrlcontentPost(inputStream, total);
            }

            @Override
            public void onFail() {

            }
        });
    }

    public void httpUrlcontentPost(InputStream inputStream, int length) {
        SD_FOLDER = Environment.getExternalStoragePublicDirectory("/a1/").getAbsolutePath() + "/";
        try {


            String fileName = SD_FOLDER + "zzreader.apk";
            File file = new File(fileName);
            // 目录不存在创建目录
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            byte[] buffer = new byte[1024];
            int len;
            int total = 0;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                total += len;

                Message msg = Message.obtain();
                msg.what = 0x03;
                msg.arg1 = length;
                msg.arg2 = total;
                msg.obj = fileName;

                handler.sendMessage(msg);


            }
            fos.close();
            bis.close();
            inputStream.close();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }
}

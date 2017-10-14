package com.stevefat.updateutilslib.dialog;
/**
 * Created by stevefat on 17-10-14.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.stevefat.updateutilslib.R;

import java.math.BigDecimal;

/**
 * Author : stevefat
 * Email :ngh8897@gmail.com
 * Created : 17-10-14 上午9:49
 */
public class MaterialDialogUtils {
    MaterialDialog.Builder dialog;
    MaterialDialog builder;
    Context mContext;

    private TextView downTitle;
    private ProgressBar downProgress;
    private TextView progress_tv;
    private TextView progress_size;


    public interface DownClick {
        void downStart(String url);
        void success(int length);
    }

    public DownClick downClickl;

    public void setDownClickl(DownClick downClickl) {
        this.downClickl = downClickl;

    }

    public MaterialDialogUtils(Context mContext) {
        this.mContext = mContext;
        dialog = new MaterialDialog.Builder(mContext);

    }


    public void showUpdate(String version, String content, final String url) {
        dialog.cancelable(false);
        dialog.title("发现新版本：" + version)
                .content(Html.fromHtml(content))
                .positiveText("更新")
                .negativeText("取消")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //立即更新
                        downClickl.downStart(url);
                    }
                });
        dialog.show();

    }


    public void initProgress() {

        if (dialog != null) {
            dialog.autoDismiss(true);
        }

        View v = LayoutInflater.from(mContext).inflate(R.layout.down_loading, null);

        builder = new MaterialDialog.Builder(mContext).customView(v, false).cancelable(false).title("版本升级").show();


        downTitle = v.findViewById(R.id.title_name);
        progress_tv = v.findViewById(R.id.progress_tv);
        downProgress = v.findViewById(R.id.down_progress);
        progress_size = v.findViewById(R.id.progress_size);

    }

    public void showProgress(int total, int current) {

        int progress = total * 100 / current;
        progress_size.setText(bytes2kb(total) + "/" + bytes2kb(current));
        progress_tv.setText(progress + "%");
        downProgress.setProgress(progress);

        if (current == total) {
            builder.dismiss();
            downClickl.success(current);
        }

    }


    private String bytes2kb(long bytes) {
        BigDecimal filesize = new BigDecimal(bytes);
        BigDecimal megabyte = new BigDecimal(1024 * 1024);
        float returnValue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP)
                .floatValue();
        if (returnValue > 1)
            return (returnValue + "MB");
        BigDecimal kilobyte = new BigDecimal(1024);
        returnValue = filesize.divide(kilobyte, 2, BigDecimal.ROUND_UP)
                .floatValue();
        return (returnValue + "KB");
    }


}

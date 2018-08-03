package com.march.common.extensions;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import com.march.common.Common;

import java.io.File;

/**
 * CreateAt : 2018/8/2
 * Describe :
 *
 * @author chendong
 */
public class UriX {

    public static Uri fromFile(Context context, File file) {
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return FileProvider.getUriForFile(context, Common.BuildConfig.APPLICATION_ID + ".fileProvider", file);
        } else {
            return Uri.fromFile(file);
        }

    }
}

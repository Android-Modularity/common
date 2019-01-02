package com.march.common;

import android.app.Application;
import android.content.Context;

import com.march.common.exts.SizeX;
import com.march.common.exts.ToastX;
import com.march.common.model.AppBuildConfig;
import com.march.common.model.Exports;

/**
 * CreateAt : 2017/12/6
 * Describe :
 *
 * @author chendong
 */
public class Common {


    public static Exports exports = new Exports();

    public static void init(Application app, Class buildClazz) {
        exports.init = true;
        exports.app = app;
        exports.appConfig = new AppBuildConfig(buildClazz);
        SizeX.init();
        ToastX.init(new ToastX.Config().setOneToast(true));
    }

    public static Context app() {
        return exports.app;
    }

    public static AppBuildConfig appConfig() {
        return exports.appConfig;
    }

    public Context getContext() {
        return exports.app;
    }


}

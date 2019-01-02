package com.march.common.exts;

import android.os.Environment;

import com.march.common.Common;

import java.io.File;

/**
 * CreateAt : 2018/10/15
 * Describe :
 *
 * @author chendong
 */
public class PathX {

    public static final String DOWNLOAD = "download";
    public static final String THUMB    = ".thumb";
    public static final String TEMP     = "temp";

    public static File cacheRoot() {
        return Common.app().getCacheDir();
    }

    public static File sdcardRoot() {
        return new File(Environment.getExternalStorageDirectory(), Common.appConfig().APPLICATION_ID);
    }

    public static File download(File root) {
        return custom(root, DOWNLOAD);
    }

    public static File thumb(File root) {
        return custom(root, THUMB);
    }

    public static File temp(File root) {
        return custom(root, TEMP);
    }

    public static File custom(File root, String path) {
        File file = new File(root, path);
        FileX.newDir(file.getAbsolutePath());
        return file;
    }
}

package com.march.common.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * CreateAt : 16/8/15
 * Describe : 创建路径
 *
 * @author chendong
 */
public class PathUtils {

    public static final String POINT_GIF  = ".gif";
    public static final String POINT_JPG  = ".jpg";
    public static final String POINT_PNG  = ".png";
    public static final String POINT_JPEF = ".jpeg";
    public static final String POINT_MP4  = ".mp4";
    public static final String POINT_MP3  = ".mp3";


    public static String fileKey;
    /**
     * app 父路径
     */
    public static String AppRootPath;
    /**
     * 暂存路径
     */
    public static String TEMP_PATH;
    /**
     * 相册文件夹
     */
    public static String DCIM_PATH;
    /**
     * 不可见文件夹
     */
    public static String THUMB_PATH;
    /**
     * 下载文件夹
     */
    public static String DOWNLOAD_PATH;
    /**
     * 根路径
     */
    public static String BASE_PATH;


    /**
     * 初始化路径
     *
     * @param context context
     * @param key     专门文件名
     */
    public static void init(Context context, String key) {
        if (key != null) fileKey = key;
        else fileKey = context.getPackageName();

        if (FileUtils.isSDCardValid()) {
            BASE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            BASE_PATH = context.getFilesDir().getAbsolutePath();
        }
        FileUtils.newDir(BASE_PATH);
        DCIM_PATH = new File(BASE_PATH, "/DCIM/Camera").getAbsolutePath();
        AppRootPath = new File(BASE_PATH, fileKey).getAbsolutePath();
        TEMP_PATH = AppRootPath + "/temp";
        THUMB_PATH = AppRootPath + "/.thumb";
        DOWNLOAD_PATH = AppRootPath + "/download";

        FileUtils.newDir(AppRootPath);
        FileUtils.newDir(TEMP_PATH);
        FileUtils.newDir(THUMB_PATH);
        FileUtils.newDir(DOWNLOAD_PATH);

    }

    public static File dcimDir() {
        return FileUtils.newDir(DCIM_PATH);
    }

    public static File thumbDir() {
        return FileUtils.newDir(THUMB_PATH);
    }

    public static File downloadDir() {
        return FileUtils.newDir(DOWNLOAD_PATH);
    }

    public static File tempDir() {
        return FileUtils.newDir(TEMP_PATH);
    }

    public static boolean isImagePath(String filePath) {
        return !FileUtils.isNotExist(filePath) && (filePath.toLowerCase().endsWith(POINT_JPEF) || filePath.toLowerCase().endsWith(POINT_PNG) || filePath.toLowerCase().endsWith(POINT_GIF));
    }


    public static boolean isWithSuffix(String path, String suffix) {
        return path.toLowerCase().endsWith(suffix);
    }
}

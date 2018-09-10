package com.march.common.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;

import com.march.common.extensions.LogX;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * CreateAt : 16/8/13
 * Describe : 唤醒系统分享
 *
 * @author chendong
 */
public class ShareUtils {


    /**
     * 分享文字
     *
     * @param context 上下文
     * @param title   文字标题
     * @param content 文字内容
     */
    public static void viewText(Context context, String title, String content) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_VIEW);
        shareIntent.putExtra(Intent.EXTRA_TEXT, content);
        shareIntent.putExtra(Intent.EXTRA_TITLE, title);
        shareIntent.setType("text/plain");
        //设置分享列表的标题，并且每次都显示分享列表
        context.startActivity(Intent.createChooser(shareIntent, "分享到"));
    }


    /**
     * 分享文字
     *
     * @param context 上下文
     * @param title   文字标题
     * @param content 文字内容
     */
    public static void shareText(Context context, String title, String content) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, content);
        shareIntent.putExtra(Intent.EXTRA_TITLE, title);
        shareIntent.setType("text/plain");
        //设置分享列表的标题，并且每次都显示分享列表
        context.startActivity(Intent.createChooser(shareIntent, "分享到"));
    }

    /**
     * 分享单张图片
     *
     * @param context 上下文
     * @param path    图片的路径
     */
    public static void shareImage(Context context, String path) {
        //由文件得到uri
        Uri imageUri = Uri.fromFile(new File(path));
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("image/*");
        context.startActivity(Intent.createChooser(shareIntent, "分享到"));
    }

    /**
     * 分享多张图片
     *
     * @param context 上下文
     * @param paths   路径的集合
     */
    public static void shareImages(Context context, List<String> paths) {
        ArrayList<Uri> uriList = new ArrayList<>();
        for (String path : paths) {
            uriList.add(Uri.fromFile(new File(path)));
        }
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriList);
        shareIntent.setType("image/*");
        context.startActivity(Intent.createChooser(shareIntent, "分享到"));
    }


    /**
     * 处理分享页面发来的intent
     * @param context context
     */
    public static void handleIntent(Activity context) {
        Intent intent = context.getIntent();
        String type = intent.getType();
        if (type == null)
            return;
        // 当文件格式无法识别或选择了多种类型的文件时，type会变成 */*
        //type可能为的类型是 image/* | video/* | audio/* | text/* | application/*
        if (type.startsWith("text") && intent.getStringExtra(Intent.EXTRA_TEXT) != null) {
            // 当直接选中文本分享时，会存放在EXTRA_TEXT里面，选择文本文件时，仍然存放在EXTRA_STREAM里面
            LogX.e("获取到分享的文本 " + intent.getStringExtra(Intent.EXTRA_TEXT));
        } else {
            List<String> sharePaths = getSharePaths(context, intent);
            LogX.e("获取到分享的文件的路径 " + sharePaths.toString());
        }
    }


    /**
     * 获取分享过来的路径
     *
     * @param context 上下文
     * @param intent  intent
     * @return 路径列表
     */
    private static List<String> getSharePaths(Context context, Intent intent) {
        String action = intent.getAction();
        List<String> paths = new ArrayList<>();
        // 单个文件
        if (Intent.ACTION_SEND.equals(action)) {
            Uri imageUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
            if (imageUri != null) {
                String realPathFromURI = getRealPathFromURI(context, imageUri);
                if (realPathFromURI != null)
                    paths.add(realPathFromURI);
            }
        }
        // 多个文件
        else if (Intent.ACTION_SEND_MULTIPLE.equals(action)) {
            ArrayList<Uri> imageUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
            if (imageUris != null) {
                for (Uri uri : imageUris) {
                    paths.add(getRealPathFromURI(context, uri));
                }
            }
        }
        return paths;
    }


    /**
     * 从uri获取path
     *
     * @param uri uri
     * @return path
     */
    private static String getRealPathFromURI(Context context, Uri uri) {
        final String scheme = uri.getScheme();
        String data = null;
        if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver()
                    .query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}
                            , null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        if (data != null) {
            LogX.e(MimeTypeMap.getFileExtensionFromUrl(data));
        }
        return data;
    }
}

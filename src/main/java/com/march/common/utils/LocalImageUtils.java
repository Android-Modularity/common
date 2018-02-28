package com.march.common.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;

import com.march.common.model.ImageInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CreateAt : 16/8/15
 * Describe : 图像操作
 *
 * @author chendong
 */
public class LocalImageUtils {

    public static final String ALL_IMAGE_KEY = "全部图片";

    /**
     * 获取全部照片信息
     *
     * @param context 上下文
     * @return 照片信息列表
     */
    public static List<ImageInfo> getImagesByMediaStore(Context context) {

        List<ImageInfo> mImageInfoList = new ArrayList<>();
        Cursor imageCursor = null;
        try {
            final String[] projection;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                projection = new String[]{MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID, MediaStore.Images.Media.DATE_MODIFIED, MediaStore.Images.Media.WIDTH, MediaStore.Images.Media.HEIGHT};
            } else {
                projection = new String[]{MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID, MediaStore.Images.Media.DATE_MODIFIED};
            }
            final String sortOrder = MediaStore.Images.Media._ID;
            imageCursor = MediaStore.Images.Media.query(context.getContentResolver(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, sortOrder);
            if (imageCursor == null || imageCursor.getCount() <= 0) {
                return mImageInfoList;
            }
            while (imageCursor.moveToNext()) {
                ImageInfo imageInfo = new ImageInfo();
                // 返回data在第几列，并获取地址
                int dataColumnIndex = imageCursor.getColumnIndex(MediaStore.Images.Media.DATA);
                String path = imageCursor.getString(dataColumnIndex);
                File file = new File(path);
                // 该路径下的文件存在则添加到集合中
                if (file.exists()) {
                    // 添加路径到对象中
                    imageInfo.setPath(path);
                    int width = 0;
                    int height = 0;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        int widthColumnIndex = imageCursor.getColumnIndex(MediaStore.Images.Media.WIDTH);
                        int heightColumnIndex = imageCursor.getColumnIndex(MediaStore.Images.Media.HEIGHT);
                        if (widthColumnIndex != -1)
                            width = imageCursor.getInt(widthColumnIndex);
                        if (heightColumnIndex != -1)
                            height = imageCursor.getInt(heightColumnIndex);
                    }
                    if (width == 0 || height == 0) {
                        BitmapFactory.Options bitmapSize = BitmapUtils.getBitmapSize(path);
                        width = bitmapSize.outWidth;
                        height = bitmapSize.outHeight;
                    }
                    if (width <= 0 || height <= 0)
                        continue;
                    imageInfo.setWidth(width);
                    imageInfo.setHeight(height);
                    // 插入修改时间
                    String modifiedDate = imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DATE_MODIFIED));
                    // 添加修改时间到对象中
                    imageInfo.setDate(modifiedDate);
                    // 添加名字
                    mImageInfoList.add(imageInfo);
                }
            }
            imageCursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (imageCursor != null)
                imageCursor.close();
        }
        // 按降序排
        Collections.sort(mImageInfoList);
        return mImageInfoList;
    }

    /**
     * 获取全部照片并且按照文件夹分离排序
     *
     * @param context 上下文
     * @return map(目录名称 目录下的所有照片)
     */
    public static Map<String, List<ImageInfo>> formatImages4EachDir(Context context) {
        return formatImages4EachDir(getImagesByMediaStore(context));
    }


    /**
     * 目录名称 + 目录下的照片列表
     *
     * @param imageInfoList 全部的照片信息
     * @return map(目录名称 目录下的所有照片)
     */
    public static Map<String, List<ImageInfo>> formatImages4EachDir(List<ImageInfo> imageInfoList) {
        Map<String, List<ImageInfo>> map = new HashMap<>();
        List<ImageInfo> imageInfoAll = new ArrayList<>();
        imageInfoAll.addAll(imageInfoList);
        map.put(ALL_IMAGE_KEY, imageInfoAll);

        for (ImageInfo imageInfo : imageInfoList) {

            boolean isImageValid = imageInfo != null
                    && !CheckUtils.isEmpty(imageInfo.getPath())
                    && !FileUtils.isNotExist(imageInfo.getPath())
                    && new File(imageInfo.getPath()).getParentFile() != null;

            if (isImageValid) {
                String dirName = new File(imageInfo.getPath()).getParentFile().getName();
                if (map.containsKey(dirName)) {
                    map.get(dirName).add(imageInfo);
                } else {
                    List<ImageInfo> tempImageInfoList = new ArrayList<>();
                    tempImageInfoList.add(imageInfo);
                    map.put(dirName, tempImageInfoList);
                }
            }
        }
        return map;
    }
}

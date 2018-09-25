package com.march.common.exts;

import com.march.common.disklru.DiskLruCache;

import java.io.File;
import java.io.IOException;

/**
 * CreateAt : 2018/9/10
 * Describe :
 *
 * @author chendong
 */
public class DiskCacheX {

    public static DiskLruCache open(File directory, int appVersion, int valueCount, long maxSize) {
        try {
            return DiskLruCache.open(directory, appVersion, valueCount, maxSize);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

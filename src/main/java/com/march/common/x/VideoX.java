package com.march.common.x;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.text.TextUtils;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * CreateAt : 2018/1/8
 * Describe :
 *
 * @author chendong
 */
public class VideoX {

    /**
     * 获取视频的信息
     *
     * @param videoPath 视频路径
     * @return 视频数据
     */
    public static MetaData parseVideoMetaData(String videoPath) {
        if (TextUtils.isEmpty(videoPath)) {
            return null;
        }
        MediaMetadataRetriever mmr = null;
        MetaData metaData = new MetaData();
        int width = 0;
        int height = 0;
        int duration = 0;
        try {
            mmr = new MediaMetadataRetriever();
            if (videoPath.startsWith("http")) {
                // decode from net
                mmr.setDataSource(videoPath, new HashMap<>());
            } else {
                File videoFile = new File(videoPath);
                if (!videoFile.exists()) {
                    return null;
                }
                FileInputStream fileInputStream = new FileInputStream(videoPath);
                FileDescriptor fileInputStreamFD = fileInputStream.getFD();
                mmr.setDataSource(fileInputStreamFD);
            }
            // 时长
            String durationStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            // 宽度
            String widthStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
            // 高度
            String heightStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
            // 缩略图, OPTION_NEXT_SYNC
            Bitmap frameAtTime = mmr.getFrameAtTime();
            if (durationStr != null) {
                duration = Integer.parseInt(durationStr);
            }
            duration = duration > 60 * 1000 ? 60 * 1000 : duration;
            if (widthStr != null) {
                width = Integer.parseInt(widthStr);
            }
            if (heightStr != null) {
                height = Integer.parseInt(heightStr);
            }
            metaData.width = width;
            metaData.height = height;
            metaData.duration = duration;
            metaData.thumb = frameAtTime;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (mmr != null) {
                mmr.release();
            }
        }
        return null;
    }

    public static class MetaData {
        int    width;
        int    height;
        long   duration;
        Bitmap thumb;
    }
}

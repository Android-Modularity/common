package com.march.common.adapter;

import android.content.Context;
import android.widget.ImageView;

/**
 * CreateAt : 2018/8/3
 * Describe :
 *
 * @author chendong
 */
public interface ImgLoadAdapter {
    void loadImg(Context context, String path, int width, int height, ImageView imageView);
}

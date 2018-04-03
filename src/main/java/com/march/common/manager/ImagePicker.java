package com.march.common.manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.march.common.BuildConfig;

import java.io.File;
import java.util.UUID;

/**
 * CreateAt : 16/8/15
 * Describe : 选择照片的基类,全都放到一个类里面，也是可行的，但是代码不够清晰,不建议使用
 *
 * @author chendong
 */
public class ImagePicker {

    public static final int PICK_ALBUM = 0;
    public static final int PICK_CAPTURE = 1;

    private static final int CHOOSE_PHOTO_FROM_CAMERA = 0x11;
    private static final int CHOOSE_PHOTO_FROM_ALBUM = 0x12;
    private static final int CHOOSE_PHOTO_FROM_SYSTEM_CROP = 0x13;

    private Activity mActivity;
    private String mSaveImageParentPath;//获取图片或者剪切后存放目录
    private File mCropImageFile;//裁剪之后的文件
    private File mCaptureImageFile;//从拍照返回的文件

    private int mPickType;
    private float xRatio;
    private float yRatio;
    private int width;
    private int height;
    private boolean isCrop;

    private OnPickFinishListener mListener;
    private File mAlbumImageFile;

    public interface OnPickFinishListener {
        void onPickerOver(File file);
    }

    public ImagePicker(Activity activity, String saveImageParentPath) {
        mActivity = activity;
        mSaveImageParentPath = saveImageParentPath;
        xRatio = 1;
        yRatio = 1;
        width = 300;
        height = 300;
        isCrop = true;
    }

    public ImagePicker pick(int type) {
        this.mPickType = type;
        if (mPickType == PICK_CAPTURE) {
            mCaptureImageFile = generateFile("capture", "jpg");
        }
        Intent intent = createIntent();
        if (intent == null) {
            return this;
        }
        if (mPickType == PICK_ALBUM) {
            mActivity.startActivityForResult(intent, CHOOSE_PHOTO_FROM_ALBUM);
        } else if (mPickType == PICK_CAPTURE) {
            mActivity.startActivityForResult(intent, CHOOSE_PHOTO_FROM_CAMERA);
        }
        return this;
    }

    public ImagePicker size(int width, int height) {
        this.width = width;
        this.height = height;
        this.xRatio = 1.0f;
        this.yRatio = height * 1.0f / width;
        return this;
    }

    public ImagePicker listener(OnPickFinishListener mListener) {
        this.mListener = mListener;
        return this;
    }

    public ImagePicker crop(boolean isCrop) {
        this.isCrop = isCrop;
        return this;
    }

    public void onActivityResult(Context context, int requestCode, int resultCode, Intent intent) {
        // 相册返回,存放在path路径的文件中
        if (requestCode == CHOOSE_PHOTO_FROM_ALBUM && resultCode == Activity.RESULT_OK) {
            String path = null;
            if (intent == null || intent.getData() == null) {
                return;
            }
            // 获得相册中图片的路径
            if ("file".equals(intent.getData().getScheme())) {
                path = intent.getData().getPath();
            } else if ("content".equals(intent.getData().getScheme())) {
                Uri selectedImage = intent.getData();
                String[] filePathColumns = {MediaStore.Images.Media.DATA};
                Cursor cursor = mActivity.getContentResolver().query(selectedImage, filePathColumns, null, null, null);
                assert cursor != null;
                if (cursor.moveToFirst()) {
                    path = cursor.getString(cursor.getColumnIndex(filePathColumns[0]));
                }
                cursor.close();
            }
            if (!TextUtils.isEmpty(path)) {
                mAlbumImageFile = new File(path);
                if (!mAlbumImageFile.exists()) {
                    return;
                }
                if (!isCrop) {
                    publishResult(mAlbumImageFile);
                } else {
                    startSystemCrop(Uri.fromFile(mAlbumImageFile));
                }
            }
        }
        // 相机拍摄返回
        else if (requestCode == CHOOSE_PHOTO_FROM_CAMERA && resultCode == Activity.RESULT_OK) {
            // 拍照返回,如果先前传入的文件路径下有文件，就通过回调返回
            if (mCaptureImageFile != null && mCaptureImageFile.exists()) {
                if (!isCrop) {
                    publishResult(mCaptureImageFile);
                } else {
                    startSystemCrop(Uri.fromFile(mCaptureImageFile));
                }
            }
        }
        // 头像裁剪返回
        else if (requestCode == CHOOSE_PHOTO_FROM_SYSTEM_CROP && resultCode == Activity.RESULT_OK) {
            if (!mCropImageFile.exists()) {
                return;
            }
            publishResult(mCropImageFile);
        }
    }


    private Intent createIntent() {
        Intent intent = null;
        if (mPickType == PICK_ALBUM) {
            // 调用系统相册
            intent = new Intent(Intent.ACTION_PICK);// 系统相册action
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        } else if (mPickType == PICK_CAPTURE) {
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            Uri contentUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileProvider", file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCaptureImageFile));
        }
        return intent;
    }

    private File generateFile(String sign, String suffix) {
        // 通过uuid生成照片唯一名字
        String mOutFileName = UUID.randomUUID().toString() + "_" + sign + "_image." + suffix;
        return new File(mSaveImageParentPath, mOutFileName);
    }

    private void publishResult(File file) {
        if (mListener != null) {
            mListener.onPickerOver(file);
        }
    }

    /**
     * 启动裁剪图片
     *
     * @param uri 原始文件uri
     */
    private void startSystemCrop(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", xRatio);
        intent.putExtra("aspectY", yRatio);
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true); // 黑边
        intent.putExtra("noFaceDetection", true); // no face detection
        // outputX,outputY 是剪裁图片的宽高
        // 指定之后会将图片缩放到指定 size
        // intent.putExtra("outputX", width);
        // intent.putExtra("outputY", height);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // 剪切返回，头像存放的路径
        mCropImageFile = generateFile("crop", "png");
        intent.putExtra("output", Uri.fromFile(mCropImageFile)); // 专入目标文件
        mActivity.startActivityForResult(intent, CHOOSE_PHOTO_FROM_SYSTEM_CROP);
    }


}

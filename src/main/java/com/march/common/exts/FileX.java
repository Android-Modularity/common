package com.march.common.exts;

import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * CreateAt : 16/8/15
 * Describe : 文件相关操作
 *
 * @author chendong
 */
public class FileX {

    /**
     * 复制文件到
     *
     * @param srcFile         原文件
     * @param destFile        目标文件
     * @param isDeleteSrcFile 是否删除源文件
     * @return 复制成功
     */
    public static boolean copyTo(File srcFile, File destFile, boolean isDeleteSrcFile) {
        if (isNotExist(srcFile)) {
            return false;
        }
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            newDir(destFile.getParentFile().getAbsolutePath());
            if (!destFile.createNewFile()) {
                return false;
            }
            bis = new BufferedInputStream(new FileInputStream(srcFile));
            bos = new BufferedOutputStream(new FileOutputStream(destFile));
            int size;
            byte[] temp = new byte[1024 * 2];
            while ((size = bis.read(temp, 0, temp.length)) != -1) {
                bos.write(temp, 0, size);
            }
            bos.flush();
            if (isDeleteSrcFile) delete(srcFile);
            return true;
        } catch (IOException e) {
            LogX.e(e);
        } finally {
            RecycleX.recycle(bis, bos);
        }
        return true;
    }

    /**
     * 遍历删除文件
     *
     * @param delFile 要删除的文件／文件夹
     * @return 正确删除
     */
    public static boolean delete(File delFile) {
        if (isNotExist(delFile))
            return false;
        if (delFile.isFile()) {
            return delFile.delete();
        } else if (delFile.isDirectory()) {
            for (File file : delFile.listFiles()) {
                delete(file);
            }
        }
        return true;
    }

    /**
     * @param path 路径
     * @return 文件夹
     */
    public static File newDir(String path) {
        File file = new File(path);
        boolean mkdirs = file.mkdirs();
        if (mkdirs) return file;
        else return null;
    }

    /**
     * @param fileName 文件名
     * @return 在根目录下创建文件
     */
    public static File newRootFile(String fileName) {
        return new File(Environment.getExternalStorageDirectory(), fileName);
    }

    /**
     * @return sd卡是否可用
     */
    public static boolean isSDCardValid() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * @param filePath 路径
     * @return 文件不存在？
     */
    public static boolean isNotExist(String filePath) {
        return EmptyX.isEmpty(filePath) || isNotExist(new File(filePath));
    }

    /**
     * @param file 文件
     * @return 文件不存在？
     */
    public static boolean isNotExist(File file) {
        return file == null || !file.exists() || file.length() == 0;
    }

    public static boolean isImageFile(String filePath){
        return !FileX.isNotExist(filePath) && (filePath.toLowerCase().endsWith("jpg") || filePath.toLowerCase().endsWith("png") || filePath.toLowerCase().endsWith("gif"));
    }


}

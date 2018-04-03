package com.march.common.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * CreateAt : 2016/10/31
 * Describe : 本地照片信息
 *
 * @author chendong
 */

public class ImageInfo implements Comparable<ImageInfo>, Parcelable {

    // 设置id为自增长的组件
    private Integer id;
    // 文件地址
    private String path;
    //0未选中,1选中未插入数据库,||(这边是已经插入数据库的可能状态)2选中插入数据库,3已经上传照片,4完全发布
    private int status;
    // 照片名字
    private String name;
    // 秒数
    private String date;
    private int width;
    private int height;
    private int fileId;
    private boolean select;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o) && path.equals(((ImageInfo) o).getPath());
    }

    @Override
    public int compareTo(ImageInfo another) {
        try {
            long a = Long.parseLong(date);
            long b = Long.parseLong(another.getDate());
            if (b > a) {
                return 1;
            } else if (b < a) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    public ImageInfo() {
    }

    @Override
    public String toString() {
        return "ImageInfo{" +
                "path='" + path + '\'' +
                ", date='" + date + '\'' +
                ", width=" + width +
                ", height=" + height +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.path);
        dest.writeInt(this.status);
        dest.writeString(this.name);
        dest.writeString(this.date);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeInt(this.fileId);
        dest.writeByte(this.select ? (byte) 1 : (byte) 0);
    }

    protected ImageInfo(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.path = in.readString();
        this.status = in.readInt();
        this.name = in.readString();
        this.date = in.readString();
        this.width = in.readInt();
        this.height = in.readInt();
        this.fileId = in.readInt();
        this.select = in.readByte() != 0;
    }

    public static final Creator<ImageInfo> CREATOR = new Creator<ImageInfo>() {
        @Override
        public ImageInfo createFromParcel(Parcel source) {
            return new ImageInfo(source);
        }

        @Override
        public ImageInfo[] newArray(int size) {
            return new ImageInfo[size];
        }
    };
}

package com.march.common.model;

import com.march.common.utils.LgUtils;

import java.lang.reflect.Field;

/**
 * CreateAt : 2018/6/13
 * Describe : 用来下沉主模块的 BuildConfig
 * 使用 Common.BuildConfig 调用
 *
 * @author chendong
 */
public class BuildConfig {

    public boolean DEBUG;
    public String  APPLICATION_ID;
    public String  BUILD_TYPE;
    public String  FLAVOR;
    public int     VERSION_CODE;
    public String  VERSION_NAME;

    public BuildConfig(Class clazz) {
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            try {
                declaredField.setAccessible(true);
                switch (declaredField.getName()) {
                    case "DEBUG":
                        DEBUG = (boolean) declaredField.get(clazz);
                        break;
                    case "APPLICATION_ID":
                        APPLICATION_ID = (String) declaredField.get(clazz);
                        break;
                    case "BUILD_TYPE":
                        BUILD_TYPE = (String) declaredField.get(clazz);
                        break;
                    case "FLAVOR":
                        FLAVOR = (String) declaredField.get(clazz);
                        break;
                    case "VERSION_CODE":
                        VERSION_CODE = (int) declaredField.get(clazz);
                        break;
                    case "VERSION_NAME":
                        VERSION_NAME = (String) declaredField.get(clazz);
                        break;
                }
            } catch (IllegalAccessException e) {
                LgUtils.e(e);
            }
        }
    }
}

package com.march.common.mgrs;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.SparseArray;

import com.march.common.exts.AppUIMixin;
import com.march.common.exts.ListX;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CreateAt : 2018/9/28
 * Describe : 权限请求管理类
 *
 * @author chendong
 */
public class PermissionMgr implements IMgr {

    private int mReqCode = 0x76;
    private SparseArray<PermissionCallback> mConsumerSparseArray;

    public PermissionMgr() {
        mConsumerSparseArray = new SparseArray<>();
    }

    /**
     * 处理请求权限的结果
     *
     * @param requestCode  code
     * @param permissions  权限列表
     * @param grantResults 结果
     */
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionCallback permissionCallback = mConsumerSparseArray.get(requestCode);
        if (permissionCallback == null) {
            return;
        }
        HashMap<String, Boolean> result = new HashMap<>();
        boolean hasDenied = false;
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                hasDenied = true;
            }
            result.put(permissions[i], grantResults[i] == PackageManager.PERMISSION_GRANTED);
        }
        permissionCallback.onResult(!hasDenied, result);
        mConsumerSparseArray.remove(requestCode);
    }

    /**
     * @param mixin       UI Mixin
     * @param permissions 需要请求的权限
     * @param consumer    请求权限的结果
     * @return 是否获得全部权限
     */
    public boolean requestPermissions(AppUIMixin mixin, PermissionCallback consumer, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> needReqPermissions = new ArrayList<>();
            for (String per : permissions) {
                if (!hasPermission(mixin.getActivity(), per)) {
                    needReqPermissions.add(per);
                }
            }
            if (needReqPermissions.isEmpty()) {
                consumer.onResult(true, new HashMap<>());
                return true;
            }
            mixin.requestPermissions(mReqCode, needReqPermissions.toArray(new String[needReqPermissions.size()]));
            // save callback
            mConsumerSparseArray.append(mReqCode, consumer);
            mReqCode++;
        } else {
            consumer.onResult(true, new HashMap<>());
            return true;
        }

        return false;
    }

    /**
     * @param activity    act
     * @param permissions 权限
     * @return 是否有这些权限
     */
    public boolean hasPermission(Activity activity, String... permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        List<String> strings = ListX.listOf(permissions);
        return ListX.all(strings, per -> activity.checkSelfPermission(per) == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void recycle() {
        if (mConsumerSparseArray != null) {
            mConsumerSparseArray.clear();
            mConsumerSparseArray = null;
        }
    }

    @Override
    public boolean isRecycled() {
        return false;
    }

    public interface PermissionCallback {
        /**
         * @param allGrant            全部获得权限
         * @param permissionResultMap 权限-对应权限的结果 map
         */
        void onResult(boolean allGrant, Map<String, Boolean> permissionResultMap);
    }
}

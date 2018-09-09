package com.march.common.extensions;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

/**
 * CreateAt : 2018/8/2
 * Describe :
 *
 * @author chendong
 */
public class PermissionX {

    public static boolean requestPermissions(
            AppUIMixin mixin,
            int requestCode,
            String...permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        List<String> needReqPermissions = new ArrayList<>();
        for (String per : permissions) {
            if(!hasPermission(mixin.getActivity(),per)) {
                needReqPermissions.add(per);
            }
        }
        if (needReqPermissions.isEmpty()) {
            return true;
        }
        mixin.requestPermissions(requestCode, needReqPermissions.toArray(new String[needReqPermissions.size()]));
        return false;
    }

    public static boolean hasPermission(Activity activity,String... permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        List<String> strings = ListX.listOf(permissions);
        return ListX.all(strings, per -> activity.checkSelfPermission(per) == PackageManager.PERMISSION_GRANTED);
    }

    public static boolean hasAllPermission(String[] permissions, int[] grantResults){
        List<Integer> integers = ListX.intListOf(grantResults);
        return ListX.all(integers,result -> result == PackageManager.PERMISSION_GRANTED);
    }

}

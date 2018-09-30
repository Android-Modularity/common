package com.march.common.exts;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * CreateAt : 2018/9/30
 * Describe :
 *
 * @author chendong
 */
public class ClipboardX {

    public static @Nullable
    ClipboardManager getManager(Context context) {
        return (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
    }

    /**
     * 复制 -> 类型一: text
     */
    public static void copy(Context context, String text) {
        ClipboardManager clipboard = getManager(context);
        ClipData textCd = ClipData.newPlainText("text", text);
        if (clipboard != null) {
            clipboard.setPrimaryClip(textCd);
        }
    }

    /**
     * 粘贴 -> 类型一: text
     */
    public static CharSequence pasteText(Context context) {
        ClipboardManager clipboard = getManager(context);
        if (clipboard != null && clipboard.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
            ClipData cdText = clipboard.getPrimaryClip();
            // 此处是 text
            ClipData.Item item = cdText.getItemAt(0);
            return item.getText();
        }
        return null;
    }

    /**
     * 复制 -> 类型二: Uri
     */
    public static void copy(Context context, Uri uri) {
        ClipboardManager clipboard = getManager(context);
        ClipData clipUri = ClipData.newUri(context.getContentResolver(), "Uri", uri);
        if (clipboard != null) {
            clipboard.setPrimaryClip(clipUri);
        }
    }

    /**
     * 粘贴 -> 类型二: Uri
     */
    public static Uri pasteUri(Context context) {
        ClipboardManager clipboard = getManager(context);
        if (clipboard != null && clipboard.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_URILIST)) {
            ClipData cdUri = clipboard.getPrimaryClip();
            // 此处是 Uri
            ClipData.Item item = cdUri.getItemAt(0);
            return item.getUri();
        }
        return null;
    }

    /**
     * 复制 -> 类型三: Intent
     */
    public static void copy(Context context, Intent intent) {
        ClipboardManager clipboard = getManager(context);
        ClipData clipIntent = ClipData.newIntent("Intent", intent);
        if (clipboard != null) {
            clipboard.setPrimaryClip(clipIntent);
        }
    }

    /**
     * 粘贴 -> 类型三: Intent
     */
    public static Intent pasteIntent(Context context) {
        ClipboardManager clipboard = getManager(context);
        if (clipboard != null && clipboard.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_INTENT)) {
            ClipData cdIntent = clipboard.getPrimaryClip();
            // 此处是 Intent
            ClipData.Item item = cdIntent.getItemAt(0);
            return item.getIntent();
        }
        return null;
    }

}

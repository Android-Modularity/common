package com.march.common.utils;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.march.common.extensions.ToastX;

import java.io.File;

/**
 * CreateAt : 2017/7/14
 * Describe : WebView 辅助
 *
 * @author chendong
 */
public class WebViewUtils {

    private static void initWebViewCache(WebView mWebView) {

        String cachePath = new File(Environment.getExternalStorageDirectory()
                , "webCache").getAbsolutePath();

        WebSettings settings = mWebView.getSettings();
        settings.setAppCacheEnabled(true);
        settings.setAppCachePath(cachePath);
        settings.setDatabaseEnabled(true);
        // 过时
        settings.setDatabasePath(cachePath);
        settings.setDomStorageEnabled(true);// 开启dom缓存

        // LOAD_DEFAULT：默认的缓存使用模式。在进行页面前进或后退的操作时，如果缓存可用并未过期就优先加载缓存，否则从网络上加载数据。这样可以减少页面的网络请求次数。
        // LOAD_CACHE_ELSE_NETWORK：只要缓存可用就加载缓存，哪怕它们已经过期失效。如果缓存不可用就从网络上加载数据。
        // LOAD_NO_CACHE：不加载缓存，只从网络加载数据。
        // LOAD_CACHE_ONLY：不从网络加载数据，只从缓存加载数据。
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 缓存最大值，过时
        settings.setAppCacheMaxSize(1000 * 1024);
    }


    @SuppressLint("SetJavaScriptEnabled")
    public static void initWebViewSettings(WebView mWebView) {
        //支持获取手势焦点
        mWebView.requestFocusFromTouch();
        // 触觉反馈，暂时没发现用处在哪里
        mWebView.setHapticFeedbackEnabled(false);

        WebSettings settings = mWebView.getSettings();
        // 支持插件
        settings.setPluginState(WebSettings.PluginState.ON);
        // 允许js交互
        settings.setJavaScriptEnabled(true);
        // 设置WebView是否可以由 JavaScript 自动打开窗口，默认为 false
        // 通常与 JavaScript 的 window.open() 配合使用。
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 允许中文编码
        settings.setDefaultTextEncodingName("UTF-8");
        // 使用大视图，设置适应屏幕
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        // 支持多窗口
        settings.setSupportMultipleWindows(false);
        // 隐藏自带缩放按钮
        settings.setBuiltInZoomControls(false);
        // 支持缩放
        settings.setSupportZoom(true);
        //设置可访问文件
        settings.setAllowFileAccess(true);
        //当WebView调用requestFocus时为WebView设置节点
        settings.setNeedInitialFocus(true);
        //支持自动加载图片
        settings.setLoadsImagesAutomatically(true);
        // 指定WebView的页面布局显示形式，调用该方法会引起页面重绘。
        // NORMAL,SINGLE_COLUMN 过时, NARROW_COLUMNS 过时 ,TEXT_AUTOSIZING
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // 从Lollipop(5.0)开始WebView默认不允许混合模式，https当中不能加载http资源，需要设置开启
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    public static String getSdUrl(String fileUrl) {
        return "file://" + Environment.getExternalStorageDirectory() + "/" + fileUrl;
    }

    public static String getAssertUrl(String fileUrl) {
        return "file:///android_asset/" + fileUrl;
    }

    public static boolean goBack(WebView webView) {
        if (webView.canGoBack()) {
            webView.goBack();
            return true;
        } else {
            return false;
        }
    }


    public static final String ALIPAY_SCHEME = "alipays";
    public static final String TAOBAO_SCHEME = "taobao";
    public static final String TMALL_SCHEME = "tmall";

    /**
     * 检测到不是 http 协议时，使用 intent 跳转
     *
     * @param context app
     * @param url     链接
     * @return 是否截断
     */
    public static boolean shouldOverrideIntentUrl(Context context, String url) {
        Uri uri = Uri.parse(url);
        if (uri != null && uri.getScheme() != null && !uri.getScheme().startsWith("http")) {
            try {
                Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setComponent(null);
                intent.setSelector(null);
                context.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                if (e instanceof ActivityNotFoundException) {
                    // 找不到客户端
                    switch (uri.getScheme()) {
                        case ALIPAY_SCHEME:
                            ToastX.show("未检测到支付宝客户端");
                            break;
                        case TAOBAO_SCHEME:
                            ToastX.show("未检测到淘宝客户端");
                            break;
                    }
                }
                return true;
            }
            return true;
        }
        return false;
    }


    public static void onDestroy(WebView webView) {
        if (webView == null)
            return;
        try {
            try {
                ((ViewGroup) webView.getParent()).removeView(webView);
            } catch (Exception e) {

            }
            webView.stopLoading();
            webView.getSettings().setJavaScriptEnabled(false);
            webView.clearHistory();
            webView.clearAnimation();
            webView.loadUrl("about:blank");
            webView.clearView();
            webView.removeAllViews();
            webView.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


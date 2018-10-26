package com.march.common.exts;


import com.march.common.pool.BytesPool;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/**
 * CreateAt : 2017/12/8
 * Describe : IO流
 *
 * @author chendong
 */
public class StreamX {


    /**
     * 打开一个网络流
     *
     * @param conn 网络连接
     * @return 流
     * @throws IOException error
     */
    public static InputStream openHttpStream(HttpURLConnection conn) throws IOException {
        conn.setRequestMethod("GET");
        conn.setReadTimeout(3_000);
        conn.setConnectTimeout(3_000);
        conn.setDoOutput(false);
        conn.setDoInput(true);
        // 设置通用的请求属性
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("connection", "Keep-Alive");
        // 发起连接
        conn.connect();
        return conn.getInputStream();
    }


    /**
     * 保存文件到
     *
     * @param file 文件
     * @param is   流
     * @return file
     */
    public static File saveStreamToFile(File file, InputStream is) {
        if (file == null || is == null) {
            return file;
        }
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(new FileOutputStream(file));
            byte[] bs = BytesPool.get().getBytes();
            int len;
            while ((len = bis.read(bs)) != -1) {
                bos.write(bs, 0, len);
                bos.flush();
            }
            BytesPool.get().releaseBytes(bs);
        } catch (Exception e) {
            LogX.e(e);
            return null;
        } finally {
            RecycleX.recycle(bis, bos);
        }
        return file;
    }


    public static byte[] saveStreamToBytes(InputStream is) {
        BufferedInputStream bis = null;
        ByteArrayOutputStream bos = null;
        byte[] result;
        try {
            bis = new BufferedInputStream(is);
            bos = new ByteArrayOutputStream();
            byte[] bs = BytesPool.get().getBytes();

            int len;
            while ((len = bis.read(bs)) != -1) {
                bos.write(bs, 0, len);
                bos.flush();
            }
            result = bos.toByteArray();
            BytesPool.get().releaseBytes(bs);
        } catch (Exception e) {
            LogX.e(e);
            return null;
        } finally {
            RecycleX.recycle(bis, bos);
        }
        return result;
    }

    /**
     * 从流中读取为字符串
     *
     * @param is 流
     * @return json
     */
    public static String saveStreamToString(InputStream is) {
        BufferedReader br = null;
        String json = null;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            json = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            RecycleX.recycle(br);
        }
        return json;
    }
}

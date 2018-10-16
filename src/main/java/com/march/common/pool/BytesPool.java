package com.march.common.pool;

import android.util.Log;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * CreateAt : 2018/4/3
 * Describe : byte[] 内存复用，参考
 *
 * @author chendong
 */
public class BytesPool {

    private static final String TAG = "ByteArrayPool";
    // 64 KB.
    private static final int TEMP_BYTES_SIZE = 64 * 1024;
    // 512 KB.
    private static final int MAX_SIZE = 2 * 1048 * 1024;
    private static final int MAX_BYTE_ARRAY_COUNT = MAX_SIZE / TEMP_BYTES_SIZE;
    private static final BytesPool BYTE_ARRAY_POOL = new BytesPool();
    private final Queue<byte[]> tempQueue = new ArrayDeque<>(0);



    private BytesPool() {
    }

    public static BytesPool get() {
        return BYTE_ARRAY_POOL;
    }

    public void clear() {
        synchronized (tempQueue) {
            tempQueue.clear();
        }
    }

    public byte[] getBytes() {
        byte[] result;
        synchronized (tempQueue) {
            result = tempQueue.poll();
        }
        if (result == null) {
            result = new byte[TEMP_BYTES_SIZE];
            if (Log.isLoggable(TAG, Log.DEBUG)) {
                Log.d(TAG, "Created temp bytes");
            }
        }
        return result;
    }

    public boolean releaseBytes(byte[] bytes) {
        if (bytes == null) {
            return false;
        }
        if (bytes.length != TEMP_BYTES_SIZE) {
            return false;
        }

        boolean accepted = false;
        synchronized (tempQueue) {
            if (tempQueue.size() < MAX_BYTE_ARRAY_COUNT) {
                accepted = true;
                tempQueue.offer(bytes);
            }
        }
        return accepted;
    }

    public int size() {
        return tempQueue.size();
    }
}

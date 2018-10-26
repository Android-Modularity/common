package com.march.common.pool;

import com.march.common.able.Releasable;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * CreateAt : 2018/4/3
 * Describe : byte[] 内存复用，参考
 *
 * @author chendong
 */
public class BytesPool2 implements Releasable {

    private static final String TAG             = "ByteArrayPool";
    // 64 KB.
    private static final int    DEF_BUFFER_SIZE = 64 * 1024;
    private static final int    DEF_MAX_COUNT   = 20;

    private final Queue<byte[]> bufferQueue = new ArrayDeque<>(0);

    private int bufferSize; // 单个缓冲区大小
    private int maxCount; // 做多允许的缓冲区个数，超过以后 release 时会被回收掉
    private int maxSize; // 根据单个缓冲区和缓冲区个数获得的最大缓冲大小

    public BytesPool2(int bufferSize, int maxCount) {
        this.bufferSize = bufferSize;
        this.maxCount = maxCount;
        this.maxSize = bufferSize * maxCount;
    }

    public BytesPool2() {
        this(DEF_BUFFER_SIZE, DEF_MAX_COUNT);
    }

    /**
     * 获取 bytes
     *
     * @return 如果队列中有可复用的，则取出复用，没有则创建新的
     */
    public byte[] getBytes() {
        byte[] result;
        synchronized (bufferQueue) {
            result = bufferQueue.poll();
        }
        if (result == null) {
            result = new byte[bufferSize];
        }
        return result;
    }

    /**
     * 回收 bytes
     *
     * @param bytes 获取到的 bytes
     * @return 是否被回收到了队列中
     */
    public boolean recycleBytes(byte[] bytes) {
        if (bytes == null) {
            return false;
        }
        if (bytes.length != bufferSize) {
            return false;
        }

        boolean accepted = false;
        synchronized (bufferQueue) {
            if (bufferQueue.size() < maxCount) {
                accepted = true;
                bufferQueue.offer(bytes);
            }
        }
        return accepted;
    }

    public int size() {
        return bufferQueue.size();
    }

    public int getMaxSize() {
        return maxSize;
    }

    @Override
    public void release() {
        synchronized (bufferQueue) {
            bufferQueue.clear();
        }
    }
}

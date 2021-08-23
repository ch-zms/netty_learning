package com.netty.learning.demo01;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 让文件在内存堆外进行修改
 *
 * @author ch
 * @date 2021-07-15 10:55
 * @desc
 */
public class Demo03 {
    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("./1.txt", "rw");

        FileChannel fileChannel = randomAccessFile.getChannel();

        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 10);
        mappedByteBuffer.clear();
        mappedByteBuffer.put(0,(byte)'H');
        mappedByteBuffer.put(3,(byte)'O');

        randomAccessFile.close();
    }
}

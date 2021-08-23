package com.netty.learning.demo01;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 将1.txt文件读取写入2.txt
 *
 * @author ch
 * @date 2021-07-15 10:55
 * @desc
 */
public class Demo01 {
    public static void main(String[] args) throws IOException {
        FileInputStream inputStream01=new FileInputStream("./1.txt");
        FileOutputStream outputStream02 = new FileOutputStream("./2.txt");
        FileChannel fileChannel01=inputStream01.getChannel();
        FileChannel fileChannel02= outputStream02.getChannel();
        ByteBuffer byteBuffer=ByteBuffer.allocate(512);
        while (true){
            byteBuffer.clear();
            int read = fileChannel01.read(byteBuffer);
            System.out.println("read:"+read);
            if(read == -1){
                break;
            }
            byteBuffer.flip();
            fileChannel02.write(byteBuffer);
        }
        fileChannel01.close();
        fileChannel02.close();

        inputStream01.close();
        outputStream02.close();

    }
}

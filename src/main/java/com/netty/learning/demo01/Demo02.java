package com.netty.learning.demo01;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * 将图片复制
 *
 * @author ch
 * @date 2021-07-15 10:55
 * @desc
 */
public class Demo02 {
    public static void main(String[] args) throws IOException {
        FileInputStream inputStream01=new FileInputStream("./img1.png");
        FileOutputStream outputStream02 = new FileOutputStream("./img2.png");
        FileChannel fileChannel01=inputStream01.getChannel();
        FileChannel fileChannel02= outputStream02.getChannel();
        //使用transferFrom拷贝图片
        fileChannel02.transferFrom(fileChannel01, 0, fileChannel01.size());

        fileChannel01.close();
        fileChannel02.close();

        inputStream01.close();
        outputStream02.close();

    }
}

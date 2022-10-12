package com.briup.smart.env.util;

import org.junit.Test;

import java.io.File;

/**
 * @author dell
 */
public class FileMax {
    //查看当前文件已经处理完的数据是多大
    @Test
    public void getMax(){
        File file= new File("src/main/resources/data-file-simple");
        System.out.println(file.length());
    }
}

package com.briup.smart.env.util;

import com.briup.smart.env.client.Gather;
import com.briup.smart.env.client.GatherImpl;
import com.briup.smart.env.entity.Environment;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.File;
import java.util.Collection;

/**
 * @author dell
 */
public class BackupTest {
    //测试backup实现类的两个方法
    @Test
    public void test0() throws Exception {
        Backup b = new BackupImpl();
       /* File file = new File("src/main/resources/data-file-simple");
        b.store("src/main/resources/backup",file.length(),false);*/
        Long load = (Long) b.load("src/main/resources/backup", false);
        System.out.println(load);
    }
    //测试gether方法里面对于backup实现类的两个方法运用是否正确
    //第一次读的时候，应该读到的是最初的1000条数据，循环第二次时，跳过了前面已经处理了的1000条，因此读入的数据应该是0条；
    @Test
    public void  test1() throws Exception {
        Gather g = new GatherImpl();
        Collection<Environment> gather = g.gather();
        System.out.println(gather.size());
    }

}

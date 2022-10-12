package com.briup.smart.env.util;

import com.sun.corba.se.impl.orbutil.ObjectWriter;

import java.io.*;

/**
 * @author dell
 */
public class BackupImpl implements Backup{
/*作用：读取备份文件的大小，便于对于已经处理过的数据不再进行二次处理
  参数说明：第一个参数传入的是同类store（）方法新建的那个文件路径，即备份文件的路径；第二个参数是读取备份文件后是否删除备份问价，默认是false；
  返回值说明：返回的是传入路径的文件的大小，即已经处理过的数据大小；
  注意：因为load第一次被调用的时候，store方法还没有被调用，因此备份文件还没创建，所以需要判断文件是否存在，作处理
  使用时机：GatherImpl的gather方法准备读取文件数据之前；
  */
    @Override
    public Object load(String fileName, boolean del) throws Exception {
        Log log = new LogImpl();
        File file = new File(fileName);
        if(!file.exists()){
            return null;
        }
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
        Object o = in.readObject();

        if(del){
            file.delete();
        }
        log.info("已获取到备份文件内容");
        return o;
    }
/*
作用：把已经处理好的文件大小存到备份文件里面；
参数说明：第一个是备份文件的路径（不存在会自动创建），第二个数已经处理好了的文件大小（字节long类型），第三个是否追加，默认是false
使用时机：在GatherImpl的gather方法文件数据处理完成后；
 */
    @Override
    public void store(String fileName, Object obj, boolean append) throws Exception {
        File file = new File(fileName);
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file,append));
        out.writeObject(obj);

        out.flush();
        out.close();
    }
}

package com.briup.smart.env.client;

import com.briup.smart.env.Configuration;
import com.briup.smart.env.entity.Environment;
import com.briup.smart.env.support.ConfigurationAware;
import com.briup.smart.env.support.PropertiesAware;
import com.briup.smart.env.util.Log;
import com.briup.smart.env.util.LogImpl;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.Properties;

/**
 * @version 1.0
 */
public class ClientImpl implements Client, ConfigurationAware, PropertiesAware {
    /*
    作用：这是客户端，将GatherImpl的gather方法获取的集合，发送到服务器端；
     */
    Log log =null;
    String host ;
     int  port ;
    @Override
    public void send(Collection<Environment> c) throws Exception {
        //Socket socket=new Socket("127.0.0.1", 8899);
        Socket socket=new Socket(host, port);
      /*  System.out.println("服务器已经连接...");
        System.out.println("准备发送文件...");*/
        log.info("服务器已经连接,准备发送文件...");
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream oos=new ObjectOutputStream(outputStream);
        oos.writeObject(c);
        oos.close();
       // System.out.println("文件发送完毕...");
        log.info("文件发送完毕...");
    }

    @Override
    public void setConfiguration(Configuration configuration) throws Exception {
        this.log = configuration.getLogger();
    }

    @Override
    public void init(Properties properties) throws Exception {
        this.host = properties.getProperty("host");
        this.port = Integer.parseInt(properties.getProperty("port"));
    }
}

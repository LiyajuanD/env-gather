package com.briup.smart.env.server;

import com.briup.smart.env.Configuration;
import com.briup.smart.env.ConfigurationImpl;
import com.briup.smart.env.entity.Environment;
import com.briup.smart.env.support.ConfigurationAware;
import com.briup.smart.env.support.PropertiesAware;
import com.briup.smart.env.util.Log;
import com.briup.smart.env.util.LogImpl;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

/**
 * @author dell
 *
 */
//服务端
    //客户端的测试类在src/test/java/com/briup/smart/env/client/gatherTest.java的test03
    //服务器的测试类在src/test/java/com/briup/smart/env/client/serverTest.java的test01
    //注意：先开服务器，再开客户端；
public class ServerImpl implements Server , ConfigurationAware, PropertiesAware {
    //接收客户端发送的集合数据
    ServerSocket serverSocket=null;
   // Log log = new LogImpl();
    Log log = null;
    public int serverPort ;
    int stopPort;
    DBStore dbStore;
    @Override
    public void reciver() throws Exception {
        //1.开启一个服务serversocket
        //serverSocket = new ServerSocket(8899);
        log.info("等待客户端的监听,服务端的端口号是"+serverPort);
        serverSocket = new ServerSocket(serverPort);
        //System.out.println("服务器已启动");
        //2.接收连接，accept
        //while ()循环监听客户端多线程
        toshutsown();
        while (true){
            log.info("服务器等待连接");
            Socket socket = serverSocket.accept();
            //System.out.println("接收成功");
            log.info("客户端连接成功");
            //3.接收客户端发送过来的对象流
            InputStream is = socket.getInputStream();
            ObjectInputStream in = new ObjectInputStream(is);
            //强转为发送时的数据，
            Collection<Environment> o = (Collection<Environment>)in.readObject();
            //4.打印对象的长度
            //System.out.println();
            log.info("接收到的长度为"+o.size());
            dbStore.saveDB(o);
        }
    }
    //关闭服务器，开启新线程，有客户端连接时关闭主服务器，是一个类似于flag的存在
    public void toshutsown(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    log.info("等待断开服务监听，端口号是"+stopPort);
                   // ServerSocket serverSocket = new ServerSocket(9999);
                    ServerSocket serverSocket = new ServerSocket(stopPort);
                    //监听9999客户端的连接，
                    serverSocket.accept();
                    shutdown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
     }
     //关闭主线程，
    @Override
    public void shutdown() throws Exception {
        serverSocket.close();
    }

    @Override
    public void setConfiguration(Configuration configuration) throws Exception {
        this.dbStore = configuration.getDbStore();
        this.log=configuration.getLogger();
    }

    @Override
    public void init(Properties properties) throws Exception {
        this.serverPort=Integer.parseInt(properties.getProperty("server-port"));
        this.stopPort =Integer.parseInt(properties.getProperty("stop-port"));
    }
}

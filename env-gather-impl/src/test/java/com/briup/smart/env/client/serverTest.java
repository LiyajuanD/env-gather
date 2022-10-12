package com.briup.smart.env.client;

import com.briup.smart.env.Configuration;
import com.briup.smart.env.ConfigurationImpl;
import com.briup.smart.env.server.Server;
import com.briup.smart.env.server.ServerImpl;
import org.junit.Test;

import java.net.Socket;

/**
 * @author dell
 */
public class serverTest {
//    开启服务器
    @Test
    public void test01() throws Exception {
        //之前创建对象
        /*Server server = new ServerImpl();
        server.reciver();*/
        //之后
        Configuration instance = ConfigurationImpl.getInstance();
        Server server = instance.getServer();
        server.reciver();
    }
//关闭服务器的测试
    @Test
    public void test2() throws Exception {
        new Socket("127.0.0.1",9999);
    }
}

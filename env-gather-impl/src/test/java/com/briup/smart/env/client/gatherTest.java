package com.briup.smart.env.client;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.briup.smart.env.Configuration;
import com.briup.smart.env.ConfigurationImpl;
import com.briup.smart.env.entity.Environment;
import com.briup.smart.env.server.DBStore;
import com.briup.smart.env.server.DBStoreImpl;
import com.briup.smart.env.server.ServerImpl;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;

/**
 * @author dell
 */
public class gatherTest {
    @Test
    public void test1() throws Exception {
        Gather gather = new GatherImpl();
        Collection<Environment> gather1 = gather.gather();
       /* Client client= new ClientImpl();
        client.send(gather1);*/
        for (Environment environment : gather1) {
            System.out.println(environment);
            //测试了一下获取日期和表名拼接是否正确
         /*   Timestamp gather_date = environment.getGather_date();
            DBStoreImpl db = new DBStoreImpl();
            int getday = db.getday(gather_date);
            System.out.println(getday);*/
        }
    }
    @Test
    public  void test2(){
        String s = "5d686f9802";
        String s1= s.substring(0,4);
        System.out.println(s1);
    }

    //测试从gather的实现类获取集合，用客户端的send方法发送到服务器（测试服务器和客户端时用）
    @Test
    public void test3() throws Exception {
       Gather gath = new GatherImpl();
       Client client = new ClientImpl();
       Collection<Environment> gather = gath.gather();
       client.send(gather);
    }
//测试获取日期
    @Test
    public void getday(){
        Timestamp timestamp = new Timestamp(1516323601926L);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timestamp);
        int i = calendar.get(Calendar.DAY_OF_MONTH);
        System.out.println(i);
    }
//测试持久化数据
    @Test
    public void test4() throws Exception {
        Gather gath = new GatherImpl();
        Collection<Environment> gather = gath.gather();
        DBStore db = new DBStoreImpl();
        db.saveDB(gather);
    }

    @Test
    public void test5(){
        Configuration instance = ConfigurationImpl.getInstance();
        ClientImpl client = new ClientImpl();
        System.out.println(client.host);
        System.out.println(client.port);
    }
}

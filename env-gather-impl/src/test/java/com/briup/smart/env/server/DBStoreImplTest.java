package com.briup.smart.env.server;

import com.briup.smart.env.client.Gather;
import com.briup.smart.env.client.GatherImpl;
import com.briup.smart.env.entity.Environment;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jxc
 * @version 1.0
 * @description TODD
 * @date 2022年08月25日 16:05
 */
public class DBStoreImplTest {
    @Test
    public void testDBStore() throws Exception {
        DBStore dbStore=new DBStoreImpl();
        Gather gatherData=new GatherImpl();
//        List<Environment> list =new ArrayList<>();

//        100|101|2|16|1|3|5d606f7802|1|1516323596029
//        list.add(new Environment("温度","100","101","2","16",1,"3",1,0,new Timestamp(Long.parseLong("1516323596029"))));
        dbStore.saveDB(gatherData.gather());
//        Timestamp timestamp=new Timestamp(Long.parseLong("1516323596029"));
//        System.out.println(timestamp);
//        System.out.println(timestamp.toString().substring(8, 10));
    }
}

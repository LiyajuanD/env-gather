package com.briup.smart.env.server;

import org.junit.Test;

/**
 * @author jxc
 * @version 1.0
 * @description TODD
 * @date 2022年08月25日 17:02
 */
public class ServerImplTest {

    @Test
    public void testReciver() throws Exception {
        Server server=new ServerImpl();
        server.reciver();
    }
}

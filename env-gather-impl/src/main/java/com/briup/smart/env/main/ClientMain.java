package com.briup.smart.env.main;

import com.briup.smart.env.Configuration;
import com.briup.smart.env.ConfigurationImpl;
import com.briup.smart.env.client.Client;
import com.briup.smart.env.entity.Environment;

import java.util.Collection;

//客户端入口类
public class ClientMain {
	public static void main(String[] args) throws Exception {
		Configuration instance = ConfigurationImpl.getInstance();
		Client client = instance.getClient();
		Collection<Environment> gather = instance.getGather().gather();
		client.send(gather);
	}
}

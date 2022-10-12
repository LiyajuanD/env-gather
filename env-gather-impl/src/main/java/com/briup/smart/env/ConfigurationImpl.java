package com.briup.smart.env;

import com.briup.smart.env.client.Client;
import com.briup.smart.env.client.Gather;
import com.briup.smart.env.server.DBStore;
import com.briup.smart.env.server.Server;
import com.briup.smart.env.support.ConfigurationAware;
import com.briup.smart.env.support.PropertiesAware;
import com.briup.smart.env.util.Backup;
import com.briup.smart.env.util.Log;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import javax.xml.parsers.SAXParser;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author dell
 * 获取对象
 */
public class ConfigurationImpl implements Configuration{
    //存放子标签的姓名和属性
      public static Map<String ,Object> map = new HashMap<>();
      public static Properties properties = new Properties();
      //这个类只需要创建一个对象，用到单例模式
      private static Configuration configuration = new ConfigurationImpl();
      public static  Configuration getInstance(){
          return configuration;
      }
      private ConfigurationImpl(){
          //私有构造 防止直接创建对象
      }
      // parseXml()方法只需要执行一次，放在静态代码块里面
      static {

          try {
              parseXml();
              initMould();
          } catch (Exception e) {
              e.printStackTrace();
          }
      }
      public static void parseXml(){
          SAXReader reader = new SAXReader();
          Document document =null;
          try {
                document = reader.read("env-gather-impl/src/main/resources/conf.xml");
                Element root = document.getRootElement();
                List<Element> elements = root.elements();
                //遍历所以子节点
                for (Element ele : elements) {
                    //反射创建对象
                       //Object obj = Class.forName(ele.attributeValue("class")).newInstance();
                       //获取节点名
                    String key = ele.getName();
                    Attribute attr = ele.attribute("class");
                    //根据值生成对象
                    Object instance = Class.forName(attr.getValue()).newInstance();
                       //放入集合
                       map.put(key,instance);
                       //获取当前标签的所有子标签
                       List<Element> child = ele.elements();
                       for (Element element : child) {
                           //放入到配置文件
                            properties.setProperty(element.getName(),element.getText());
                        }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void initMould() throws Exception {
          //map存放了所有的实现类对象，遍历所有的集合，若是某个对象实现了接口，就去调用方法，
        for (Object value : map.values()) {
            if (value instanceof ConfigurationAware) {
                    ((ConfigurationAware) value).setConfiguration(configuration);
            }
            if(value instanceof PropertiesAware){
                ((PropertiesAware) value).init((properties));
            }
        }
    }
    @Override
    public Log getLogger() throws Exception {
        return (Log) map.get("logger");
    }

    @Override
    public Server getServer() throws Exception {
        return (Server) map.get("server");
    }

    @Override
    public Client getClient() throws Exception {
        return (Client) map.get("client");
    }

    @Override
    public DBStore getDbStore() throws Exception {
        return (DBStore) map.get("dbStore");
    }

    @Override
    public Gather getGather() throws Exception {
        return (Gather) map.get("gather");
    }

    @Override
    public Backup getBackup() throws Exception {
        return (Backup) map.get("backup");
    }
}

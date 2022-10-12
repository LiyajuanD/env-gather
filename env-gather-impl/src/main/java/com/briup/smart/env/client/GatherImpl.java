package com.briup.smart.env.client;

import com.briup.smart.env.Configuration;
import com.briup.smart.env.ConfigurationImpl;
import com.briup.smart.env.entity.Environment;
import com.briup.smart.env.support.ConfigurationAware;
import com.briup.smart.env.support.PropertiesAware;
import com.briup.smart.env.util.Backup;
import com.briup.smart.env.util.BackupImpl;
import com.briup.smart.env.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;


/**
 * @author dell
 *  * Gather接口是物联网数据中心项目采集模块的规范
 *  * 该模块对物联网数据中心项目环境信息进行采集
 *  * 将采集的数据封装成Collection<Environment>集合
 */
public class GatherImpl implements Gather ,ConfigurationAware,PropertiesAware{
   //String filepath= "env-gather-impl/src/main/resources/data-file-simple";
   String filepath;
   String backupFilePath;
    List<Environment> list = new ArrayList<>();
    Environment environment =null;
    Reader fileReader=null;
    BufferedReader reader=null;
    Backup backup =null;
    Log log;
    @Override
    public  Collection<Environment> gather() throws Exception {
        //Backup backup = new BackupImpl();
        File file = new File(filepath);
        //1.读取文件（在resources文件夹下的datafilesample文件）
        fileReader= new FileReader(file);
        //1.1 逐行读取
        reader = new BufferedReader(fileReader);
        //(跳过多少字节)
        //Long len = (Long) backup.load("env-gather-impl/src/main/resources/backup", Backup.LOAD_UNREMOVE);
        Long len = (Long) backup.load(backupFilePath, Backup.LOAD_UNREMOVE);
        if(len!=null){
            //skip方法是在文件中跳过指定字节后读取；
            log.info("跳过" + len +"字节后读取");
            reader.skip(len);
        }
        //具体业务操作，封装environment对象并加入集合
        String str;
        while ((str=reader.readLine())!=null){
            String []arr = str.split("[|]");
            if(arr.length!=9) {
                continue;
            }
            environment = new Environment();
            //发送端id
            environment.setSrcId(arr[0]);
            //树莓派系统id
            environment.setDesId(arr[1]);
            //实验箱区域模块id(1-8)
            environment.setDevId(arr[2]);
            //模块上传感器地址
            environment.setSersorAddress(arr[3]);
            //环境种类名称
            //传感器个数
            environment.setCount(Integer.parseInt(arr[4]));
            //发送指令标号 3表示接收数据 16表示发送命令
            environment.setCmd(arr[5]);
            //环境值
            //状态 默认1表示成功
            environment.setStatus(Integer.parseInt(arr[7]));
            //采集时间
            environment.setGather_date(new Timestamp(Long.parseLong(arr[8])));
            Environment copy = copy(environment);
            if("16".equals(arr[3])){
                environment.setName("温度");
                copy.setName("湿度");
                //温度
                temperature(arr[6],environment);
                //湿度
                humidity(arr[6],copy);
            }else if("256".equals(arr[3])){
                environment.setName("光照强度");
                co2Light(arr[6],environment);
            }else if("1280".equals(arr[3])){
                environment.setName("二氧化碳");
                co2Light(arr[6],environment);
            }
            list.add(copy);
            list.add(environment);
        }
        //此时调用该方法，是将刚处理完的文件大小，备份到指定文件中，以便于再次读取时跳过处理好的数据；
        //backup.store("env-gather-impl/src/main/resources/backup",file.length(),Backup.STORE_OVERRIDE);
        backup.store(backupFilePath,file.length(),Backup.STORE_OVERRIDE);
        log.info("文件已备份至"+backupFilePath);
        return list;
    }

    //温度和湿度是不同对象，需要创建新对象，此时除了名字其他的属性都相同
    public  Environment copy(Environment e) throws Exception {
            Environment environment = new Environment();
            //发送端id
            environment.setSrcId(e.getSrcId());
            //树莓派系统id
            environment.setDesId(e.getDesId());
            //实验箱区域模块id(1-8)
            environment.setDevId(e.getDevId());
            //模块上传感器地址
            environment.setSersorAddress(e.getSersorAddress());
            //传感器个数
            environment.setCount(e.getCount());
            //发送指令标号 3表示接收数据 16表示发送命令
            environment.setCmd(e.getCmd());
            //状态 默认1表示成功
            environment.setStatus(e.getStatus());
            //采集时间
            environment.setGather_date(e.getGather_date());
        return environment;
    }
    //二氧化碳和光照强度
    public void co2Light(String s,Environment e){
        String da = s.substring(0,4);
        e.setData(Integer.parseInt(da,16));
    }
    //温度
    public void temperature(String s,Environment e){
        String da = s.substring(0,4);
        int data = Integer.parseInt(da,16);
        float end= ((data*(0.00268127F))-46.85F);
        e.setData(end);
    }
    //湿度
    public void humidity(String s,Environment e){
        String da = s.substring(4,8);
        int data = Integer.parseInt(da,16);
        float end = (data*0.00190735F)-6;
        e.setData(end);
    }
    @Override
    public void setConfiguration(Configuration configuration) throws Exception {
        this.backup = configuration.getBackup();
        this.log = configuration.getLogger();
    }

    @Override
    public void init(Properties properties) throws Exception {
        this.filepath = properties.getProperty("data-file-path");
        this.backupFilePath = properties.getProperty("file-path");
    }
   /* public String getStr(String s,int a,int b){
        String str = s.substring(a,b);
        return str;
    }*/
}

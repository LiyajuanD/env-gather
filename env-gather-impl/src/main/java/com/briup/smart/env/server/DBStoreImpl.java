package com.briup.smart.env.server;

import com.briup.smart.env.Configuration;
import com.briup.smart.env.entity.Environment;
import com.briup.smart.env.support.ConfigurationAware;
import com.briup.smart.env.support.PropertiesAware;
import com.briup.smart.env.util.Log;

import java.sql.*;
import java.util.Calendar;
import java.util.Collection;
import java.util.Properties;


/**
 * @author dell
 *  * DBStore接口是物联网数据中心项目入库模块的规范
 *  * 该模块负责对Environment集合进行持久化操作
 */
public class DBStoreImpl implements DBStore , ConfigurationAware, PropertiesAware {
    Connection connection=null;
    //String url = "jdbc:oracle:thin:@127.0.0.1:1521:XE";
    //String user ="lyj";
    //String password = "lyj123";
    // String driver="oracle.jdbc.OracleDriver";
    String url;
    String user;
    String password;
    String driver;
    Log log;
    @Override
    public void saveDB(Collection<Environment> coll) throws Exception {
        log.info("进行save数据的持久化操作");
        Class.forName(driver);
        connection = DriverManager.getConnection(url, user, password);
        for (Environment c : coll) {
            //PreparedStatement statement = getStatement(environment);
            Timestamp gather_date =c.getGather_date();
            //拼接表名
            int day = getday(gather_date);
            String sql = "insert into E_DETAIL_" + day
                    + " values(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,c.getName());
            statement.setString(2,c.getSrcId());
            statement.setString(3,c.getDesId());
            statement.setString(4,c.getDevId());
            statement.setString(5,c.getSersorAddress());
            statement.setInt(6,c.getCount());
            statement.setString(7,c.getCmd());
            statement.setInt(8,c.getStatus());
            statement.setFloat(9,c.getData());
            statement.setTimestamp(10,c.getGather_date());
            statement.executeUpdate();
        }
        log.info("数据插入完毕，已实现数据的持久化操作");
    }
    //获取环境对象的Timestamp gather_date属性，转换为毫秒数，然后获取对应的日期，与表名拼接，添加数据到对应的表中；
    public int getday(Timestamp timestamp){
        Timestamp times = new Timestamp(Long.parseLong(String.valueOf(timestamp.getTime())));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(times);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public void setConfiguration(Configuration configuration) throws Exception {
        this.log=configuration.getLogger();
    }

    @Override
    public void init(Properties properties) throws Exception {
        this.driver=properties.getProperty("driver");
        this.password = properties.getProperty("password");
        this.url=properties.getProperty("url");
        this.user = properties.getProperty("username");
    }
}

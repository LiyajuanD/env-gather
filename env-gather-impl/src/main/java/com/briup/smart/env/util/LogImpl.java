package com.briup.smart.env.util;

import org.apache.log4j.Logger;

/**
 * @author dell
 */
public class LogImpl implements Log{
    //获取日志对象，log是org.apache.log4j.Logger下面的，获取相应的日志级别，调用相应的方法
    Logger logger = Logger.getRootLogger();
    @Override
    public void debug(String message) {
        logger.debug(message);
    }

    @Override
    public void info(String message) {
        logger.info(message);
    }

    @Override
    public void warn(String message) {
        logger.warn(message);
    }

    @Override
    public void error(String message) {
        logger.error(message);
    }

    @Override
    public void fatal(String message) {
        logger.fatal(message);
    }
}

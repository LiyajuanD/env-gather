package com.briup.smart.env.util;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * @author dell
 */
public class LogImplTest {
    @Test
    public void test2(){
        Logger logger = Logger.getRootLogger();
        logger.fatal("打印了fatal日志");
        logger.error("打印了error日志");
        logger.warn("打印了warn日志");
        logger.info("打印了info日志");
        logger.debug("打印了debug日志");
        logger.trace("打印了trace日志");
    }
}

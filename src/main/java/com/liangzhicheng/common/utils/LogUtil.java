package com.liangzhicheng.common.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogUtil {

    private static final Logger logger = LogManager.getLogger(LogUtil.class);

    public static void info(String message, Object ... object){
        logger.info(message, object);
    }

    public static void warn(String message, Object ... object){
        logger.warn(message, object);
    }

    public static void error(String message, Object ... object){
        logger.error(message, object);
    }

}

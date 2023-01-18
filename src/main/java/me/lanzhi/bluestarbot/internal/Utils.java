package me.lanzhi.bluestarbot.internal;

import me.lanzhi.api.reflect.URLClassLoaderAccessor;
import me.lanzhi.api.util.LoggerUtils;
import me.lanzhi.bluestarbot.api.Internal;

import java.util.logging.Logger;

/**
 * 管理器
 */
@Internal
public class Utils
{

    private static URLClassLoaderAccessor classLoaderAccessor;

    public static URLClassLoaderAccessor classLoaderAccessor()
    {
        return classLoaderAccessor;
    }

    public static void setClassLoaderAccess(URLClassLoaderAccessor classLoaderAccessor)
    {
        Utils.classLoaderAccessor=classLoaderAccessor;
    }

    private static LoggerUtils logger;

    public static LoggerUtils logger()
    {
        return logger;
    }

    public static void setLogger(Logger logger)
    {
        Utils.logger=new LoggerUtils(logger);
    }
}

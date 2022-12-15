package me.lanzhi.bluestarbot.internal;

import java.util.logging.Logger;

/**
 * 目前暂时只存放Logger
 */
public class Utils
{
    private static Logger logger;

    public static Logger logger()
    {
        return logger;
    }

    public static void setLogger(Logger logger)
    {
        Utils.logger=logger;
    }
}

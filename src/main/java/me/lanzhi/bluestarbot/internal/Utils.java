package me.lanzhi.bluestarbot.internal;

import java.util.logging.Logger;

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

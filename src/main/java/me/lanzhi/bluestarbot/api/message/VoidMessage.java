package me.lanzhi.bluestarbot.api.message;

/**
 * 一些目前无法被解析(更别提构造)的消息
 */
public interface VoidMessage extends Message
{
    Type getType();

    enum Type
    {
        /**
         * 小程序
         */
        LightAPP,
        /**
         * 服务消息
         */
        ServiceMessage,
        /**
         * 未知的消息类型
         */
        Unknown
    }
}

package me.lanzhi.bluestarbot.api.message;

import me.lanzhi.bluestarbot.internal.message.TextImpl;

/**
 * 一个纯文本消息
 */
public interface Text extends Message
{
    /**
     * @param text 纯文本消息的显示文本
     * @return 创建出的消息
     */
    static Text create(String text)
    {
        return new TextImpl(text);
    }
}

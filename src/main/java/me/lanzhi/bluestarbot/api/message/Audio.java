package me.lanzhi.bluestarbot.api.message;

import me.lanzhi.bluestarbot.api.contact.SendAudioAble;
import me.lanzhi.bluestarbot.internal.message.AudioImpl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 一条语音
 */
public interface Audio extends Message
{
    /**
     * 创建一个语音
     * 注,若创建时的聊天与实际发送时不同,可能出现问题
     *
     * @param file    语音文件
     * @param contact 聊天
     * @return 语音
     */
    static Audio create(File file,SendAudioAble contact)
    {
        return AudioImpl.create(file,contact);
    }

    /**
     * 创建一个语音
     * 注,若创建时的聊天与实际发送时不同,可能出现问题
     *
     * @param stream  语音文件
     * @param contact 聊天
     * @return 语音
     */
    static Audio create(InputStream stream,SendAudioAble contact) throws IOException
    {
        return AudioImpl.create(stream,contact);
    }

    /**
     * @return 语音下载链接,自己创建的语音则返回null
     */
    String getDownloadURL();

    /**
     * @return 文件大小
     */
    long getSize();

    /**
     * @return MD5
     */
    byte[] md5();

    /**
     * @return 文件名
     */
    String fileName();

    /**
     * @return 语音长度,自己创建的为null
     */
    Long length();
}

package me.lanzhi.bluestarbot.api.message;

import me.lanzhi.bluestarbot.api.contact.Contact;
import me.lanzhi.bluestarbot.internal.message.ImageImpl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface Image extends Message
{
    /**
     * 创建一个图片
     * 注,若创建时的聊天与实际发送时不同,可能出现问题
     *
     * @param file    图片文件
     * @param contact 聊天
     * @return 图片
     */
    static Image create(File file,Contact contact)
    {
        return ImageImpl.create(file,contact);
    }

    /**
     * 创建一个图片
     * 注,若创建时的聊天与实际发送时不同,可能出现问题
     *
     * @param stream  图片
     * @param contact 聊天
     * @return 图片
     */
    static Image create(InputStream stream,Contact contact) throws IOException
    {
        return ImageImpl.create(stream,contact);
    }

    /**
     * @return 图片ID
     */
    String getImageId();

    /**
     * @return 下载链接
     */
    String getDownloadURL();
}

package me.lanzhi.bluestarbot.api.message;

import me.lanzhi.bluestarbot.api.contact.Contact;
import me.lanzhi.bluestarbot.internal.message.FlashImageImpl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 闪照,创建方法和相关方法与普通图片无异,只在官方客户端有不同
 *
 * @see Image 图片
 */
public interface FlashImage extends Message, Image
{
    /**
     * @see Image 图片
     */
    static FlashImage create(File file,Contact contact)
    {
        return FlashImageImpl.create(file,contact);
    }

    /**
     * @see Image 图片
     */
    static FlashImage create(InputStream stream,Contact contact) throws IOException
    {
        return FlashImageImpl.create(stream,contact);
    }

    /**
     * @see Image 图片
     */
    String getImageId();

    /**
     * @see Image 图片
     */
    String getDownloadURL();
}

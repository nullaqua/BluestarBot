package me.lanzhi.bluestarbot.api.message;

import me.lanzhi.bluestarbot.api.contact.Contact;
import me.lanzhi.bluestarbot.internal.message.FlashImageImpl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 闪照,创建方法和相关方法与普通图片无异,只在官方客户端有不同
 *
 * @see Image
 */
public interface FlashImage extends Message, Image
{
    /**
     * @see Image
     */
    static FlashImage create(File file,Contact contact)
    {
        return FlashImageImpl.create(file,contact);
    }

    /**
     * @see Image
     */
    static FlashImage create(InputStream stream,Contact contact) throws IOException
    {
        return FlashImageImpl.create(stream,contact);
    }

    /**
     * @see Image
     */
    String getImageId();

    /**
     * @see Image
     */
    String getDownloadURL();
}

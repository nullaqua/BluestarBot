package me.lanzhi.bluestarbot.internal.message;

import me.lanzhi.bluestarbot.api.Internal;
import me.lanzhi.bluestarbot.api.contact.Contact;
import me.lanzhi.bluestarbot.api.message.FlashImage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Internal
public abstract class FlashImageImpl implements FlashImage, MessageImpl
{
    public static FlashImageImpl create(File file,Contact contact)
    {
        return new Custom(file,contact);
    }

    public static FlashImageImpl create(InputStream stream,Contact contact) throws IOException
    {
        return new Custom(stream,contact);
    }

    public static FlashImageImpl create(net.mamoe.mirai.message.data.FlashImage flashImage)
    {
        return new FromMirai(flashImage);
    }

    @Override
    public String getImageId()
    {
        return toMirai().getImage().getImageId();
    }

    public abstract net.mamoe.mirai.message.data.FlashImage toMirai();

    @Override
    public String getDownloadURL()
    {
        return toMirai().getImage().getImageId();
    }

    @Internal
    public static final class Custom extends FlashImageImpl
    {
        private final ImageImpl image;

        public Custom(InputStream stream,Contact contact) throws IOException
        {
            image=ImageImpl.create(stream,contact);
        }

        public Custom(File file,Contact contact)
        {
            image=ImageImpl.create(file,contact);
        }

        @Override
        public net.mamoe.mirai.message.data.FlashImage toMirai()
        {
            return net.mamoe.mirai.message.data.FlashImage.from(image.toMirai());
        }
    }

    @Internal
    public static final class FromMirai extends FlashImageImpl
    {
        private final net.mamoe.mirai.message.data.FlashImage image;

        public FromMirai(net.mamoe.mirai.message.data.FlashImage image)
        {
            this.image=image;
        }

        @Override
        public net.mamoe.mirai.message.data.FlashImage toMirai()
        {
            return image;
        }
    }
}

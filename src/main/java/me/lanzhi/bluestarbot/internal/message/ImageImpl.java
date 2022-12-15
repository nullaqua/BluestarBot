package me.lanzhi.bluestarbot.internal.message;

import me.lanzhi.bluestarbot.api.contact.Contact;
import me.lanzhi.bluestarbot.api.message.Image;
import net.mamoe.mirai.utils.ExternalResource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public abstract class ImageImpl implements Image, MessageImpl
{

    public static ImageImpl create(File file,Contact contact)
    {
        return new Custom(file,contact);
    }

    public static ImageImpl create(InputStream stream,Contact contact) throws IOException
    {
        return new Custom(stream,contact);
    }

    public static ImageImpl create(net.mamoe.mirai.message.data.Image image)
    {
        return new FromMirai(image);
    }

    @Override
    public String getDisplay(Contact contact)
    {
        return "[图片]";
    }

    @Override
    public String contentToString()
    {
        return "[图片]";
    }

    @Override
    public String getDownloadURL()
    {
        return net.mamoe.mirai.message.data.Image.queryUrl(toMirai());
    }

    public abstract net.mamoe.mirai.message.data.Image toMirai();

    private static final class Custom extends ImageImpl
    {
        private final ExternalResource resource;
        private final Contact contact;

        private Custom(File file,Contact contact)
        {
            resource=ExternalResource.create(file).toAutoCloseable();
            this.contact=contact;
        }

        private Custom(InputStream stream,Contact contact) throws IOException
        {
            resource=ExternalResource.create(stream).toAutoCloseable();
            this.contact=contact;
        }

        @Override
        public net.mamoe.mirai.message.data.Image toMirai()
        {
            return net.mamoe.mirai.message.data.Image.fromId(getImageId());
        }

        @Override
        public String getImageId()
        {
            return contact.getToContact().uploadImage(resource).getImageId();
        }
    }

    private static final class FromMirai extends ImageImpl
    {
        private final net.mamoe.mirai.message.data.Image image;

        private FromMirai(net.mamoe.mirai.message.data.Image image)
        {
            this.image=image;
        }

        @Override
        public String getImageId()
        {
            return image.getImageId();
        }

        @Override
        public net.mamoe.mirai.message.data.Image toMirai()
        {
            return image;
        }
    }

}

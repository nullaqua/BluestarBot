package me.lanzhi.bluestarbot.api.event.message;

import me.lanzhi.bluestarbot.api.contact.Contact;
import me.lanzhi.bluestarbot.api.event.BluestarBotEvent;
import me.lanzhi.bluestarbot.internal.Mapping;

import java.io.InputStream;

/**
 * 图片上传事件,上传后触发
 */
public final class ImageUploadEvent extends BluestarBotEvent
{
    public ImageUploadEvent(net.mamoe.mirai.event.events.ImageUploadEvent event)
    {
        super(event);
    }

    @Override
    public net.mamoe.mirai.event.events.BeforeImageUploadEvent getEvent()
    {
        return (net.mamoe.mirai.event.events.BeforeImageUploadEvent) super.getEvent();
    }

    public Contact getContact()
    {
        return Mapping.map(getEvent().getTarget());
    }

    public InputStream getImage()
    {
        return getEvent().getSource().inputStream();
    }
}
